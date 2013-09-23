package org.sadnatau.relwiki.transport.model;

import java.util.List;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class NewPage {

    private String title;
    private String author;
    private String wikitext;
    private List<String> keywords;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWikitext() {
        return wikitext;
    }

    public void setWikitext(String wikitext) {
        this.wikitext = wikitext;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
