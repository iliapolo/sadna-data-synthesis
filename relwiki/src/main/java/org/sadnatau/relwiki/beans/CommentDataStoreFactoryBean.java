package org.sadnatau.relwiki.beans;

import org.sadnatau.data.RelationalDataStore;
import org.sadnatau.relwiki.data.CommentDataStore;
import org.sadnatau.relwiki.model.Comment;
import org.sadnatau.relwiki.utils.Utils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Component
public class CommentDataStoreFactoryBean implements FactoryBean<CommentDataStore> {

    private static final String RELATION_PATH = "comment/relation.txt";
    private static final String DECOMPOSITIONS_PATH = "comment/decompositions.txt";

    @Override
    public CommentDataStore getObject() throws Exception {

        String relationPath = Utils.extractResource(RELATION_PATH, "relation.txt").getAbsolutePath();
        String decompositionPath = Utils.extractResource(DECOMPOSITIONS_PATH, "decompositions.txt").getAbsolutePath();
        RelationalDataStore dataStore = new RelationalDataStore(relationPath, decompositionPath);

        return new CommentDataStore(dataStore);
    }

    @Override
    public Class<?> getObjectType() {
        return Comment.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
