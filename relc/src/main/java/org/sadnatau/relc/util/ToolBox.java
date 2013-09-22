package org.sadnatau.relc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * ToolBox calls with misc. utility functions. 
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 *
 */
public class ToolBox {

	
	/**
	 * 
	 * Gets list of names of columns and tuple with values for all col. names,
	 * and returns list of name:value just for given columns names. 
	 * 
	 * @param colNames list of names of columns
	 * @param colValues tuple with values (for all col. names and maybe more)
	 * 
	 * @return list of name:value just for given columns names
	 */
	public static List<String> neededColValues(List<String> colNames, List<String> colValues) {
		List<String> ret = new ArrayList<>();
		for (String nam : colNames) {
			for (String val : colValues) {
				if (val.startsWith(nam + ":")) {
					ret.add(val);
					break;
				}
			}
		}
		return ret;
	}
		
	
	/**
	 * Returns list of columns names of a tuple.
	 * 
	 * @param tuple a tuple
	 * @return list of columns names of a tuple
	 * 
	 */
	public static List<String> getColNamesOfTuple(List<String> tuple) {
		List<String> ret = new ArrayList<String>();
		for (String s: tuple) {
			String[] arr = s.split(":");
			ret.add(arr[0]);
		}
		return ret;
	}

	/**
	 * Returns true iff s1 and s2 are disjoint sets.
	 * 
	 * @param s1 the 1st set
	 * @param s2 the 2nd set
	 * 
	 * @returns true iff s1 and s2 are disjoint sets
	 */
	public static boolean isDisjointSets(List<String> s1, List<String> s2) {
		for (String a : s1) {
			if (s2.contains(a)) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Returns true iff s1 subset of s2.
	 * 
	 * @param s1 the 1st set
	 * @param s2 the 2nd set
	 * 
	 * @returns true iff s1 subset of s2
	 */
	public static boolean isSubSet(List<String> s1, List<String> s2) {
		for (String a : s1) {
			if (!s2.contains(a)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the union of the given 2 sets.
	 * 
	 * @param s1 the 1st set
	 * @param s2 the 2nd set
	 * 
	 * @returns the union of the two sets
	 */
	public static List<String> setsUnion(List<String> s1, List<String> s2) {
		List<String> ret = new ArrayList<String>(s2);
		for (String a : s1) {
			if (!ret.contains(a)) {
				ret.add(a);
			}
		}
		return ret;
	}

	/**
	 * 
	 * Merges two tuples.
	 * 
	 * @param s the 1st tuple
	 * @param t the 2nd tuple
	 * 
	 * @return the merged tuple, or null if tuples not match.
	 */
	public static List<String> margeTuples(List<String> s, List<String> t) {
		if (!tuplesMatch(s, t)) {
			return null;
		}
		List<String> ret = new ArrayList<String>();
		ret.addAll(t);
		for (String str : s) {
			if (!t.contains(str)) {
				ret.add(str);
			}
		}
		return ret;
	}

	/**
	 * Returns true iff s and t have same values on common columns.
	 * 
	 * @param s the 1st tuple
	 * @param t the 2nd tuple
	 * 
	 * @return true iff s and t have same values on common columns
	 */
	public static boolean tuplesMatch(List<String> s, List<String> t) {
		String[] colNameVal;
		for (String str : s) {
			colNameVal = str.split(":");  //column name and value two elem. array.
			for (String el : t) {
				if (el.startsWith(colNameVal[0] + ":")) {  //common col. for s and t.
					String[] tmp = el.split(":");
					if (!colNameVal[1].equals(tmp[1])) {  //have different value on same col. name.
						return false;
					}
					break;  //no need to continue iterate on t.
				}
			}
		}
		return true;
	}

	/**
	 * Returns the natural join of two sets of tuples.
	 * 
	 * @param s 1st set of tuples
	 * @param t 2nd set of tuples
	 * 
	 * @return the natural join of the two sets of tuples
	 */
	public static List<List<String>> naturalJoin(List<List<String>> s, List<List<String>> t) {
		if (s.isEmpty()) {
			return t;
		}
		if (t.isEmpty()) {
			return s;
		}
		List<List<String>> ret = new ArrayList<>();
		for (List<String> tup1 : s) {
			for (List<String> tup2 : t) {
				List<String> mrg = margeTuples(tup1, tup2);
				if (mrg != null) {  //only if tuples match.
					ret.add(mrg);
				}
			}
		}
		return ret;
	}


    /**
     * Updates values of columns in left tuple with according values in right tuple
     * (set of columns of right tuple must be sub-set of left).
     *
     * @param toBeUpdated the tuple to be updated
     * @param theUpdate the updating tuple
     */
    public static void updateTuple(List<String> toBeUpdated, List<String> theUpdate) {

        List<String> rightNames = ToolBox.getColNamesOfTuple(theUpdate);
        List<String> tmp = new ArrayList<>(toBeUpdated);

        for (String s : tmp) {
            for (String el : rightNames) {
                if (s.startsWith(el + ":")) {
                    toBeUpdated.remove(s);  // removing cols. from toBeUpdated that appear in theUpdate.
                    break;
                }
            }
        }
        toBeUpdated.addAll(theUpdate);
    }
}
