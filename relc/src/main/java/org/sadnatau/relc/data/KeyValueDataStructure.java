package org.sadnatau.relc.data;

import java.util.Set;

/**
 * An interface of primitive key-value associative map data structure.
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 *
 */
public interface KeyValueDataStructure<K,V> {

	
	/**
	 * Returns the number of key-value mappings in this ds. 
	 * 
	 * @return number of elements (key-value pairs) in ds
	 */
	public int size();
	
	
	/**
	 * Returns true if this ds contains no key-value mappings.
	 * 
	 * @return true iff ds is empty
	 */
	public boolean isEmpty();
	
	
	/**
	 * Returns true if this ds contains a mapping for the specified key.
	 * 
	 * @param key the key to look for
	 * 
	 * @return true iff ds containg key
	 */
	public boolean containsKey(K key);
	
	
	/**
	 * Returns the value to which the specified key is mapped, 
	 * or null if this ds contains no mapping for the key.
	 * 
	 * @param key the key whose value we want to get
	 * 
	 * @return key's value, if found (null otherwise)
	 */
	public V get(K key);
	

	/**
	 * Associates the specified value with the specified key in this ds.
	 * If the ds previously contained a mapping for the key, 
	 * the old value is replaced by the specified value.
	 * 
	 * @param key the key to put
	 * @param value the value of the key
	 */
	public void put(K key, V value);
	
	
	/**
	 * Removes the mapping for a key from this ds if it is present. 
	 * 
	 * @param key the key to remove
	 * 
	 */
	public void remove(K key);
	
	
	/**
	 * Returns a set view of the keys contained in this ds.
	 * 
	 * @return set view of the keys contained in this ds
	 */
	public Set<K> keySet();
	
}
