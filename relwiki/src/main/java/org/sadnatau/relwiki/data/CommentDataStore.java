package org.sadnatau.relwiki.data;

import org.sadnatau.data.RelationalDataStore;
import org.sadnatau.relwiki.model.Comment;

import java.util.Arrays;
import java.util.Set;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class CommentDataStore {

    private RelationalDataStore<Comment> commentRelationalDataStore;

    public CommentDataStore(final RelationalDataStore<Comment> dataStore) {
        this.commentRelationalDataStore = dataStore;
    }

    public void empty() {
        commentRelationalDataStore.empty();
    }

    public Set<Comment> getAll(final String title) throws Exception {
        Comment commentTemplate = new Comment();
        commentTemplate.setTitle(title);

        // TODO - ask evgeny why this query doesnt not work when passing the 'title' in the result field
        // see CommentDataStoreTest#testGetAllByTitle
        Set<Comment> query =
                commentRelationalDataStore.query(commentTemplate, Arrays.asList("author", "date", "time", "comment"));
        for (Comment comment : query) {
            comment.setTitle(title);
        }
        return query;
    }

    public Set<Comment> getAll() throws Exception {
        Comment comment = new Comment();
        return commentRelationalDataStore.query(comment, Arrays.asList("author", "date", "time", "comment", "title"));
    }

    public void add(final Comment comment) throws Exception {
        commentRelationalDataStore.insert(comment);
    }
}
