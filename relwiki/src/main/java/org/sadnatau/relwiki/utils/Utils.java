package org.sadnatau.relwiki.utils;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;
import org.sadnatau.relwiki.transport.model.Change;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    public static <T> Set<T> findIntersection(Set<T> first, Set<T> second) {
        Set<T> common = new HashSet<T>(first);
        common.retainAll(second);
        return common;
    }

    public static <T> Set<T> findIntersection(List<Set<T>> sets) {
        Set<T> intersection = sets.get(0);
        for (int i = 1; i < sets.size(); i++) {
            intersection = findIntersection(intersection, sets.get(i));
        }
        return intersection;
    }

    public static Set<Change> findDiff(String existingText, String currentText)  {

        Set<Change> changes = new HashSet<Change>();
        String[] existingLines = existingText.split("\\n");
        String[] currentLines = currentText.split("\\n");

        int i = 0 /* existing */, j /* current */ = 0;
        while (true) {

            if (i < existingLines.length && j < currentLines.length) {
                String existingLine = existingLines[i];
                String currentLine = currentLines[i];
                if (!existingLine.equals(currentLine)) {
                    // treat this as a removal and addition
                    Change added = new Change();
                    added.setText(currentLine);
                    added.setLineNumber(i + 1);
                    added.setAddedOrRemoved("added");
                    Change removed = new Change();
                    removed.setText(existingLine);
                    removed.setLineNumber(i + 1);
                    removed.setAddedOrRemoved("removed");
                    changes.add(added);
                    changes.add(removed);

                }
                // go to next line if the is one
                i++;
                j++;
            } else if (i >= existingLines.length && j < currentLines.length) {

                // we have scanned all existing lines and there are still current lines left
                // treat the current lines left as additions
                for (int l = j; l < currentLines.length; l++) {
                    Change added = new Change();
                    added.setAddedOrRemoved("added");
                    added.setLineNumber(l + 1);
                    added.setText(currentLines[l]);
                    changes.add(added);
                }

                // we are done.
                break;

            } else if (j >= currentLines.length && i < existingLines.length) {

                // we have scanned all current lines and there are still existing lines left
                // treat the existing lines left as removals
                for (int m = i; m < existingLines.length; m++) {
                    Change removed = new Change();
                    removed.setAddedOrRemoved("removed");
                    removed.setLineNumber(m + 1);
                    removed.setText(existingLines[m]);
                    changes.add(removed);
                }

                // we are done
                break;

            } else if (i >= existingLines.length && j >= currentLines.length) {
                // we have moved out of bounds in bothes arrays.
                break;
            }

        }
        return changes;
    }
}
