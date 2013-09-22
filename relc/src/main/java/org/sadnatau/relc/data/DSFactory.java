package org.sadnatau.relc.data;

import org.sadnatau.relc.compiler.InstanceNode;

import java.util.List;

/**
 * 
 * A factory class with a factory function to create instances of different 
 * primitive key-value associative map data structures.
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 *
 */
public class DSFactory {

    /**
     * 
     * A factory method for creating instances of different 
     * primitive key-value associative map data structures.
     * 
     * @param ds an enum value of desired data structure
     * 
     * @return an instance of primitive key-value associative map data structure, by given input
     * 
     */
    public static KeyValueDataStructure<List<String>, InstanceNode> getDS(PrimitiveDS ds) {
    	
    	switch (ds) {
            case HTABLE:
                return new HashTable<>();
            case LIST:
                return new LinkedList<>();
            case VECTOR:
                return new Vector<>();
            case BTREE:
                return new BinaryTree<>();
        }
        throw new IllegalStateException("Unexpected DataStructure Type : " + ds);
    }
    
}
