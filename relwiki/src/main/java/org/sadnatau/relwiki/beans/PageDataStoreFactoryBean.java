package org.sadnatau.relwiki.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.sadnatau.bridge.data.RelationalDataStore;
import org.sadnatau.relwiki.data.PageDataStore;
import org.sadnatau.relwiki.model.Edit;
import org.sadnatau.relwiki.model.Page;
import org.sadnatau.relwiki.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.net.URL;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Component
public class PageDataStoreFactoryBean implements FactoryBean<PageDataStore> {

    private Logger logger = LoggerFactory.getLogger(CommentDataStoreFactoryBean.class);


    private static final String RELATION_PATH = "page/relation.txt";
    private static final String DECOMPOSITIONS_PATH = "page/decompositions.txt";
    private static final String DATA = "page/data.json";

    @Override
    public PageDataStore getObject() throws Exception {

        logger.debug("Creating a relational data store for object [" + Page.class + "]");

        PageDataStore pageDataStore = getPageDataStore();
        pageDataStore.empty();

        URL resource = Resources.getResource(DATA);
        logger.debug("Loading store with initial data from resource " + resource);

        Page[] pages = new ObjectMapper()
                .readValue(Resources.toString(resource, Charsets.UTF_8), Page[].class);
        for (Page page : pages) {
            pageDataStore.add(page);
        }
        return pageDataStore;
    }

    public PageDataStore getPageDataStore() throws Exception {
        String relationPath = Utils.extractResource(RELATION_PATH, "relation.txt").getAbsolutePath();
        String decompositionPath = Utils.extractResource(DECOMPOSITIONS_PATH, "decompositions.txt").getAbsolutePath();
        RelationalDataStore<Page> dataStore = new RelationalDataStore<Page>(relationPath, decompositionPath);
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
