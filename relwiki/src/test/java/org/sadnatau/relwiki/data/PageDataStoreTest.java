package org.sadnatau.relwiki.data;

import org.junit.Assert;
import org.junit.Test;
import org.sadnatau.relwiki.beans.PageDataStoreFactoryBean;
import org.sadnatau.relwiki.model.Page;

import java.util.Arrays;
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

        Set<String> expected = new HashSet<String>();
        for (int i = 0; i < 5; i++) {
            Page page = new Page();
            page.setTitle("title" + i);
            dataStore.add(page);
            expected.add(page.getTitle());
        }
        Set<String> all = dataStore.getAll();
        Assert.assertEquals(expected, all);
    }

    @Test
    public void testGetPagesForKeyword() throws Exception {

        PageDataStore dataStore = createDataStore();

        Page page = new Page();
        page.setTitle("title1");
        page.setKeyword("hello");

        Page page1 = new Page();
        page1.setTitle("title2");
        page1.setKeyword("hello");

        dataStore.add(page);
        dataStore.add(page1);

        Set<String> expected = new HashSet<String>(Arrays.asList("title1", "title2"));

        Set<String> titles = dataStore.getPagesForKeyword("hello");
        Assert.assertEquals(expected, titles);
    }

    @Test
    public void testGetPagesForAuthor() throws Exception {

        PageDataStore dataStore = createDataStore();

        Page page = new Page();
        page.setTitle("title1");
        page.setAuthor("hello");

        Page page1 = new Page();
        page1.setTitle("title2");
        page1.setAuthor("hello");

        dataStore.add(page);
        dataStore.add(page1);

        Set<String> expected = new HashSet<String>(Arrays.asList("title1", "title2"));

        Set<String> titles = dataStore.getPagesForAuthor("hello");
        Assert.assertEquals(expected, titles);

    }

    @Test
    public void testGetPages() throws Exception {

        PageDataStore dataStore = createDataStore();

        Page page = new Page();
        page.setTitle("title");
        page.setAuthor("author");

        dataStore.add(page);

        Set<Page> author = dataStore.getPages("author");

        Page next = author.iterator().next();
        Assert.assertEquals("author", next.getAuthor());


    }

    @Test
    public void testGetAllKeywords() throws Exception {

        PageDataStore dataStore = createDataStore();

        Page page = new Page();
        page.setTitle("title1");
        page.setKeyword("hello1");

        Page page1 = new Page();
        page1.setTitle("title2");
        page1.setKeyword("hello2");

        dataStore.add(page);
        dataStore.add(page1);

        Set<String> expected = new HashSet<String>(Arrays.asList("hello1", "hello2"));

        Set<String> titles = dataStore.getAllKeywords();
        Assert.assertEquals(expected, titles);

    }

    @Test
    public void testGetAllAuthors() throws Exception {

        PageDataStore dataStore = createDataStore();

        Page page = new Page();
        page.setTitle("title1");
        page.setAuthor("hello1");

        Page page1 = new Page();
        page1.setTitle("title2");
        page1.setAuthor("hello2");

        dataStore.add(page);
        dataStore.add(page1);

        Set<String> expected = new HashSet<String>(Arrays.asList("hello1", "hello2"));

        Set<String> titles = dataStore.getAllAuthors();
        Assert.assertEquals(expected, titles);

    }

    @Test
    public void testUpdateWikiText() throws Exception {

        PageDataStore dataStore = createDataStore();

        Page page = new Page();
        page.setTitle("title");
        page.setAuthor("a");
        page.setKeyword("k");
        page.setWikitext("wikitext");

        Page page1 = new Page();
        page1.setTitle("title1");
        page1.setAuthor("b");
        page1.setKeyword("g");
        page1.setWikitext("wikitext1");

        dataStore.add(page);
        dataStore.add(page1);

        dataStore.updateWikiText("title", "wikitextupdated");

        // check for update of page with title 'title'
        Assert.assertEquals("wikitextupdated", dataStore.getWikiText("title"));

        // check the update didnt affect the other page
        Assert.assertEquals("wikitext1", dataStore.getWikiText("title1"));


    }

    private PageDataStore createDataStore() throws Exception {
        PageDataStoreFactoryBean bean = new PageDataStoreFactoryBean();
        PageDataStore dataStore = bean.getPageDataStore();
        dataStore.empty();
        return dataStore;
    }
}
