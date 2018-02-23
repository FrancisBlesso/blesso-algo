/**
 * 
 */
package net.blesso.datastruct.graph;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fblesso
 *
 */
public class CycleFinder<T> extends DepthFirstSearcher<T> {
	
	public CycleFinder(Graph<T> graph) {
		super(graph);
	}

	private Map<Vertex<T>, Vertex<T>> parentMap = new HashMap<>();

	@Override
	public void processVertexEarly(Vertex<T> vertex) {
		
	}

	@Override
	public void processVertexLate(Vertex<T> vertex) {
		
	}

	@Override
	public void processEdge(Edge<T> edge, Vertex<T> start, Vertex<T> end) {
		
	}

}
