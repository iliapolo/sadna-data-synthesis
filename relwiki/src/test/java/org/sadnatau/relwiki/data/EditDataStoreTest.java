package org.sadnatau.relwiki.data;

import junit.framework.Assert;
import org.junit.Test;
import org.sadnatau.relwiki.beans.EditDataStoreFactoryBean;
import org.sadnatau.relwiki.model.Edit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class EditDataStoreTest {

    @Test
    public void testGetAllRevisions() throws Exception {

        EditDataStore dataStore = createDataStore();

        Edit edit = new Edit();
        edit.setRevision("2");
        edit.setTitle("Union Square, Boston");

        Edit edit1 = new Edit();
        edit1.setRevision("3");
        edit1.setTitle("Union Square, Boston");

        dataStore.add(edit);
        dataStore.add(edit1);

        Set<Integer> expected = new HashSet<Integer>(Arrays.asList(2, 3));
        Set<Integer> allRevisions = dataStore.getAllRevisions("Union Square, Boston");
        Assert.assertEquals(expected, allRevisions);
    }

    @Test
    public void testGetAllForRevision() throws Exception {

        EditDataStore dataStore = createDataStore();

        Edit edit = new Edit();
        edit.setRevision("2");
        edit.setTitle("title");
        edit.setAddedOrRemoved("added");

        Edit edit1 = new Edit();
        edit1.setRevision("2");
        edit1.setTitle("title");
        edit1.setAddedOrRemoved("removed");

        Edit edit2 = new Edit();
        edit2.setRevision("3");
        edit2.setTitle("title");
        edit2.setAddedOrRemoved("removed");

        dataStore.add(edit);
        dataStore.add(edit1);
        dataStore.add(edit2);

        Set<Edit> expected = new HashSet<Edit>();
        expected.add(edit);
        expected.add(edit1);

        Set<Edit> allForRevision = dataStore.getAllForRevisionAndTitle("2", "title");
        Assert.assertEquals(expected, allForRevision);
    }

    private EditDataStore createDataStore() throws Exception {
        EditDataStoreFactoryBean bean = new EditDataStoreFactoryBean();
        EditDataStore dataStore = bean.getEditDataStore();
        dataStore.empty();
        return dataStore;
    }

}
