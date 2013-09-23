package org.sadnatau.relwiki.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.sadnatau.data.RelationalDataStore;
import org.sadnatau.relwiki.data.CommentDataStore;
import org.sadnatau.relwiki.model.Comment;
import org.sadnatau.relwiki.utils.Utils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Component
public class CommentDataStoreFactoryBean implements FactoryBean<CommentDataStore> {

    private static final String RELATION_PATH = "comment/relation.txt";
    private static final String DECOMPOSITIONS_PATH = "comment/decompositions.txt";
    private static final String DATA = "comment/data.json";

    @Override
    public CommentDataStore getObject() throws Exception {

        CommentDataStore commentDataStore = getCommentDataStore();
        commentDataStore.empty();

        // load the store with initial data
        Comment[] comments = new ObjectMapper()
                .readValue(Resources.toString(Resources.getResource(DATA), Charsets.UTF_8), Comment[].class);
        for (Comment comment : comments) {
            commentDataStore.add(comment);
        }
        return commentDataStore;
    }

    public CommentDataStore getCommentDataStore() throws Exception {
        String relationPath = Utils.extractResource(RELATION_PATH, "relation.txt").getAbsolutePath();
        String decompositionPath = Utils.extractResource(DECOMPOSITIONS_PATH, "decompositions.txt").getAbsolutePath();
        RelationalDataStore dataStore = new RelationalDataStore(relationPath, decompositionPath);
        CommentDataStore commentDataStore = new CommentDataStore(dataStore);
        return commentDataStore;
    }

    @Override
    public Class<?> getObjectType() {
        return CommentDataStore.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
