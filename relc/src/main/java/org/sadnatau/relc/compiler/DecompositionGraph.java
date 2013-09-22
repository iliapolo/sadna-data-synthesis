package org.sadnatau.relc.compiler;

import org.sadnatau.relc.data.PrimitiveDS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * A class that represents a decomposition graph.
 * 
 * @author Yevgeny Levanzov & Daniel Samuelov
 * 
 */
public class DecompositionGraph implements Iterable<DecompositionGraph.Vertex>, Serializable {

    /* impl. serializable. */
    private static final long serialVersionUID = 42L;

	private List<Vertex> vertices;
	private boolean topSorted;

	public DecompositionGraph() {
		this.vertices = new ArrayList<>();
		this.topSorted = false;
	}

	public void addVertex(Vertex v) {
		this.vertices.add(v);
	}

	/**
	 * 
	 * Returns Vertex object of a vertex in this graph, by given name,
	 * or null if not found. 
	 * 
	 * @param name the name of vertex 
	 * 
	 * @return Vertex object of a vertex in this graph, by given name,
	 * 		   or null if not found. 
	 */
	public Vertex getVertexByName(String name) {
		for (Vertex v : vertices) {
			if (v.getName().equals(name)) {
				return v;
			}
		}
		return null;
	}
	
	/*
	 * Returns iterator over vertices in topological order. 
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<Vertex> iterator() {
		if (!topSorted) {
			topologicalSort();
			topSorted = true;
		}
		return vertices.iterator();
	}

    /**
     * sorts vertices list topologically.
     */
	private void topologicalSort() {
        List<Vertex> lst = new ArrayList<>();
        DFS(lst);
        vertices.clear();
        vertices.addAll(lst);
	}

    /**
     * DSF Algorithm.
     * @param topsortList
     */
	private void DFS(List<Vertex> topsortList) {
		for (Vertex v : vertices) {   //init.
			v.setColor("WHITE");
		}
		for (Vertex v : vertices) {
			if (v.getColor().equals("WHITE")) {
				DFSVisit(v, topsortList);
			}
		}
	}

    /**
     * the DFS algorithm recursive visit method.
     * @param v
     * @param topsortList
     */
	private void DFSVisit(Vertex v, List<Vertex> topsortList) {
		v.setColor("GRAY");
		List<Edge> edges = v.getAdjList();
		for (Edge e : edges) {  //going through all neighbors.
			Vertex dest = getVertexByName(e.getDestVertName());
			if (dest.getColor().equals("WHITE")) {
				DFSVisit(dest, topsortList);
			}
		}
		v.setColor("BLACK");
		topsortList.add(0, v);  //building the topo. order list.
	}
	
	/**
	 * 
	 * A class for a vertex of decomposition graph. 
	 * 
	 * @author Yevgeny Levanzov & Daniel Samuelov
	 * 
	 */
	public static class Vertex implements Serializable {

        private static final long serialVersionUID = 41L; /* impl. serializable. */

		private String name;

		// names of columns of sink vertex (out-degree = 0).
		private List<String> sinkColsNames;

		private List<Edge> adjacent;

		// for DFS.
		private String color;
        // for remove and update operations.
		private List<String> boundCols;

		private boolean aboveCut;

		public Vertex(String name) {
			this.name = name;
			this.sinkColsNames = new ArrayList<String>();
			this.adjacent = new ArrayList<Edge>();
			this.boundCols = new ArrayList<String>();
			this.aboveCut = false;
		}

		public List<String> getSinkColsNames() {
			return this.sinkColsNames;
		}
		
		public void setSinkColsNames(List<String> s) {
			this.sinkColsNames.addAll(s);
			Collections.sort(this.sinkColsNames);
		}
		
		public boolean isSinkVertex() {
			return (!this.sinkColsNames.isEmpty());
		}
		
		public String getName() {
			return this.name;
		}

		public List<Edge> getAdjList() {
			return this.adjacent;
		}

		public void addAdj(Edge e) {
			this.adjacent.add(e);
		}

		public void setColor(String c) {
			this.color = c;
		}
		
		public String getColor() {
			return this.color;
		}
		
		public void setBoundCols(List<String> cols) {
			this.boundCols.clear();
			this.boundCols.addAll(cols);
		}

		public List<String> getBoundCols() {
			return this.boundCols;
		}

		public void setAboveCut(boolean b) {
			this.aboveCut = b;
		}
		
		public boolean isAboveCut() {
			return this.aboveCut;
		}

		@Override
		public boolean equals(Object v) {

			return (this.name.equals(((Vertex)v).getName()));
		}
		
		@Override
		public String toString() {
			String s = name + ": ";
			if (!sinkColsNames.isEmpty()) {
				s = s + sinkColsNames;
			} else {
				s = s + adjacent;
			}
			s = s + ", Bound Columns: " + boundCols;
			return s;
		}
	}
	
	
	/**
	 * 
	 * A class for an edge of decomposition graph. 
	 * 
	 * @author Yevgeny Levanzov & Daniel Samuelov
	 *
	 */
	public static class Edge implements Serializable {

        /* impl. serializable. */
        private static final long serialVersionUID = 40L;

        private String originVertName;
		private String destVertName;
        private List<String> cols;
		private PrimitiveDS ds;

		public Edge(String orig, String dest, List<String> col, PrimitiveDS ds) {
			this.originVertName = orig;
			this.destVertName = dest;
			this.cols = new ArrayList<String>(col);
			Collections.sort(this.cols);
			this.ds = ds;
		}

		public String getOriginVertName() {
			return this.originVertName;
		}

		public String getDestVertName() {
			return this.destVertName;
		}

		public List<String> getCols() {
			return this.cols;
		}

		public PrimitiveDS getPrimitiveDS() {
			return this.ds;
		}
		
		@Override
		public String toString() {
            return "vert: " + destVertName + " cols: " + cols + " ds: " + ds;
		}
	}
}
