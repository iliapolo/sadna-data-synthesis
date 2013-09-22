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

    private CommentDataStore createDataStore() throws Exception {
        CommentDataStoreFactoryBean bean = new CommentDataStoreFactoryBean();
        CommentDataStore dataStore = bean.getObject();
        dataStore.empty();
        return dataStore;
    }

}
