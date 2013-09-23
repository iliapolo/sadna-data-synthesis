package org.sadnatau.relwiki.data;

import junit.framework.Assert;
import org.junit.Test;
import org.sadnatau.relwiki.beans.CommentDataStoreFactoryBean;
import org.sadnatau.relwiki.model.Comment;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests functionality of {@link CommentDataStore}
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class CommentDataStoreTest {

    @Test
    public void testGetAll() throws Exception {

        CommentDataStore dataStore = createDataStore();

        Set<Comment> expected = new HashSet<Comment>();
        for (int i = 0; i < 5; i++) {
            Comment comment = new Comment();
            comment.setTitle("title" + i);
            dataStore.add(comment);
            expected.add(comment);
        }
        Set<Comment> all = dataStore.getAll();
        Assert.assertEquals(expected, all);
    }

    @Test
    public void testGetAllByTitle() throws Exception {

        CommentDataStore dataStore = createDataStore();

        Comment comment = new Comment();
        comment.setTitle("title");
        comment.setDate("date1");
        comment.setAuthor("author1");

        Comment comment1 = new Comment();
        comment1.setTitle("title");
        comment1.setDate("date2");
        comment1.setAuthor("author2");

        Comment comment2 = new Comment();
        comment2.setTitle("title1");
        comment2.setDate("date3");
        comment2.setAuthor("author2");

        dataStore.add(comment);

        dataStore.add(comment1);
        dataStore.add(comment2);

        Set<Comment> expected = new HashSet<Comment>();
        expected.add(comment);
        expected.add(comment1);

        Set<Comment> comments = dataStore.getAll("title");
        Assert.assertEquals(expected, comments);


    }

    private CommentDataStore createDataStore() throws Exception {
        CommentDataStoreFactoryBean bean = new CommentDataStoreFactoryBean();
        CommentDataStore dataStore = bean.getObject();
        dataStore.empty();
        return dataStore;
    }

}
