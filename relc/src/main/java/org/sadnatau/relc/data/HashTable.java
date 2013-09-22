package org.sadnatau.relc.data;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;


/**
 * 
 * An implementation of a hash table key-value associative map data structure.
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 *
 */
public class HashTable<K,V> implements KeyValueDataStructure<K,V> {

	private Hashtable<K,V> htable;

	public HashTable() {
		this.htable = new Hashtable<>();
	}
	
	
    @Override
	public int size() {
		return this.htable.size();
	}


    @Override
    public boolean isEmpty() {
		return this.htable.isEmpty();
	}


    @Override
	public boolean containsKey(K key) {
		return this.htable.containsKey(key);
	}

    @Override
	public V get(K key) {
		return this.htable.get(key);
	}

    @Override
	public void put(K key, V value) {
		this.htable.put(key, value);
	}

    @Override
	public Set<K> keySet() {
		return this.htable.keySet();
	}

    @Override
	public void remove(K key) {
		this.htable.remove(key);
	}

	@Override
	public String toString() {
		String s = "[";
		for (Map.Entry<K,V> se : htable.entrySet()) {
			s = s + "-- " + se.getKey() + " --> " + se.getValue() + ", "; 
		}
		if (s.length() >= 2) {
			s = s.substring(0, s.length()-2);
		}
		s = s+ "]";
		return s;
	}
}
