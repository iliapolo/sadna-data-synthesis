package org.sadnatau.relc.compiler;

import com.google.common.io.Resources;
import org.sadnatau.relc.data.PrimitiveDS;
import org.sadnatau.relc.util.SyntaxError;
import org.sadnatau.relc.util.ToolBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * The RelC compiler main class.
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 * 
 */
public class RelcCompiler {

    private static final String USER_HOME = System.getProperty("user.home");

    private static final String WORKING_DIR = USER_HOME + "/relc-work/";

    private String relFilePath;
    private String decompFilePath;
    private List<String> columns;
    private List<String> funcDomainCols;
    private List<String> funcImageCols;
    private DecompositionGraph decompGraph;

    // patterns for parsing.
    private static final String SET = "[{](.*)[}]";
	private static final Pattern SET_PAT = Pattern.compile(SET);

	private static final String FUNC = "(.*)[-][>](.*)";
	private static final Pattern FUNC_PAT = Pattern.compile(FUNC);

	private static final String ELEMENT = "[ \t]*(\\w+)[ \t]*";
	private static final Pattern ELEMENT_PAT = Pattern.compile(ELEMENT);
	
	private static final String GRAPH_LINE = "(\\w+)[:](.*)";
	private static final Pattern GRAPH_LINE_PAT = Pattern.compile(GRAPH_LINE);

	private static final String EDGE = ELEMENT + "[<](.*)[|][ \t]*([a-z]+)[ \t]*[>][ \t]*";
	private static final Pattern EDGE_PAT = Pattern.compile(EDGE);
	
	
	public RelcCompiler(final String relFilePath,
                        final String decompFilePath) throws SyntaxError, IOException {

		this.relFilePath = relFilePath;
		this.decompFilePath = decompFilePath;
		this.columns = new ArrayList<String>();
		this.funcDomainCols = new ArrayList<String>();
		this.funcImageCols = new ArrayList<String>();
		this.decompGraph = new DecompositionGraph();

		parseColumnsAndFunction();
		parseDecompGraph();
	}

    // creates the output java code file.
    public RelcCompilationResult compile() throws Exception {

        String packageName = "org.sadnatau.relc.compiler";

        long now = System.currentTimeMillis();

        String outputCodeDir = WORKING_DIR + "/" +  +now + "/org/sadnatau/relc/compiler";
        String graphSerializationFileName = "serialized-decomposition-graph";
        String outClassName = "DataProviderImpl";

        RelcCompilationResult compilationResult = new RelcCompilationResult();
        compilationResult.setCompiledFileName(outClassName);
        compilationResult.setCompiledFileRoot(WORKING_DIR + "/" + now);
        compilationResult.setPackageName(packageName);

        File f = new File(outputCodeDir);  // make package of output file.
        boolean mkdirs = f.mkdirs();
        if (!mkdirs) {
            throw new IOException("Failed to create directories : " + f.getAbsolutePath());
        }

        String cols = lstToString(this.columns);
        String funDomCols = lstToString(this.funcDomainCols);
        String funImgCols = lstToString(this.funcImageCols);

        BufferedReader reader = null;
        Writer writer = null;
        ObjectOutputStream out = null;

        try {

            // serialization of decomposition graph.

            String serializedGraphFilePath = outputCodeDir + "/" + graphSerializationFileName;

            out = new ObjectOutputStream(new FileOutputStream(serializedGraphFilePath));
            out.writeObject(this.decompGraph);

            String pathToTemplate = ToolBox.extractResource("org/sadnatau/relc/compiler/DataProviderImpl.template",
                    "DataProviderImpl.template").getAbsolutePath();

            reader = new BufferedReader(new FileReader(pathToTemplate));
            String outputFilePath = outputCodeDir + "/" + outClassName + ".java";
            File outputFile = new File(outputFilePath);
            writer = new FileWriter(outputFile);

            writer.write("package " + packageName + ";\r\n");

            String line = reader.readLine();  // skip package line.
            line = reader.readLine();

            while (!line.startsWith("public class")) { // till class line.
                writer.write(line + "\r\n");
                line = reader.readLine();
            }
            writer.write("public class " + outClassName + " implements DataProvider {\r\n");   //writing class line.
            line = reader.readLine();
            while (!line.contains("*/")) { //till end of java-doc of constructor.
                writer.write(line + "\r\n");
                line = reader.readLine();
            }
            writer.write("\t */\r\n");

            // writing the constructor code.

            String nl = "\n";
            String ctorCode = "\tpublic " + outClassName + "() throws IOException, ClassNotFoundException {" + nl +
                    "\t\tthis.decompositionSerializationFilePath = " +
                    '"' + serializedGraphFilePath + "\";" + nl +
                    "\t\tthis.cols = " + "\"" + cols + "\";" + nl +
                    "\t\tthis.funDomCols = " + "\"" + funDomCols + "\";" + nl +
                    "\t\tthis.funImgCols = " + "\"" + funImgCols + "\";" + nl +
                    "\t\t" + "initColumnsAndFunction();" + nl +
                    "\t\t" + "initDecompositionGraph();" + nl +
                    "\t}" + nl;


            writer.write(ctorCode);
            line = reader.readLine();

            while (line != null) {  //till EOF.
                writer.write(line + "\r\n");
                line = reader.readLine();
            }
            System.out.println("Compiled java code to " + outputFilePath);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // ignore.
            }
        }

