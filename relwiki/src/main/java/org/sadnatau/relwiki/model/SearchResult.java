package org.sadnatau.relwiki.model;

import java.util.List;

/**
 * POJO for representing a search query result.
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class SearchResult {

    private List<String> keywords;
    private List<String> authors;
    private List<String> pages;

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(final List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(final List<String> authors) {
        this.authors = authors;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(final List<String> pages) {
        this.pages = pages;
    }
}
