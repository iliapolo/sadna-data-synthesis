package org.sadnatau.bridge.classloading;

import org.sadnatau.relc.data.DataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class DataProviderClassLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataProviderClassLoader.class);

    public static DataProvider load(final String rootFolder,
                             final String fullClassName) throws Exception{

        // load the class using a custom class loader.
        URLClassLoader loader = new URLClassLoader(
                new URL[] {Paths.get(rootFolder).toUri().toURL()}, DataProviderClassLoader.class.getClassLoader());

        // return a new instance of this class
        logger.debug("Loading class [" + fullClassName + "] from " + rootFolder);
        final Object dataProvider = loader.loadClass(fullClassName).newInstance();

        // cannot cast to DataProvider since we are using a different class loader.
        // return a new implementation and delegate to the newly loaded class using reflection.
        return new ReflectionDelegatingDataProvider(dataProvider);
    }
}
