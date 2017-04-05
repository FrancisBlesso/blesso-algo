/**
 * 
 */
package net.blesso.datastruct.graph;

/**
 * Identifies two vertexes connected in a graph. To make the edge lightweight, it uses the vertices' id 
 * instead of the vertex itself.  
 *
 * @author fblesso
 */
public class Edge {
	
	private final long start;
	private final long end;
	//TODO add weight function
	
	public Edge(long start, long end) {
		this.start = start;
		this.end = end;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}
	@Override
	public String toString() {
		return "edge " + start + " -> " + end;
	}
	
}
