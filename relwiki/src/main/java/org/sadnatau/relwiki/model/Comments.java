package org.sadnatau.relwiki.model;

import java.util.List;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class Comments {

    private Page page;
    private List<Comment> comments;

    public Comments(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
