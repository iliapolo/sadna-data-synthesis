package org.sadnatau.relwiki.model;

import java.util.Date;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class Comment {

    private String name;
    private Date time = new Date(System.currentTimeMillis());
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
