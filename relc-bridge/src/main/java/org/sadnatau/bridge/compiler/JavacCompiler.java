package org.sadnatau.bridge.compiler;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class JavacCompiler {

    List<URL> resources;

    public JavacCompiler(final URL ... classpathResources) {
        this.resources = Arrays.asList(classpathResources);
    }

    public void compile(final String javaFilePath) throws IOException, InterruptedException {

        String classpath = "";
        if (!resources.isEmpty()) {
            classpath += "-classpath ";
            for (URL resource : resources) {
                classpath += handleResource(resource);
            }
        }
        Process exec = Runtime.getRuntime().exec("javac " + classpath + " " + javaFilePath);
        exec.waitFor();

    }

    public void addResource(URL resource) {
        resources.add(resource);
    }

    private String handleResource(URL resource) {
        Preconditions.checkNotNull(resource, "resource");
        String resourcePath = resource.getPath();

        if (resourcePath.startsWith("file:")) {
            resourcePath = resourcePath.split("file:")[1];
        }

        if (resourcePath.contains(".jar")) {
            // we are running from inside the jar
            // pass the relc jar as the classpath
            return resourcePath.split("!")[0];
        } else {
            // we are running in unit tests.
            return resourcePath.split("classes")[0] + "/classes";
        }
    }

}
