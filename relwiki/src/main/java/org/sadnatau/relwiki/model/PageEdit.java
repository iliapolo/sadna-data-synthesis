package org.sadnatau.relwiki.model;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class PageEdit {

    // one author per edit
    private String author;

    // page content after edit
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
