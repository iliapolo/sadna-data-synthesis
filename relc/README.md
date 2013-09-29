Relational Data Compiler
========================

The project consists out of the following main packages:

Compiler package
----------------

- [RelcCompiler](src/main/java/org/sadnatau/relc/compiler/RelcCompiler.java) - This class represents the actual compiler. This compiler accepts two inputs:  
1.[Relations file](src/test/resources/org/sadnatau/relc/compiler/relation.txt) - This file describes the funtional dependencie of the specific data.  
2.[Decomposition graph file](src/test/resources/org/sadnatau/relc/compiler/decomp.txt) - This file describes the decomposition graph on the given data.  
During the compiler instantiation, it parses both these files, performs input validations as well as adequacy check for the decomposition graph file.  
If everything is ok, the compiler creates a Java code file that implements the [DataProvider](src/main/java/org/sadnatau/relc/data/DataProvider.java) interface for the specific relation and decomposition graph.

### API ###

    RelcCompiler compiler = new RelcCompiler(relationsFilePath, decompositionFilePath);
    RelcCompilationResult compilationResult = compiler.compile();
    
    String packageName = compilationResult.getPackageName()
    String compiledFileName = compilationResult.getCompiledFileName()
    String compiledFileRoot = compilationResult.getCompiledFileRoot()

- [DataProviderImpl](src/main/resources/org/sadnatau/relc/compiler/DataProviderImpl.template) - This file is basically the output file the compiler creates.  
It holds all the operations implementations, which are static and not input dependent. The only thing that is generated at compile time is the decomposition graph and the relation specification.  
This information is injected into the file by creating a constrcutor that reads it from a serialized object stored in a file.

- [DecompositionGraph](src/main/java/org/sadnatau/relc/compiler/DecompositionGraph.java) - This class represents the input decompostion graph, which is static.  
It holds two inner class for the graph vertex and egde. Also provider iteration capabillities using a topological sort.

- [InstanceNode]((src/main/java/org/sadnatau/relc/compiler/InstanceNode.java) - This class represents a node in the dynamic decomposition graph. A "decomposition instance".  
All the relational interface implementations are strongly bounded to this class. Thas is, an operation on the dynamic graph is executed by operating on the root of the graph, which is an instance of this class.  
It continues to execute the operation in a recursive manner on all graph nodes, similair to implementations of a binary search tree.
For instance, a tuple addition, begins at the root and continues recursively downwards, while adding the necessary nodes to the data structure.

Data Package
------------

This package has classes that implement primitive data structures. All implement a shared interface, which is just a Map.  
Most of the implementation are based on built-in Java structures and use composition and delegation.

- [DataProvider](src/main/java/org/sadnatau/relc/data/DataProvider.java) - The relational interface that exposes the possible operations.
- [KeyValueDataStrcture](src/main/java/org/sadnatau/relc/data/KeyValueDataStructure.java) - An key-value data structure interface that every custom data structure must implement.
- [PrivmitiveDS](src/main/java/org/sadnatau/relc/data/PrimitiveDS.java) - An enum class enumarating all the possible data structures.
- [Vector](src/main/java/org/sadnatau/relc/data/Vector.java) - A dynamic array implementation.
- [LinkedList](src/main/java/org/sadnatau/relc/data/LinkedList.java) - A two sided linked list implementation.
- [HashTable](src/main/java/org/sadnatau/relc/data/HashTable.java) - A hash table implementation.
- [BinaryTree](src/main/java/org/sadnatau/relc/data/BinaryTree.java) - A balanced binary search tree implementation.
- [DSFactory](src/main/java/org/sadnatau/relc/data/DSFactory.java) - A factory class for creating data structure instances.

Util package
------------

- [ToolBox](src/main/java/org/sadnatau/relc/util/ToolBox.java) - A utility class to be used by other classes. mainly holds tuple manipulations.
- [SyntaxError](src/main/java/org/sadnatau/relc/util/SyntaxError.java) - An exception class for syntactic errors.
- [SemanticError](src/main/java/org/sadnatau/relc/util/SemanticError.java) - An exception class for semantic errors.

