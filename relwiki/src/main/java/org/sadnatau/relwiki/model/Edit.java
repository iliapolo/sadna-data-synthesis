package org.sadnatau.relwiki.model;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class Edit {

    private String revision;
    private String title;
    private String lineNumber;
    private String addedOrRemoved;
    private String text;

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edit edit = (Edit) o;

        if (addedOrRemoved != null ? !addedOrRemoved.equals(edit.addedOrRemoved) : edit.addedOrRemoved != null)
            return false;
        if (lineNumber != null ? !lineNumber.equals(edit.lineNumber) : edit.lineNumber != null) return false;
        if (revision != null ? !revision.equals(edit.revision) : edit.revision != null) return false;
        if (text != null ? !text.equals(edit.text) : edit.text != null) return false;
        if (title != null ? !title.equals(edit.title) : edit.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = revision != null ? revision.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (lineNumber != null ? lineNumber.hashCode() : 0);
        result = 31 * result + (addedOrRemoved != null ? addedOrRemoved.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Edit{" +
                "revision='" + revision + '\'' +
                ", title='" + title + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                ", addedOrRemoved='" + addedOrRemoved + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
