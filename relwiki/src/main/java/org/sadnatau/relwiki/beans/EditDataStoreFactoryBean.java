package org.sadnatau.relwiki.beans;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.sadnatau.data.RelationalDataStore;
import org.sadnatau.relwiki.data.EditDataStore;
import org.sadnatau.relwiki.model.Edit;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
@Component
public class EditDataStoreFactoryBean implements FactoryBean<EditDataStore> {

    private static final String RELATIONS_PATH = "edit/relation.txt";
    private static final String DECOMPOSITIONS_PATH = "edit/decompositions.txt";

    @Override
    public EditDataStore getObject() throws Exception {

        String relationsString = Resources.toString(Resources.getResource(RELATIONS_PATH), Charsets.UTF_8);
        String decompositionsString = Resources.toString(Resources.getResource(DECOMPOSITIONS_PATH), Charsets.UTF_8);
        RelationalDataStore dataStore = new RelationalDataStore(relationsString, decompositionsString);

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
