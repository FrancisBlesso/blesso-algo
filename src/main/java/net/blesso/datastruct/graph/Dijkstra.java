/**
 * 
 */
package net.blesso.datastruct.graph;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.blesso.datastruct.TreeNode;

/**
 * Builds a tree, using Dijkstra's algorithm, of least-cost paths from the starting Vertex to all other reachable vertices in the 
 * graph. 
 *
 * @author fblesso
 * @param <T> the type of object the graph manages
 */
public class Dijkstra<T> {
	
	/** The graph to search.*/
	private final Graph<T> graph;
	
	/** 
	 * The current best distance for each vertex from the start. For any vertex, the distance will
	 * decrease as the algorithm discovers more efficient routes. 
	 */
	private final Map<Vertex<T>, Double> distances = new HashMap<>();

	/** Keeps track of each's vertex's {@link TreeNode}. */
	private final Map<Vertex<T>, TreeNode<Vertex<T>>> treeNodes = new HashMap<>();
	
	/** 
	 * The vertices remaining to be processed. The algorithm removes vertices from this set 
	 * in order of the distance from the starting vertex. 
	 */
	private final Set<Vertex<T>> unprocessedVertices = new LinkedHashSet<>();
	
	public Dijkstra(Graph<T> graph) {
		this.graph = graph;
	}
	
	/**
	 * Initializes all data structures to start a search. For each vertex, it sets the distance
	 * to a maximum value.
	 */
	private void initialize() {
		distances.clear();
		treeNodes.clear();
		unprocessedVertices.clear();
		for(Vertex<T> vertex : graph.getVertices()) {
			distances.put(vertex,  Double.MAX_VALUE);
			unprocessedVertices.add(vertex);
			treeNodes.put(vertex, new TreeNode<>(vertex));
		}
	}

	/**
	 * Runs the search from the graph's first vertex. 
	 * @return the tree of best paths with the start vertex as the root.
	 */
	public TreeNode<Vertex<T>> search() {
		return search(0);
	}
	
	/**
	 * Runs the search from a specific vertex in the graph.
	 * @param vertexPosition the vertex's position in the graph's list of vertices.
	 * @return the tree of best paths with the start vertex as the root.
	 */
	public TreeNode<Vertex<T>> search(int vertexPosition) {
		final Vertex<T> startVertex = graph.getVertices().get(vertexPosition);
		return search(startVertex);
	}
	
	/**
	 * @param firstVertex the {@link Vertex} to start from.
	 * @return {@link TreeNode} starting at the first vertex. the tree will map the best path to every reachable vertex
	 */
	//TODO add endVertex parameter so we can end the search early once the path to it is found 
	public TreeNode<Vertex<T>> search(Vertex<T> firstVertex) {
		initialize();
		
		distances.put(firstVertex, 0.0);
		
		Vertex<T> currentVertex = firstVertex;
		
		while(currentVertex != null) {
			unprocessedVertices.remove(currentVertex);
			final TreeNode<Vertex<T>> currentTreeNode = treeNodes.get(currentVertex);
			final double currentDistance = distances.get(currentVertex);
			for(Edge<T> edge : graph.findEdges(currentVertex)) {
				final Vertex<T> endVertex = graph.findVertex(edge.getEnd());
				final Double weight = edge.getWeight().weight(currentVertex, endVertex);
				final Double endVertexDistance = currentDistance + weight;
				if (distances.get(endVertex) > endVertexDistance) {
					distances.put(endVertex, endVertexDistance);
					final TreeNode<Vertex<T>> endTreeNode = treeNodes.get(endVertex);
					endTreeNode.setParent(currentTreeNode);
				}
			}
			currentVertex = getClosestVertex();
		}
		return treeNodes.get(firstVertex);
	}

	/**
	 * Iterates through all the unprocessed vertices and determines which has a distance closest to the 
	 * start vertex.
	 * @return the {@link Vertex} closest to the start vertex from the collection of unprocessed vertices
	 */
	private Vertex<T> getClosestVertex() {
		Vertex<T> currentVertex = null;
		double distance = Double.MAX_VALUE;
		for(Vertex<T> vertex : unprocessedVertices){
			Double currDistance = distances.get(vertex);
			if ( currDistance < distance) {
				distance = currDistance;
				currentVertex = vertex;
			}
		}
		return currentVertex;
	}

	//TODO not ready yet.
//	public List<Vertex<T>> bestPath(Vertex<T> firstVertex, Vertex<T> endVertex) {
//		search(firstVertex);
//		final List<Vertex<T>> returnList = new ArrayList<>();
//		TreeNode<Vertex<T>> treeNode = treeNodes.get(endVertex);
//		while(!treeNode.getItem().equals(firstVertex)) {
//			returnList.add(0,treeNode.getItem());
//			treeNode = treeNode.getParent();
//			if (treeNode == null) {
//				throw new RuntimeException("no path from " + firstVertex + " to " + endVertex);
//			}
//		}
//		return returnList;
//	}
}
