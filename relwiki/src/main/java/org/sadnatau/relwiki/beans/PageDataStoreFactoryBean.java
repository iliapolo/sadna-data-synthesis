package org.sadnatau.relwiki.beans;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.sadnatau.data.RelationalDataStore;
import org.sadnatau.relwiki.data.PageDataStore;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Component
public class PageDataStoreFactoryBean implements FactoryBean<PageDataStore> {

    private static final String RELATIONS_PATH = "page/relation.txt";
    private static final String DECOMPOSITIONS_PATH = "page/decompositions.txt";

    @Override
    public PageDataStore getObject() throws Exception {

        String relationsString = Resources.toString(Resources.getResource(RELATIONS_PATH), Charsets.UTF_8);
        String decompositionsString = Resources.toString(Resources.getResource(DECOMPOSITIONS_PATH), Charsets.UTF_8);
        RelationalDataStore dataStore = new RelationalDataStore(relationsString, decompositionsString);

        return new PageDataStore(dataStore);
    }

    @Override
    public Class<?> getObjectType() {
        return PageDataStore.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
