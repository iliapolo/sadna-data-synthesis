package org.sadnatau.data;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class DataModelQueryGeneratorTest {

    @Test
    public void testGenerateRelcQuery() throws Exception {

        MockData mockData = new MockData();
        mockData.setTitle("title-value");
        mockData.setAuthor("author-value");

        List<String> expectedQuery = new ArrayList<>();
        expectedQuery.add("title:title-value");
        expectedQuery.add("author:author-value");
        List<String> actualQuery = DataModelQueryGenerator.generate(mockData);

        Assert.assertEquals(actualQuery, expectedQuery);

    }
}
