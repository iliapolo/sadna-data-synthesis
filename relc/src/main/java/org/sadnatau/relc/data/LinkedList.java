package org.sadnatau.relc.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * An implementation of a linked list key-value associative map data structure.
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 *
 */
public class LinkedList<K,V> implements KeyValueDataStructure<K,V> {

	private List<Entry> lst;

	public LinkedList() {
		this.lst = new java.util.LinkedList<>();
	}
	
    @Override
	public int size() {
		return this.lst.size();
	}

    @Override
	public boolean isEmpty() {
		return this.lst.isEmpty();
	}

    @Override
	public boolean containsKey(K key) {
		for (Entry e : this.lst) {
			if (e.getKey().equals(key)){
				return true;
			}
		}
		return false;
	}
	
    @Override
	public V get(K key) {
		for (Entry e : this.lst) {
			if (e.getKey().equals(key)){
				return e.getValue();
			}
		}
		return null;
	}

    @Override
	public void put(K key, V value) {
		for (Entry e : this.lst) {
			if (e.getKey().equals(key)){
				e.setValue(value);
				return;
			}
		}
		Entry e = new Entry(key, value);
		this.lst.add(e);
	}

    @Override
	public void remove(K key) {
		for (Entry e : this.lst) {
			if (e.getKey().equals(key)) {
				this.lst.remove(e);
				return;
			}
		}
	}
	
    @Override
	public Set<K> keySet() {
		Set<K> ret = new HashSet<>();
		for (Entry e : this.lst) {
			ret.add(e.getKey());
		}
		return ret;
	}
	
	@Override
	public String toString() {
		
		return lst.toString();
	}

    /**
     * a class for linked list key-value entry pair.
     */
    private class Entry {

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

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object o) {
            return this.key.equals(((Entry)o).getKey());
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "-- " + key + " --> " + value;
        }
    }
}
