package org.sadnatau.relc.inputparser;

import java.util.List;

public class RelationSpecParser {

	private String relationFilePath;
	
	/**
	 * Constructor.
	 * 
	 * @param relFilePath the path to relational specification file 
	 */
	public RelationSpecParser(String relFilePath) {
		
		this.relationFilePath = relFilePath;
	}
	
	/*
	 * 
	 * returns true iff set1 is a subset of set2.
	 * 
	 */
	private boolean isSubset(List<String> set1, List<String> set2) {
		
		for (String el : set1) {
			
			if (!set2.contains(el)) {
				return false;
			}
		}
		
		return true;
	}

}
