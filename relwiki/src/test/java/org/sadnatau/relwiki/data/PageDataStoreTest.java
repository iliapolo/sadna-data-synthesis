package org.sadnatau.relwiki.data;

import org.junit.Assert;
import org.junit.Test;
import org.sadnatau.relwiki.beans.PageDataStoreFactoryBean;
import org.sadnatau.relwiki.model.Page;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests functionality of {@link PageDataStore}
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class PageDataStoreTest {

    @Test
    public void testGetAuthors() throws Exception {

        PageDataStore dataStore = createDataStore();

        Page page1 = new Page();
        page1.setAuthor("a");
        page1.setTitle("title");

        Page page2 = new Page();
        page2.setAuthor("b");
        page2.setTitle("title");

        dataStore.add(page1);
        dataStore.add(page2);

        // now the test
        Set<String> expected = new HashSet<String>();
        expected.add("a");
        expected.add("b");
        Set<String> authors = dataStore.getAuthors("title");
        Assert.assertEquals(expected, authors);
    }

    @Test
    public void testGetKeywords() throws Exception {

        PageDataStore dataStore = createDataStore();

        Page page1 = new Page();
        page1.setKeyword("a");
        page1.setTitle("title");

        Page page2 = new Page();
        page2.setKeyword("b");
        page2.setTitle("title");

        dataStore.add(page1);
        dataStore.add(page2);

        // now the test
        Set<String> expected = new HashSet<String>();
        expected.add("a");
        expected.add("b");
        Set<String> authors = dataStore.getKeywords("title");
        Assert.assertEquals(expected, authors);

    }

    @Test
    public void testGetWikiText() throws Exception {

        PageDataStore dataStore = createDataStore();

        Page page = new Page();
        page.setWikitext("text");
        page.setTitle("title");

        dataStore.add(page);

        // now the test
        String text = dataStore.getWikiText("title");
        Assert.assertEquals("text", text);

    }

    @Test
    public void testGetAll() throws Exception {

        PageDataStore dataStore = createDataStore();

        Set<Page> expected = new HashSet<Page>();
        for (int i = 0; i < 5; i++) {
            Page page = new Page();
            page.setTitle("title" + i);
            dataStore.add(page);
            expected.add(page);
        }
        Set<Page> all = dataStore.getAll();
        Assert.assertEquals(expected, all);
    }

    private PageDataStore createDataStore() throws Exception {
        PageDataStoreFactoryBean bean = new PageDataStoreFactoryBean();
        PageDataStore dataStore = bean.getObject();
        dataStore.empty();
        return dataStore;
    }
}
