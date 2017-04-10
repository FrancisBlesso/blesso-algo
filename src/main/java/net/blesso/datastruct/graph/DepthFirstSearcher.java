/**
 * 
 */
package net.blesso.datastruct.graph;

import java.util.HashMap;
import java.util.Map;

import net.blesso.datastruct.TreeNode;

/**
 * Performs a depth-first search upon a graph. During the search, the algorithm will 
 * build a {@link GraphFlags} 
 * describing how the algorithm processed the search. The algorithm will also build a tree
 * of the vertices. The {@link GraphFlags} will contain
 * a {@link TreeNode} containing the root of this tree.
 *
 * @author fblesso
 */
public class DepthFirstSearcher<T> {
	
	/** The graph to search.*/
	private final Graph<T> graph;
	
	/** Tracks the status of the graph's vertices. */
	private final GraphFlags graphFlags;
	
	/** Tracks the order that the vertices are processed. */
	private int entryExitCount;
	
	/** Indicates there is no need to continue the search. */
	private boolean finished;

	public DepthFirstSearcher(Graph<T> graph) {
		this.graph = graph;
		graphFlags = new GraphFlags();
	}

	/**
	 * Runs the search from the graph's first vertex. 
	 */
	public GraphFlags search() {
		return search(0);
	}
	
	public GraphFlags search(int vertexPosition) {
		final Vertex<T> startVertex = graph.getVertices().get(vertexPosition);
		return search(startVertex);
	}
	
	/**
	 * @param firstVertex the {@link Vertex} to start from.
	 * @return {@link GraphFlags} describing how the search was performed
	 */
	public GraphFlags search(Vertex<T> firstVertex) {
		entryExitCount = 0;
		finished = false;
		graphFlags.initialize(graph);
		searchVertex(firstVertex);
		return graphFlags;
	}

	/**
	 * Allows a subclass to indicate the search has finished. 
	 */
	protected void setFinished() {
		this.finished = true;
	}
	
	void processVertexEarly(Vertex<T> vertex){
		
	}
	void processVertexLate(Vertex<T> vertex){
		
	}
	void processEdge(Edge edge, Vertex<T> start, Vertex<T> end){
		
	}
	
	/**
	 * Recursively searches from a vertex. 
	 * @param vertex the vertex to search from. 
	 */
	private void searchVertex(Vertex<T> vertex) {
		if (finished) {
			return;
		}
		processVertexEarly(vertex);
		final VertexFlags<T> vertexFlags = graphFlags.getVertexFlags(vertex);
		vertexFlags.setEntryTime(entryExitCount);
		entryExitCount++;
		vertexFlags.setDiscovered();
		for(Edge edge : graph.findEdges(vertex)) {
			final Vertex<T> endVertex = graph.findVertex(edge.getEnd());
			final VertexFlags<T> endFlags = graphFlags.getVertexFlags(endVertex);
			if (graphFlags.getVertexState(endVertex) == VertexFlags.VertexState.UNDISCOVERED) {
				endFlags.getTreeNode().setParent(vertexFlags.getTreeNode());
				processEdge(edge, vertex, endVertex);
				searchVertex(endVertex);
			}
		}
		processVertexLate(vertex);
		vertexFlags.setProcessed();
		vertexFlags.setExitTime(entryExitCount);
		entryExitCount++;
	}
	
	public final class GraphFlags {
		private final Map<Long, VertexFlags<T>> vertexFlags = new HashMap<>();

		private void initialize(Graph<T> g){
			vertexFlags.clear();
			for (Vertex<T> v : graph.getVertices()){
				vertexFlags.put(v.getId(), new VertexFlags<T>(v));
			}
		}

		public VertexFlags<T> getVertexFlags(Vertex<T> v){
			return vertexFlags.get(v.getId());
		}
		
		public VertexFlags.VertexState getVertexState(Vertex<T> v){
			return getVertexFlags(v).getState();
		}
		public TreeNode<Vertex<T>> getTree() {
			return vertexFlags.get(graph.getVertices().get(0).getId()).getTreeNode();
		}
	}
}
