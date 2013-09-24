package org.sadnatau.relwiki.transport.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class PageEdit {

    private String currentText;
    private String author;
    private List<String> keywords = new ArrayList<String>();

    public String getCurrentText() {
        return currentText;
    }

    public void setCurrentText(String currentText) {
        this.currentText = currentText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