        return compilationResult;
    }

	// converts a list of strings to a comma-separated string.
	private String lstToString(List<String> lst) {
		String ret = "";
		if (lst.isEmpty()) {
			return ret;
		}
		for (String s : lst) {
			ret = ret + s + ",";
		}
		return ret.substring(0, ret.length() - 1);  //without last comma.
	}

    // parses set of columns and function file.
    private void parseColumnsAndFunction() throws SyntaxError, IOException {

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(relFilePath));

            // first line - the columns set
            String line = reader.readLine();
            Matcher m = SET_PAT.matcher(line);
            if (!m.matches()) {
                throw new SyntaxError("Wrong syntax of set of columns.");
            }

            stringOfColsToList(m.group(1), columns);
            line = reader.readLine();

            m = FUNC_PAT.matcher(line);
            if (!m.matches()) {
                throw new SyntaxError("Wrong syntax of functional dependency.");
            }
            stringOfColsToList(m.group(1), funcDomainCols);
            stringOfColsToList(m.group(2), funcImageCols);
            reader.close();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }


    /**
     * Gets a comma-separated string of columns, and makes from it a list of columns.
     * @param s
     * @param lst
     * @throws SyntaxError
     */
    private void stringOfColsToList(String s, List<String> lst) throws SyntaxError {
        String[] cols = s.split(",");
        Matcher m = null;
        for (String c : cols) {
            m = ELEMENT_PAT.matcher(c);
            if (!m.matches()) {
                throw new SyntaxError("Wrong syntax of name of a column.");
            }
            lst.add(m.group(1));
        }
    }

    // parses the decomposition graph file.
    private void parseDecompGraph() throws SyntaxError, IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(decompFilePath));
            String line = null;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                parseGraphFileLine(line);
            }
            reader.close();
            calcBoundColumns(); //calculates bound columns for each vertex.
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // ignore.
            }
        }
    }

    // parses a line of decomposition graph file.
    private void parseGraphFileLine(String line) throws SyntaxError {
        Matcher m = GRAPH_LINE_PAT.matcher(line);
        if (!m.matches()) {
            throw new SyntaxError("Wrong syntax of line in graph file.");
        }
        String origVertexName = m.group(1);

        // the vertex of this line.
        DecompositionGraph.Vertex v = new DecompositionGraph.Vertex(origVertexName);

        // this vertex is a sink, we get his columns.
        if (!m.group(2).contains("<")) {

            List<String> cols = new ArrayList<String>();
            stringOfColsToList(m.group(2), cols);
            v.setSinkColsNames(cols);

            this.decompGraph.addVertex(v);

            return;
        }

        //this vertex has outgoing edges.
        String[] adjacentVert = m.group(2).split(";");

        for (String s : adjacentVert) {
            m = EDGE_PAT.matcher(s);
            if (!m.matches()) {
                throw new SyntaxError("Wrong syntax of line in graph file.");
            }
            String adjVertName = m.group(1);
            PrimitiveDS ds = PrimitiveDS.valueOf(m.group(3).toUpperCase(Locale.ENGLISH));

            //getting columns names.
            List<String> cols = new ArrayList<String>();
            stringOfColsToList(m.group(2), cols);
            DecompositionGraph.Edge e = new DecompositionGraph.Edge(origVertexName, adjVertName, cols, ds);
            v.addAdj(e);
        }
        this.decompGraph.addVertex(v);
    }


    /**
     * sets for each vertex the set of columns which appear on any path from root to him (union).
     */
    private void calcBoundColumns() {

        //we travel on vertices in top order, to fill the cols. correctly.

        for (DecompositionGraph.Vertex v : this.decompGraph) {
            for (DecompositionGraph.Edge e : v.getAdjList()) {
                DecompositionGraph.Vertex dest = decompGraph.getVertexByName(e.getDestVertName());
                List<String> tmp = dest.getBoundCols();
                tmp = ToolBox.setsUnion(tmp, v.getBoundCols());
                tmp = ToolBox.setsUnion(tmp, e.getCols());
                if (dest.isSinkVertex()) {
                    tmp = ToolBox.setsUnion(tmp, dest.getSinkColsNames());
                }
                dest.setBoundCols(tmp);
            }
        }
    }
}
