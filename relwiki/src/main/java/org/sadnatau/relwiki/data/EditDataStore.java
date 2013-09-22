package org.sadnatau.relwiki.data;

import org.sadnatau.data.RelationalDataStore;
import org.sadnatau.relwiki.model.Edit;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class EditDataStore {

    private RelationalDataStore<Edit> editRelationalDataStore;

    public EditDataStore(final RelationalDataStore dataStore) {
        this.editRelationalDataStore = dataStore;
    }

}
