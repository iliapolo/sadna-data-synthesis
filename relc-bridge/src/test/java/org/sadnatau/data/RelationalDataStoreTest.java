package org.sadnatau.data;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Test functionality of {@link RelationalDataStore}
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class RelationalDataStoreTest {

    private static final String RELATION = "org/sadnatau/data/relation.txt";
    private static final String DECOMPOSITIONS = "org/sadnatau/data/decompositions.txt";

    @Test
    public void testQuery() throws Exception {

        RelationalDataStore<Page> dataStore = createDataStore();

        Page page = new Page();
        page.setTitle("my-title");
        page.setAuthor("my-author");
        page.setWikitext("my-text");
        page.setKeyword("my-keyword");

        dataStore.insert(page);

        // query the author of the page with keyword 'my-keyword'
        Page template = new Page();
        template.setKeyword("my-keyword");
        Set<Page> authors = dataStore.query(template, Arrays.asList("author"));

        // the result should contain just one page
        Assert.assertEquals(1, authors.size());

        // the result page should have 'my-author' as the author
        Page result = authors.iterator().next();

        Assert.assertEquals("my-author", result.getAuthor());

        // all other fields should be null since we requested only the author
        Assert.assertNull(result.getKeyword());
        Assert.assertNull(result.getTitle());
        Assert.assertNull(result.getWikitext());

    }

    @Test
    public void testRemove() throws Exception {

        RelationalDataStore<Page> dataStore = createDataStore();

        Page page = new Page();
        page.setTitle("my-title");

        dataStore.insert(page);

        dataStore.remove(page);

        // at this point the page should not exist
        Set<Page> pages = dataStore.query(page, Arrays.asList("title"));
        Assert.assertTrue(pages.isEmpty());
    }

    @Test
    public void testUpdate() throws Exception {

        RelationalDataStore<Page> dataStore = createDataStore();

        Page page = new Page();
        page.setTitle("my-title");
        page.setAuthor("my-author");
        page.setWikitext("text1");
        page.setKeyword("my-keyword");

        dataStore.insert(page);

        Map<String, Object> updates = new HashMap<>();
        updates.put("wikitext", "text1updated");

        Page pageToUpdate = new Page();
        pageToUpdate.setTitle("my-title");
        // update all pages with title 'my-title' with new wikitext
        dataStore.update(pageToUpdate, updates);

        Page template = new Page();
        template.setAuthor("my-author");
        Set<Page> pages = dataStore.query(template, Arrays.asList("wikitext"));

        // the result should contain just one page
        Assert.assertEquals(1, pages.size());

        // the text of that page should be 'text1updated'
        Page next = pages.iterator().next();
        Assert.assertEquals("text1updated", next.getWikitext());


    }

    private RelationalDataStore<Page> createDataStore() throws Exception {
        String relation = Resources.toString(Resources.getResource(RELATION), Charsets.UTF_8);
        String decomp = Resources.toString(Resources.getResource(DECOMPOSITIONS), Charsets.UTF_8);;
        RelationalDataStore<Page> pageRelationalDataStore = new RelationalDataStore<>(relation, decomp);
        pageRelationalDataStore.empty();
        return pageRelationalDataStore;
    }
}
