package org.sadnatau.relwiki.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class Utils {

    public static File createTempDirectory() throws IOException {

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

    public static File extractResource(final String resourcePath, final String name) throws IOException {
        String text = Resources.toString(Resources.getResource(resourcePath), Charsets.UTF_8);
        File tempDirectory = Utils.createTempDirectory();
        String filePath = tempDirectory.getAbsolutePath() + "/" + name;
        File file = new File(filePath);
        FileUtils.writeStringToFile(file, text);
        return file;
    }



}
