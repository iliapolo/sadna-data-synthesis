package org.sadnatau.relc.compiler;

import org.sadnatau.relc.data.DSFactory;
import org.sadnatau.relc.data.KeyValueDataStructure;
import org.sadnatau.relc.data.PrimitiveDS;
import org.sadnatau.relc.util.ToolBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * A class of a node in a decomposition instance.
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 * 
 */
public class InstanceNode {

	// will be the same as in decomposition graph (we'll have many instances with same name).
	private String name;
	
	// maps an adjacent vertex (by its name from decomposition graph) to its mapping ds of the decomp. instance.
	// for example: we have an edge from x to y (in decomp. graph), with column ns and htable,
	// then in map of InstanceNode object of x, key y will be mapped to htable (with ns as a key).
	private Map<String, KeyValueDataStructure<List<String>,InstanceNode>> adjacent;
	
	// list of values of columns for sink node.
	private List<String> sinkNodeValues;
	
	
	public InstanceNode(String name) {
		this.name = name;
		this.adjacent = new HashMap<>();
		this.sinkNodeValues = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	public List<String> getSinkNodeValues() {
		return this.sinkNodeValues;
	}

	public void addSinkNodeValues(List<String> vals) {
		if (this.sinkNodeValues.isEmpty()) {
			Collections.sort(vals);
			this.sinkNodeValues.addAll(vals);
		}
	}

	public boolean isSinkNode() {
		return (!this.sinkNodeValues.isEmpty());
	}

	
	/**
	 * 
	 * 
	 * @param adjNodeName name of the adj. node
	 * @param mapKeyColumns key for mapping to adj. node
	 * @param ds the mapping ds
	 * @param add the instance of adj. node to add, or null if we must create a new one.
	 * 
	 * @return the instance node that was added to the decomposition instance
	 */
	public InstanceNode addAdjacent(String adjNodeName, List<String> mapKeyColumns, PrimitiveDS ds, InstanceNode add) {
		Collections.sort(mapKeyColumns);
		InstanceNode ret = add == null ? new InstanceNode(adjNodeName) : add;
		if (!this.adjacent.containsKey(adjNodeName)) {  //name of adj. vertex still not in main map.
			
			// init. key-value ds.
			KeyValueDataStructure<List<String>, InstanceNode> toAdd = DSFactory.getDS(ds);
			toAdd.put(mapKeyColumns, ret);
			this.adjacent.put(adjNodeName, toAdd);
			return ret;
		} 
		
		// getting ds of adj. vertex from main map.
		KeyValueDataStructure<List<String>,InstanceNode> d = this.adjacent.get(adjNodeName);
		
		if (d.containsKey(mapKeyColumns)) {  //key already exists.			
			return d.get(mapKeyColumns);
		}
		d.put(mapKeyColumns, ret);
		return ret;
	}
	
	
	/**
	 * 
	 * Returns all tuples that match given query in sub-relation rooted at this node.
	 * 
	 * @param theQuery the query tuple to match
	 * 
	 * @return all tuples that match given query in sub-relation rooted at this node
	 */
	public List<List<String>> queryNode(List<String> theQuery) {
		
    	if (isSinkNode()) {
    		return qunit(theQuery);
    	}
    	Set<String> keySet = this.adjacent.keySet();
	
    	if (keySet.size() == 1) {   // this node out-degree is 1.
        	Iterator<String> itr = keySet.iterator();
    		return qscan(theQuery, this.adjacent.get(itr.next()));
    	}
    	
    	if (keySet.size() > 1) {   // this node out-degree is > 1.
    		return qjoin(theQuery);
    	}
    	
    	return new ArrayList<List<String>>();  // empty set.
	}
	
	
	/**
	 * Removes all tuples that match given tuple in sub-relation rooted at this node.
	 * 
	 * @param remTuple the tuple to match
	 * @param decompVert list of vertices of decomposition graph (containing this removal cut)
	 */
	public void removeNode(List<String> remTuple, List<DecompositionGraph.Vertex> decompVert) {
		
		for (String adj : this.adjacent.keySet()) {
			
			//getting ds of adj. vertex from main map.
			KeyValueDataStructure<List<String>,InstanceNode> ds = this.adjacent.get(adj);
			boolean isAboveCut = isNodeAboveCut(adj, decompVert);
			removalScan(remTuple, decompVert, ds, isAboveCut);
		}
	}

	
	private void removalScan(List<String> remTuple, List<DecompositionGraph.Vertex> decompVert,
									KeyValueDataStructure<List<String>,InstanceNode> ds, boolean isAboveCut) {
			
		InstanceNode adjNode;

        List<List<String>> keysToRemove = new ArrayList<>();
		
		for (List<String> key : ds.keySet()) {
			adjNode = ds.get(key);
			if (ToolBox.tuplesMatch(key, remTuple) &&
						(!adjNode.isSinkNode() || ToolBox.tuplesMatch(adjNode.getSinkNodeValues(), remTuple))) {
				if (isAboveCut) {
					ds.get(key).removeNode(remTuple, decompVert);
				} else { // given ds maps from node above the cut, to nodes below the cut.
                    keysToRemove.add(key);
				}
			}
		}
        for (List<String> k : keysToRemove) {
            ds.remove(k);
        }
	}
	
	
	private boolean isNodeAboveCut(String name, List<DecompositionGraph.Vertex> decompVert) {
		for (DecompositionGraph.Vertex v : decompVert) {
			if (name.equals(v.getName()) && v.isAboveCut()) {
				return true;
			}
		}
		return false;
	}
	
	
	private List<List<String>> qunit(List<String> theQuery) {
		List<List<String>> ret = new ArrayList<>();
		if (ToolBox.tuplesMatch(sinkNodeValues, theQuery)) {
			ret.add(new ArrayList<>(sinkNodeValues));
		}
		return ret;
	}
	
	
	private List<List<String>> qscan(List<String> theQuery, 
                                     KeyValueDataStructure<List<String>,InstanceNode> mapToScan) {
		
		List<List<String>> ret = new ArrayList<>();
		
		//flag that says whether set of key columns of mapToScan and theQuery are disjoint.
		boolean keyQueryDisjoint = false;
		
		if (!mapToScan.isEmpty()) {
			
			// gets the columns of the key of this map ds.
			List<String> keyColNames = ToolBox.getColNamesOfTuple(mapToScan.keySet().iterator().next());
			List<String> queryColNames = ToolBox.getColNamesOfTuple(theQuery);
			
			if (ToolBox.isDisjointSets(keyColNames, queryColNames)) {
				keyQueryDisjoint = true;
			}
			
			if (ToolBox.isSubSet(keyColNames, queryColNames)) {  // in this case we can use lookup instead of scan.
				return qlookup(theQuery, ToolBox.neededColValues(keyColNames, theQuery), mapToScan);
			}
		}
		
		
		for (List<String> key : mapToScan.keySet()) {
			if (keyQueryDisjoint || ToolBox.tuplesMatch(key, theQuery)) {  // if disjoint - every key matches.
				// no need to keep key in theQuery, while querying its sub-graph.
				List<String> updatedQuery = new ArrayList<String>(theQuery);
				updatedQuery.removeAll(key);
				
				List<List<String>> res = mapToScan.get(key).queryNode(updatedQuery);
	
				List<List<String>> tmp = new ArrayList<List<String>>();
				
				// add the key columns to all tuples found, if any.
				for (List<String> tup : res) {
					tmp.add(ToolBox.margeTuples(key, tup));
				}
				ret.addAll(tmp);
			}
		}
		return ret;
	}

	private List<List<String>> qlookup(List<String> theQuery, List<String> lookupKey, 
                                       KeyValueDataStructure<List<String>,InstanceNode> mapToLook) {
		
		List<List<String>> ret = new ArrayList<>();
		Collections.sort(lookupKey);
		InstanceNode n = mapToLook.get(lookupKey);
		if (n != null) {
			//no need to keep key in theQuery, while querying its sub-graph.
			List<String> updatedQuery = new ArrayList<>(theQuery);
			updatedQuery.removeAll(lookupKey);
			ret = n.queryNode(updatedQuery);
		}
		return ret;
	}
	
	private List<List<String>> qjoin(List<String> theQuery) {
		
		List<List<String>> ret = new ArrayList<>();

        for (String s : this.adjacent.keySet()) {
            ret = ToolBox.naturalJoin(ret, qscan(theQuery, this.adjacent.get(s)));
        }
		return ret;
		
	}
	
	@Override
	public String toString() {
		String s = "node name: " + name;
		if (!this.sinkNodeValues.isEmpty()) {
			s = s + "   sink values: \n" + this.sinkNodeValues;
		} else {
			s = s + "   mappings: \n" + this.adjacent.values(); 
		}
		return s;
	}
}
