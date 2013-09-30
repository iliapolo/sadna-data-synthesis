package org.sadnatau.relwiki.data;

import org.sadnatau.bridge.data.RelationalDataStore;
import org.sadnatau.relwiki.model.Page;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class PageDataStore {

    private RelationalDataStore<Page> pageRelationalDataStore;

    public PageDataStore(final RelationalDataStore<Page> dataStore) {
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
        Set<Page> pages = pageRelationalDataStore.query(pageTemplate, Arrays.asList("author"));

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
        Set<Page> pages = pageRelationalDataStore.query(pageTemplate, Arrays.asList("keyword"));

        for (Page page : pages) {
            keywords.add(page.getKeyword());
        }
        return keywords;
    }

    public String getWikiText(final String title) throws Exception {

        // this should give us one result since page content is identical for all entries.
        Page pageTemplate = new Page();
        pageTemplate.setTitle(title);
        String author = getAuthors(title).iterator().next();
        String keyword = getKeywords(title).iterator().next();
        pageTemplate.setAuthor(author);
        pageTemplate.setKeyword(keyword);

        Set<Page> pages = pageRelationalDataStore.query(pageTemplate, Arrays.asList("wikitext"));
        return pages.iterator().next().getWikitext();
    }

    public void add(final Page page) throws Exception {
        pageRelationalDataStore.insert(page);
    }

    public Set<String> getAll() throws Exception {
        Page pageTemplate = new Page();
        Set<Page> query =
                pageRelationalDataStore.query(pageTemplate, Arrays.asList("title"));
        Set<String> pageTitles = new HashSet<String>();
        for (Page page : query) {
            pageTitles.add(page.getTitle()) ;
        }
        return pageTitles;
    }

    public Set<String> getPagesForKeyword(String keyword) throws Exception {
        Set<String> result = new HashSet<String>();
        Page template = new Page();
        template.setKeyword(keyword);
        Set<Page> titles = pageRelationalDataStore.query(template, Arrays.asList("title"));
        for (Page page : titles) {
            result.add(page.getTitle());
        }
        return result;
    }

    public Set<String> getPagesForAuthor(String author) throws Exception {
        Set<String> result = new HashSet<String>();
        Page template = new Page();
        template.setAuthor(author);
        Set<Page> titles = pageRelationalDataStore.query(template, Arrays.asList("title"));
        for (Page page : titles) {
            result.add(page.getTitle());
        }
        return result;
    }

    public Set<Page> getPages(String author) throws Exception {
        Page template = new Page();
        template.setAuthor(author);
        return pageRelationalDataStore.query(template, Arrays.asList("title", "author"));
    }

    public Set<String> getAllKeywords() throws Exception {
        Page pageTemplate = new Page();
        Set<Page> query =
                pageRelationalDataStore.query(pageTemplate, Arrays.asList("keyword"));
        Set<String> pageKeywords = new HashSet<String>();
        for (Page page : query) {
            pageKeywords.add(page.getKeyword()) ;
        }
        return pageKeywords;
    }

    public Set<String> getAllAuthors() throws Exception {
        Page pageTemplate = new Page();
        Set<Page> query =
                pageRelationalDataStore.query(pageTemplate, Arrays.asList("author"));
        Set<String> pageAuthors = new HashSet<String>();
        for (Page page : query) {
            pageAuthors.add(page.getAuthor()) ;
        }
        return pageAuthors;
    }

    public void updateWikiText(String title, String newText) throws Exception {
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("wikitext", newText);

        Set<String> authors = getAuthors(title);
        Set<String> keywords = getKeywords(title);
        for (String author : authors) {
            // create a page for every title, author, keyword combination
            for (String keyword : keywords) {
                Page template = new Page();
                template.setTitle(title);
                template.setAuthor(author);
                template.setKeyword(keyword);
                pageRelationalDataStore.update(template, updates);
            }

        }
    }
}
