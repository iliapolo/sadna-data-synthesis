package org.sadnatau.relwiki.beans;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.sadnatau.data.RelationalDataStore;
import org.sadnatau.relwiki.data.CommentDataStore;
import org.sadnatau.relwiki.model.Comment;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Component
public class CommentDataStoreFactoryBean implements FactoryBean<CommentDataStore> {

    private static final String RELATIONS_PATH = "comment/relation.txt";
    private static final String DECOMPOSITIONS_PATH = "comment/decompositions.txt";

    @Override
    public CommentDataStore getObject() throws Exception {

        String relationsString = Resources.toString(Resources.getResource(RELATIONS_PATH), Charsets.UTF_8);
        String decompositionsString = Resources.toString(Resources.getResource(DECOMPOSITIONS_PATH), Charsets.UTF_8);
        RelationalDataStore dataStore = new RelationalDataStore(relationsString, decompositionsString);

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
