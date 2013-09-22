package org.sadnatau.data;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class DataModelQueryGeneratorTest {

    private static DataModelQueryGenerator queryGenerator;

    @BeforeClass
    public static void createGenerator() throws IOException {
        queryGenerator = new DataModelQueryGenerator();
    }


    @Test
    public void testGenerateRelcQuery() throws Exception {

        MockData mockData = new MockData();
        mockData.setTitle("title-value");
        mockData.setAuthor("author-value");

        List<String> expectedQuery = new ArrayList<>();
        expectedQuery.add("title:title-value");
        expectedQuery.add("author:author-value");
        List<String> actualQuery = queryGenerator.generate(mockData);

        Assert.assertEquals(actualQuery, expectedQuery);

    }
}
