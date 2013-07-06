package org.sadnatau.relwiki.model;

import java.util.List;

/**
 * A Query template for result matching.
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class QueryTemplate {

    /**
     * List of keywords belonging to the desired page.
     */
    private List<String> keywords;

    /**
     * Currently support just one author.
     * // TODO - What do multiple authors mean? OR? AND?
     */
    private String author;

    public List<String> getKeywords() {
        return keywords;
    }

    public String getAuthor() {
        return author;
    }

    public void setKeywords(final List<String> keywords) {
        this.keywords = keywords;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }
}
