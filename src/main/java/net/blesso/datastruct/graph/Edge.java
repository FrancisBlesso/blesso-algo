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
public class Edge<T> {
	
	private final long start;
	private final long end;
	private final EdgeWeight<T> weight;
	
	public Edge(long start, long end, EdgeWeight<T> weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}
	public EdgeWeight<T> getWeight() {
		return weight;
	}
	@Override
	public String toString() {
		return "edge " + start + " -> " + end;
	}
	
}
