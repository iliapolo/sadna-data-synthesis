package org.sadnatau.relwiki.data;

import org.sadnatau.data.RelationalDataStore;
import org.sadnatau.relwiki.model.Page;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class PageDataStore {

    private RelationalDataStore<Page> pageRelationalDataStore;

    public PageDataStore(final RelationalDataStore dataStore) {
        this.pageRelationalDataStore = dataStore;
    }

    public void empty() {
        pageRelationalDataStore.empty();
    }

    public Set<String> getAuthors(final String title) throws Exception {

        Set<String> authors = new HashSet<String>();
        Page pageTemplate = new Page();
        pageTemplate.setTitle(title);

        // this will populate just the author field of the results
        Collection<Page> pages = pageRelationalDataStore.query(pageTemplate, Arrays.asList("author"));

        for (Page page : pages) {
            authors.add(page.getAuthor());
        }
        return authors;
    }

    public Set<String> getKeywords(final String title) throws Exception {

        Set<String> keywords = new HashSet<String>();
        Page pageTemplate = new Page();
        pageTemplate.setTitle(title);

        // this will populate just the author field of the results
        Collection<Page> pages = pageRelationalDataStore.query(pageTemplate, Arrays.asList("keyword"));

        for (Page page : pages) {
            keywords.add(page.getKeyword());
        }
        return keywords;
    }

    public String getWikiText(final String title) throws Exception {

        // this should give us one result since page content is identical for all entries.
        Page pageTemplate = new Page();
        pageTemplate.setTitle(title);

        Collection<Page> pages = pageRelationalDataStore.query(pageTemplate, Arrays.asList("wikitext"));
        return pages.iterator().next().getWikitext();
    }

    public void add(final Page page) throws Exception {
        pageRelationalDataStore.insert(page);
    }

    public Set<Page> getAll() throws Exception {
        Page pageTemplate = new Page();
        return pageRelationalDataStore.query(pageTemplate, Arrays.asList("author", "keyword", "title", "wikitext"));
    }
}
