package org.sadnatau.relwiki.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Query template for result matching.
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class QueryTemplate {

    /**
     * List of keywords to search by.
     */
    private List<String> keywords = new ArrayList<>();

    /**
     * List of authors to search by.
     *
     */
    private List<String> authors = new ArrayList<>();

    public List<String> getKeywords() {
        return keywords;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setKeywords(final List<String> keywords) {
        this.keywords = keywords;
    }

    public void setAuthors(final List<String> authors) {
        this.authors = authors;
    }
}
