Relational Data Compiler
========================

This compiler accepts two inputs:

* 1. [Relations file](src/test/resources/org/sadnatau/relc/compiler/relation.txt) - this file describes the funtional dependencie of the specific data.
* 2. [Decomposition graph file](src/test/resources/org/sadnatau/relc/compiler/decomp.txt) - this file describes the decomposition graph on the given data.

### API ###

    RelcCompiler compiler = new RelcCompiler(relationsFilePath, decompositionFilePath);
    RelcCompilationResult compilationResult = compiler.compile();
    
    String packageName = compilationResult.getPackageName()
    String compiledFileName = compilationResult.getCompiledFileName()
    String compiledFileRoot = compilationResult.getCompiledFileRoot()

The output Java file is an implementation of the [DataProvider](src/main/java/org/sadnatau/relc/data/DataProvider.java) interface for the specific relation and decomposition graph.

