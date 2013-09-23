package org.sadnatau.relwiki.utils;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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

}
