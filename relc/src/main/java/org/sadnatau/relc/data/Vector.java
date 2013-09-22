package org.sadnatau.relc.data;

import java.util.HashSet;
import java.util.Set;


/**
 * 
 * An implementation of a vector (dynamic array) key-value associative map data structure.
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 *
 */
public class Vector<K,V> implements KeyValueDataStructure<K,V> {

	private java.util.Vector<Entry> arr;

	public Vector() {
		this.arr = new java.util.Vector<>();
	}

    @Override
	public int size() {
		return this.arr.size();
	}

    @Override
	public boolean isEmpty() {
		return this.arr.isEmpty();
	}

    @Override
	public boolean containsKey(K key) {
		for (Entry e : this.arr) {
			if (e.getKey().equals(key)){
				return true;
			}
		}
		return false;
	}

    @Override
	public V get(K key) {
		for (Entry e : this.arr) {
			if (e.getKey().equals(key)){
				return e.getValue();
			}
		}
		return null;
	}

    @Override
	public void put(K key, V value) {
		for (Entry e : this.arr) {
			if (e.getKey().equals(key)){
				e.setValue(value);
				return;
			}
		}
		Entry e = new Entry(key, value);
		this.arr.add(e);
	}

    @Override
	public void remove(K key) {
		for (Entry e : this.arr) {
			if (e.getKey().equals(key)) {
				this.arr.remove(e);
				return;
			}
		}
	}
	
    @Override
	public Set<K> keySet() {
		Set<K> ret = new HashSet<>();
		for (Entry e : this.arr) {
			ret.add(e.getKey());
		}
		return ret;
	}

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
