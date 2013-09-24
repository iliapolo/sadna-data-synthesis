package org.sadnatau.relwiki.transport.model;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class Change {

    private int lineNumber;
    private String text;
    private String addedOrRemoved;

    public String getAddedOrRemoved() {
        return addedOrRemoved;
    }

    public void setAddedOrRemoved(String addedOrRemoved) {
        this.addedOrRemoved = addedOrRemoved;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
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

        Change change = (Change) o;

        if (lineNumber != change.lineNumber) return false;
        if (addedOrRemoved != null ? !addedOrRemoved.equals(change.addedOrRemoved) : change.addedOrRemoved != null)
            return false;
        if (text != null ? !text.equals(change.text) : change.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lineNumber;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (addedOrRemoved != null ? addedOrRemoved.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Change{" +
                "lineNumber=" + lineNumber +
                ", text='" + text + '\'' +
                ", addedOrRemoved='" + addedOrRemoved + '\'' +
                '}';
    }
}
