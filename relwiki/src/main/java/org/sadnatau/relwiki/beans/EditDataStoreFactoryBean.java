package org.sadnatau.relwiki.beans;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.sadnatau.data.RelationalDataStore;
import org.sadnatau.relwiki.data.EditDataStore;
import org.sadnatau.relwiki.model.Edit;
import org.sadnatau.relwiki.utils.Utils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Component
public class EditDataStoreFactoryBean implements FactoryBean<EditDataStore> {

    private static final String RELATION_PATH = "edit/relation.txt";
    private static final String DECOMPOSITIONS_PATH = "edit/decompositions.txt";

    @Override
    public EditDataStore getObject() throws Exception {

        String relationPath = Utils.extractResource(RELATION_PATH, "relation.txt").getAbsolutePath();
        String decompositionPath = Utils.extractResource(DECOMPOSITIONS_PATH, "decompositions.txt").getAbsolutePath();
        RelationalDataStore dataStore = new RelationalDataStore(relationPath, decompositionPath);

        return new EditDataStore(dataStore);
    }

    @Override
    public Class<?> getObjectType() {
        return Edit.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
