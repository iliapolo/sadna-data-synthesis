package org.sadnatau.relwiki.beans;

import org.junit.Test;
import org.sadnatau.relwiki.data.PageDataStore;
import org.sadnatau.relwiki.model.Page;

import java.util.Set;

/**
 * This class tests the ability to create a data store for pages from the input files.
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class PageDataStoreFactoryBeanTest {

    @Test
    public void testGetObject() throws Exception {
        new PageDataStoreFactoryBean().getObject();
    }
}
