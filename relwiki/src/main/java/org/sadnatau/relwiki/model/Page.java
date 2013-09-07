package org.sadnatau.relwiki.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class Page {

    /**
     * Page content.
     */
    private String content = "";

    /**
     * authors of this page.
     */
    private List<String> authors = new ArrayList<String>();

    /**
     * Page title.
     */
    private String title = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
