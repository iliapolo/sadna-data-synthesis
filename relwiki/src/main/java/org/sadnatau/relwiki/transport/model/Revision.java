package org.sadnatau.relwiki.transport.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Eli Polonsky
 * @since 0.1
 */
public class Revision implements Comparable<Revision> {

    private Set<Change> changes;
    private int number;

    public void setChanges(Set<Change> changes) {
        this.changes = changes;
    }

    public Set<Change> getChanges() {
        return changes;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Change> getAdded() {
        List<Change> added = new ArrayList<Change>();
        for (Change change : changes) {
            if (change.getAddedOrRemoved().trim().toLowerCase().equals("added")) {
                added.add(change);
            }
        }
        return added;
    }


    public List<Change> getRemoved() {
        List<Change> removed = new ArrayList<Change>();
        for (Change change : changes) {
            if (change.getAddedOrRemoved().trim().toLowerCase().equals("removed")) {
                removed.add(change);
            }
        }
        return removed;
    }

    @Override
    public int compareTo(Revision o) {
        if (getNumber() == o.getNumber()) return 0;
        if (getNumber() > o.getNumber()) return 1;
        return -1;
    }
}
