package org.sadnatau.relwiki.utils;

import junit.framework.Assert;
import org.junit.Test;
import org.sadnatau.relwiki.transport.model.Change;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class UtilsTest {

    @Test
    public void testFindIntersectionSameSize() throws Exception {

        Set<String> first = new HashSet<String>(Arrays.asList("author1", "author2"));
        Set<String> second = new HashSet<String>(Arrays.asList("author1", "author3"));

        Set<String> expected = new HashSet<String>(Arrays.asList("author1"));
        Set<String> cut = Utils.findIntersection(first, second);

        Assert.assertEquals(expected, cut);

    }

    @Test
    public void testFindIntersectionFirstBigger() throws Exception {

        Set<String> first = new HashSet<String>(Arrays.asList("author1", "author2", "author3", "author4"));
        Set<String> second = new HashSet<String>(Arrays.asList("author1", "author3"));

        Set<String> expected = new HashSet<String>(Arrays.asList("author1", "author3"));
        Set<String> cut = Utils.findIntersection(first, second);

        Assert.assertEquals(expected, cut);

    }

    @Test
    public void testFindIntersectionSecondBigger() throws Exception {

        Set<String> first = new HashSet<String>(Arrays.asList("author1", "author2"));
        Set<String> second = new HashSet<String>(Arrays.asList("author1", "author3", "author2", "author4"));

        Set<String> expected = new HashSet<String>(Arrays.asList("author1", "author2"));
        Set<String> cut = Utils.findIntersection(first, second);

        Assert.assertEquals(expected, cut);

    }

    @Test
    public void testFindIntersectionOfMultipleSets() throws Exception {

        Set<String> first = new HashSet<String>(Arrays.asList("author1", "author2"));
        Set<String> second = new HashSet<String>(Arrays.asList("author1", "author3", "author2", "author4"));
        Set<String> third = new HashSet<String>(Arrays.asList("author1"));

        List<Set<String>> sets = new ArrayList<Set<String>>();
        sets.add(first);
        sets.add(second);
        sets.add(third);

        Set<String> expected = new HashSet<String>(Arrays.asList("author1"));

        Set<String> intersection = Utils.findIntersection(sets);
        Assert.assertEquals(expected, intersection);
    }

    @Test
    public void testGetDiffEqualSize() {

        String existingText = "hello\nworld. i am eli\nHow are you?";
        String currentText = "good day\nworld. i am eli\nWhats your name?";

        Change helloRemoved = new Change();
        helloRemoved.setAddedOrRemoved("removed");
        helloRemoved.setLineNumber(1);
        helloRemoved.setText("hello");

        Change goodDayAdded = new Change();
        goodDayAdded.setAddedOrRemoved("added");
        goodDayAdded.setLineNumber(1);
        goodDayAdded.setText("good day");

        Change howAreYouRemoved = new Change();
        howAreYouRemoved.setAddedOrRemoved("removed");
        howAreYouRemoved.setLineNumber(3);
        howAreYouRemoved.setText("How are you?");

        Change whatsYourNameAdded = new Change();
        whatsYourNameAdded.setAddedOrRemoved("added");
        whatsYourNameAdded.setLineNumber(3);
        whatsYourNameAdded.setText("Whats your name?");

        Set<Change> expected = new HashSet<Change>();
        expected.add(helloRemoved);
        expected.add(goodDayAdded);
        expected.add(howAreYouRemoved);
        expected.add(whatsYourNameAdded);

        Set<Change> diff = Utils.findDiff(existingText, currentText);
        Assert.assertEquals(expected, diff);

    }

    @Test
    public void testGetDiffExistingBigger() {

        String existingText = "hello\nworld. i am eli\nHow are you?";
        String currentText = "good day\nworld. i am eli";

        Change helloRemoved = new Change();
        helloRemoved.setAddedOrRemoved("removed");
        helloRemoved.setLineNumber(1);
        helloRemoved.setText("hello");

        Change goodDayAdded = new Change();
        goodDayAdded.setAddedOrRemoved("added");
        goodDayAdded.setLineNumber(1);
        goodDayAdded.setText("good day");

        Change howAreYouRemoved = new Change();
        howAreYouRemoved.setAddedOrRemoved("removed");
        howAreYouRemoved.setLineNumber(3);
        howAreYouRemoved.setText("How are you?");

        Set<Change> expected = new HashSet<Change>();
        expected.add(helloRemoved);
        expected.add(goodDayAdded);
        expected.add(howAreYouRemoved);

        Set<Change> diff = Utils.findDiff(existingText, currentText);
        Assert.assertEquals(expected, diff);

    }

    @Test
    public void testGetDiffCurrentBigger() {

        String existingText = "hello\nworld. i am eli";
        String currentText = "good day\nworld. i am eli\nWhats your name?";

        Change helloRemoved = new Change();
        helloRemoved.setAddedOrRemoved("removed");
        helloRemoved.setLineNumber(1);
        helloRemoved.setText("hello");

        Change goodDayAdded = new Change();
        goodDayAdded.setAddedOrRemoved("added");
        goodDayAdded.setLineNumber(1);
        goodDayAdded.setText("good day");

        Change whatsYourNameAdded = new Change();
        whatsYourNameAdded.setAddedOrRemoved("added");
        whatsYourNameAdded.setLineNumber(3);
        whatsYourNameAdded.setText("Whats your name?");

        Set<Change> expected = new HashSet<Change>();
        expected.add(helloRemoved);
        expected.add(goodDayAdded);
        expected.add(whatsYourNameAdded);

        Set<Change> diff = Utils.findDiff(existingText, currentText);
        Assert.assertEquals(expected, diff);

    }

}
