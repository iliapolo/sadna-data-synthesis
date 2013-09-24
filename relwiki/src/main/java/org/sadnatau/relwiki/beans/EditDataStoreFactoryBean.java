package org.sadnatau.relwiki.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static final String DATA = "edit/data.json";

    @Override
    public EditDataStore getObject() throws Exception {

        EditDataStore editDataStore = getEditDataStore();
        editDataStore.empty();

        // load the store with initial data
        Edit[] edits = new ObjectMapper()
                .readValue(Resources.toString(Resources.getResource(DATA), Charsets.UTF_8), Edit[].class);
        for (Edit edit : edits) {
            editDataStore.add(edit);
        }
        return editDataStore;


    }

    public EditDataStore getEditDataStore() throws Exception {
        String relationPath = Utils.extractResource(RELATION_PATH, "relation.txt").getAbsolutePath();
        String decompositionPath = Utils.extractResource(DECOMPOSITIONS_PATH, "decompositions.txt").getAbsolutePath();
        RelationalDataStore<Edit> dataStore = new RelationalDataStore<Edit>(relationPath, decompositionPath);
        return new EditDataStore(dataStore);
    }

    @Override
    public Class<?> getObjectType() {
        return EditDataStore.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
