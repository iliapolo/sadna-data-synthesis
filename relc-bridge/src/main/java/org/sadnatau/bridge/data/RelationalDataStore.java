package org.sadnatau.bridge.data;

import org.apache.commons.beanutils.PropertyUtils;
import org.sadnatau.bridge.classloading.DataProviderClassLoader;
import org.sadnatau.bridge.compiler.JavacCompiler;
import org.sadnatau.relc.compiler.RelcCompilationResult;
import org.sadnatau.relc.compiler.RelcCompiler;
import org.sadnatau.relc.data.DataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
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
public class RelationalDataStore<T> implements DataStore<T> {

    private Logger logger = LoggerFactory.getLogger(RelationalDataStore.class);

    private DataProvider dataProvider;

    /**
     *
     * @param relationsPath
     * @param decompositionsPath
     * @throws IOException
     */
    public RelationalDataStore(final String relationsPath,
                               final String decompositionsPath) throws Exception {

        logger.debug("Creating a RelcCompiler from files [" + relationsPath + "," + decompositionsPath + "]");
        RelcCompiler relcCompiler = new RelcCompiler(relationsPath, decompositionsPath);
        this.dataProvider = createDataProvider(relcCompiler);
    }

    private DataProvider createDataProvider(final RelcCompiler relcCompiler) throws Exception {

        // this created a java file
        logger.debug("Compiling decomposition graph to Java");
        RelcCompilationResult compilationResult = relcCompiler.compile();

        // compile to bytecode
        String packagePath = compilationResult.getPackageName().replace(".", "/");

        String javaFilePath = compilationResult.getCompiledFileRoot() + "/" + packagePath + "/" + compilationResult
                .getCompiledFileName() + ".java";


        URL relc = this.getClass().getClassLoader().getResource("org/sadnatau/relc");
        JavacCompiler javacCompiler = new JavacCompiler(relc);
        logger.debug("Compiling Bytecode from Java file " + javaFilePath);
        javacCompiler.compile(javaFilePath);

        return DataProviderClassLoader.load(compilationResult.getCompiledFileRoot(), compilationResult.getPackageName() + "." +
                compilationResult.getCompiledFileName());
    }

    @Override
    public Set<T> query(T template, List<String> resultFields) throws Exception {

        Set<T> result = new HashSet<>();
        List<String> relcQuery = DataModelQueryGenerator.generate(template);
        logger.debug("Performing query on " + dataProvider + " : " + relcQuery);
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

    @Override
    public void insert(T data) throws Exception {

        logger.debug("Inserting " + data + " to" + dataProvider);

        List<String> tupleValues = new ArrayList<>();

        for (Field field : data.getClass().getDeclaredFields()) {
            String tupleValue = field.getName() + ":" + PropertyUtils.getProperty(data, field.getName());
            tupleValues.add(tupleValue);
        }

        dataProvider.insert(tupleValues);
    }

    @Override
    public void remove(T template) throws Exception {
        logger.debug("Removing " + template + " from" + dataProvider);
        List<String> tuple = DataModelQueryGenerator.generate(template);
        dataProvider.remove(tuple);
    }

    @Override
    public void update(T matchingData, Map<String, Object> fieldsToUpdate) throws Exception {
        logger.debug("Updating " + matchingData + " of " + dataProvider + " with " + fieldsToUpdate);
        List<String> matchingTuple = DataModelQueryGenerator.generate(matchingData);

        List<String> updatedValues = new ArrayList<>();
        for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
            String tupleValue = entry.getKey() + ":" + entry.getValue();
            updatedValues.add(tupleValue);
        }

        dataProvider.update(matchingTuple, updatedValues);
    }

    @Override
    public void empty() {
        logger.debug("Clearing and initializing " + dataProvider);
        dataProvider.empty();
    }
}
