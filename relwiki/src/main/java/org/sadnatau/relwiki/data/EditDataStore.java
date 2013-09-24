package org.sadnatau.relwiki.data;

import org.sadnatau.data.RelationalDataStore;
import org.sadnatau.relwiki.model.Edit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class EditDataStore {

    private RelationalDataStore<Edit> editRelationalDataStore;

    public EditDataStore(final RelationalDataStore<Edit> dataStore) {
        this.editRelationalDataStore = dataStore;
    }

    public void empty() {
        editRelationalDataStore.empty();
    }

    public void add(Edit edit) throws Exception {
        editRelationalDataStore.insert(edit);
    }

    public Set<Integer> getAllRevisions(String title) throws Exception {

        Edit template = new Edit();
        template.setTitle(title);
        Set<Edit> query = editRelationalDataStore.query(template, Arrays.asList("revision"));
        Set<Integer> revisions = new HashSet<Integer>();
        for (Edit edit : query) {
            revisions.add(Integer.valueOf(edit.getRevision()));
        }

        return revisions;
    }

    public Set<Edit> getAll() throws Exception {
        Edit template = new Edit();
        return editRelationalDataStore.query(template, Arrays.asList("revision", "title", "lineNumber",
                "addedOrRemoved", "text"));
    }

    public Set<Edit> getAllForRevisionAndTitle(String revisionNumber, String title) throws Exception {
        Edit template = new Edit();
        template.setRevision(revisionNumber);
        template.setTitle(title);
        Set<Edit> query = editRelationalDataStore.query(template, Arrays.asList("lineNumber",
                "addedOrRemoved", "text"));
        for (Edit edit : query) {
            edit.setRevision(revisionNumber);
            edit.setTitle(title);
        }
        return query;
    }
}
