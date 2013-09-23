package org.sadnatau.classloading;

import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sadnatau.compiler.JavacCompiler;
import org.sadnatau.relc.data.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class DataProviderClassLoaderTest {

    private static final String RANDOM_USER_HOME = System.getProperty("user.home") + "/" + UUID.randomUUID();
    private static final String FILE_NAME = "ForTest";
    private static final String FOR_TEST = "org/sadnatau/classloading/" + FILE_NAME;
    private static final String PACKAGE = "org.sadnatau.compiler";

    @BeforeClass
    public static void compile() throws IOException, InterruptedException {

        File source = new File(Resources.getResource(FOR_TEST).getPath());
        File target = new File(RANDOM_USER_HOME + "/" + PACKAGE.replace(".", "/") + "/ForTest.java");

        // create a .java file
        FileUtils.copyFile(source, target);

        // compile to bytecode
        // the relc package is needed for compilation
        JavacCompiler javacCompiler = new JavacCompiler(Resources.getResource("org/sadnatau/relc"));
        javacCompiler.compile(target.getAbsolutePath());
    }

    @Test
    public void testLoad() throws Exception {

        DataProvider load = DataProviderClassLoader.load(RANDOM_USER_HOME, PACKAGE + "." + FILE_NAME);

        // invoke every method
        load.empty();
        load.insert(null);
        load.remove(null);
        load.update(null, null);
        load.query(null, null);
    }

    @AfterClass
    public static void clean() throws IOException {
        FileUtils.deleteDirectory(new File(RANDOM_USER_HOME));
    }
}
