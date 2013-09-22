package org.sadnatau.relc.data;


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * 
 * An implementation of a balanced binary search tree key-value associative map data structure.
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 *
 */
public class BinaryTree<K,V> implements KeyValueDataStructure<K,V> {

	private TreeSet<Entry> ts;

	public BinaryTree() {
		this.ts = new TreeSet<>();
	}

    @Override
	public int size() {
		return this.ts.size();
	}

    @Override
	public boolean isEmpty() {
		return this.ts.isEmpty();
	}

    @Override
	public boolean containsKey(K key) {
		for (Entry e : this.ts) {
			if (e.getKey().equals(key)){
				return true;
			}
		}
		return false;
	}

    @Override
	public V get(K key) {
		for (Entry e : this.ts) {
			if (e.getKey().equals(key)){
				return e.getValue();
			}
		}
		return null;
	}

    @Override
	public void put(K key, V value) {
		for (Entry e : this.ts) {
			if (e.getKey().equals(key)){
				e.setValue(value);  //update value.
				return;
			}
		}
		Entry e = new Entry(key, value);
		this.ts.add(e);
	}

    @Override
	public void remove(K key) {
		for (Entry e : this.ts) {
			if (e.getKey().equals(key)) {
				this.ts.remove(e);
				return;
			}
		}
	}

	@Override
	public Set<K> keySet() {
		Set<K> ret = new HashSet<>();
		for (Entry e : this.ts) {
			ret.add(e.getKey());
		}
		return ret;
	}

    private class Entry implements Comparable<Entry> {

        private K key;
        private V value;

        public Entry(K key, V val) {
            this.key = key;
            this.value = val;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V val) {
            this.value = val;
        }

        @Override
        @SuppressWarnings("unchecked")
        public int compareTo(Entry o) {

            List<String> thisKey = (List<String>)(this.getKey());
            List<String> otherKey = (List<String>)(o.getKey());

            Collections.sort(thisKey);
            Collections.sort(otherKey);

            //comparing by lexicographic order of strings.
            for (int i = 0; i < thisKey.size(); i++) {
                if (thisKey.get(i).compareTo(otherKey.get(i)) != 0) {
                    return thisKey.get(i).compareTo(otherKey.get(i));
                }
            }
            if (thisKey.size() > otherKey.size()) {
                return 1;
            } else {
                return -1;
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object o) {

            return this.key.equals(((Entry)o).getKey());
        }

        @Override
        public String toString() {
            return "-- " + key + " --> " + value;
        }
    }


}
