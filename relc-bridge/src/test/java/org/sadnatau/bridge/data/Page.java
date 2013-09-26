package org.sadnatau.bridge.data;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class Page {

    private String keyword;
    private String wikitext;
    private String author;
    private String title;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getWikitext() {
        return wikitext;
    }

    public void setWikitext(String wikitext) {
        this.wikitext = wikitext;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        if (author != null ? !author.equals(page.author) : page.author != null) return false;
        if (keyword != null ? !keyword.equals(page.keyword) : page.keyword != null) return false;
        if (title != null ? !title.equals(page.title) : page.title != null) return false;
        if (wikitext != null ? !wikitext.equals(page.wikitext) : page.wikitext != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = keyword != null ? keyword.hashCode() : 0;
        result = 31 * result + (wikitext != null ? wikitext.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "keyword='" + keyword + '\'' +
                ", wikitext='" + wikitext + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
