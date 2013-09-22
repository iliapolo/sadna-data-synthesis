package org.sadnatau.data;

import com.sun.tools.javac.Main;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.sadnatau.classloading.DataProviderClassLoader;
import org.sadnatau.relc.compiler.RelcCompilationResult;
import org.sadnatau.relc.compiler.RelcCompiler;
import org.sadnatau.relc.data.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class RelationalDataStore<T> {

    private DataProvider dataProvider;
    private DataModelQueryGenerator generator;

    /**
     *
     * @param relations
     * @param decompositions
     * @throws IOException
     */
    public RelationalDataStore(final String relations,
                               final String decompositions) throws Exception {

        File tempDirectory = createTempDirectory();

        String relationsPath = tempDirectory.getAbsolutePath() + "/relations.txt";
        String decompositionsPath = tempDirectory.getAbsolutePath() + "/decompositions.txt";
        FileUtils.writeStringToFile(new File(relationsPath), relations);
        FileUtils.writeStringToFile(new File(decompositionsPath), decompositions);

        System.out.println("Creating a RelcCompiler from files [" + relationsPath + "," + decompositionsPath + "]");
        RelcCompiler relcCompiler = new RelcCompiler(relationsPath, decompositionsPath);
        this.dataProvider = createDataProvider(relcCompiler);
        this.generator = new DataModelQueryGenerator();

        FileUtils.deleteDirectory(tempDirectory);
    }

    private File createTempDirectory() throws IOException {

        File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

        boolean delete = temp.delete();
        if (!delete) {
            throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
        }

        boolean mkdir = temp.mkdir();
        if (!mkdir) {
            throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
        }

        return temp;
    }

    private DataProvider createDataProvider(final RelcCompiler relcCompiler) throws Exception {

        // this created a java file
        System.out.println("Compiling decomposition graph to java code");
        RelcCompilationResult compilationResult = relcCompiler.compile();

        // compile to bytecode
        String packagePath = compilationResult.getPackageName().replace(".", "/");

        String javaFilePath = compilationResult.getCompiledFileRoot() + "/" + packagePath + "/" + compilationResult
                .getCompiledFileName() + ".java";
        String[] javacArguments = {javaFilePath};

        System.out.println("Compiling java code from file " + javaFilePath + " to bytecode");
        Main.compile(javacArguments);

        DataProviderClassLoader loader = new DataProviderClassLoader();
        return loader.load(compilationResult.getCompiledFileRoot(), compilationResult.getPackageName() + "." +
                compilationResult.getCompiledFileName());
    }

    public final Set<T> query(T template, List<String> resultFields) throws Exception {

        Set<T> result = new HashSet<>();

        List<String> relcQuery = generator.generate(template);

        List<List<String>> query = dataProvider.query(relcQuery, resultFields);

        for (List<String> tuple : query) {

            // each tuple is an instance of T
            // lets try and build it.
            T t = (T) template.getClass().newInstance();
            for (String tupleEntry : tuple) {
                // each tuple entry is in the form of field:value
                String fieldName = tupleEntry.split(":")[0];
                String fieldValue = tupleEntry.split(":")[1];
                if (fieldValue.equals("null")) {
                    fieldValue = null;
                }
                PropertyUtils.setProperty(t, fieldName, fieldValue);
            }
            result.add(t);
        }
        return result;
    }

    public final void insert(final T data) throws Exception {

        List<String> tupleValues = new ArrayList<>();

        for (Field field : data.getClass().getDeclaredFields()) {
            String tupleValue = field.getName() + ":" + PropertyUtils.getProperty(data, field.getName());
            tupleValues.add(tupleValue);
        }

        dataProvider.insert(tupleValues);
    }

    public void remove(final T data) throws Exception {
        List<String> tuple = generator.generate(data);
        dataProvider.remove(tuple);
    }

    public void update(final T matchingData, Map<String, Object> fieldsToUpdate) throws Exception {

        List<String> matchingTuple = generator.generate(matchingData);

        List<String> updatedValues = new ArrayList<>();
        for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
            String tupleValue = entry.getKey() + ":" + entry.getValue();
            updatedValues.add(tupleValue);
        }

        dataProvider.update(matchingTuple, updatedValues);
    }

    public void empty() {
        dataProvider.empty();
    }
}
