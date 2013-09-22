package org.sadnatau.relwiki.model;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class Edit {

    private int revision;
    private String title;
    private int lineNumber;
    private String addedOrRemoved;
    private String text;

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getAddedOrRemoved() {
        return addedOrRemoved;
    }

    public void setAddedOrRemoved(String addedOrRemoved) {
        this.addedOrRemoved = addedOrRemoved;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
