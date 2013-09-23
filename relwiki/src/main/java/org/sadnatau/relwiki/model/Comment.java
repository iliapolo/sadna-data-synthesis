package org.sadnatau.relwiki.model;

import java.util.Date;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class Comment implements Comparable<Comment> {

    private String author;
    private String date;
    private String time;
    private String comment;
    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

        Comment comment1 = (Comment) o;

        if (author != null ? !author.equals(comment1.author) : comment1.author != null) return false;
        if (comment != null ? !comment.equals(comment1.comment) : comment1.comment != null) return false;
        if (date != null ? !date.equals(comment1.date) : comment1.date != null) return false;
        if (time != null ? !time.equals(comment1.time) : comment1.time != null) return false;
        if (title != null ? !title.equals(comment1.title) : comment1.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", comment='" + comment + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int compareTo(Comment other) {

        // compare two comments by date and time
        // this is to make it sortable.
        Date myDate = getDate(this);
        Date otherDate = getDate(other);

        return myDate.compareTo(otherDate);
    }

    private Date getDate(Comment comment) {
        String[] commentDate = comment.getDate().split("/");
        String[] commentTime = comment.getTime().split("-");
        Date date = new Date();
        date.setDate(Integer.valueOf(commentDate[0]));
        date.setMonth(Integer.valueOf(commentDate[1]));
        date.setYear(Integer.valueOf(commentDate[2]));
        date.setHours(Integer.valueOf(commentTime[0]));
        date.setMinutes(Integer.valueOf(commentTime[1]));
        date.setSeconds(Integer.valueOf(commentTime[2]));
        return date;
    }
}
