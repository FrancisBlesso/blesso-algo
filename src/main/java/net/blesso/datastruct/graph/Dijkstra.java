/**
 * 
 */
package net.blesso.datastruct.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

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
	 * Tracks a node's current distance and other information about each vertex.
	 */
	private final Map<Vertex<T>, DijkstraNode<T>> nodes = new HashMap<>();
	
	/** 
	 * The vertices remaining to be processed. The algorithm removes vertices from this set 
	 * in order of the distance from the starting vertex. 
	 */
	private final Queue<DijkstraNode<T>> nextVertices = new PriorityQueue<>();
	
	public Dijkstra(Graph<T> graph) {
		this.graph = graph;
	}
	
	/**
	 * Initializes all data structures to start a search. For each vertex, it sets the distance
	 * to a maximum value.
	 */
	private void initialize() {
		nodes.clear();
		for(Vertex<T> vertex : graph.getVertices()) {
			nodes.put(vertex, new DijkstraNode<>(vertex));
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
		
		final DijkstraNode<T> firstNode = nodes.get(firstVertex);
		firstNode.distance = 0.0;
		nextVertices.add(firstNode);
		
		while(!nextVertices.isEmpty()) {
			final DijkstraNode<T> currentNode = nextVertices.remove();
			final Vertex<T> currentVertex = currentNode.vertex;
			currentNode.processed = true;
			final double currentDistance = currentNode.distance;
			for(Edge<T> edge : graph.findEdges(currentVertex)) {
				final Vertex<T> endVertex = graph.findVertex(edge.getEnd());
				final DijkstraNode<T> endNode = nodes.get(endVertex);
				if (!endNode.processed) {
					nextVertices.add(endNode);
				}
				final Double weight = edge.getWeight().weight(currentVertex, endVertex);
				final Double endVertexDistance = currentDistance + weight;
				if (endNode.distance > endVertexDistance) {
					endNode.distance = endVertexDistance;
					endNode.setParent(currentNode);
				}
			}
		}
		return firstNode.treeNode;
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
	
	/**
	 * Maintains state for each {@link Vertex} during the search.
	 * 
	 * @param <T> they {@link Vertex}'s type
	 */
	private static class DijkstraNode<T> implements Comparable<DijkstraNode<T>> {
		
		/** The corresponding vertex. */
		private final Vertex<T> vertex;
		
		/** {@link TreeNode} for the shortest path tree from the original node. */
		private final TreeNode<Vertex<T>> treeNode;
		
		/** 
		 * The current best distance for each vertex from the start. For any vertex, the distance will
		 * decrease as the algorithm discovers more efficient routes. 
		 */
		private double distance = Double.MAX_VALUE;
		
		/** Whether we've handled this vertex already. */
		private boolean processed = false;;
		
		private DijkstraNode(Vertex<T> vertex) {
			this.vertex = vertex;
			treeNode = new TreeNode<Vertex<T>>(vertex);
		}
		
		private void setParent(DijkstraNode<T> parent) {
			treeNode.setParent(parent.treeNode);
		}

		@Override
		public int compareTo(final DijkstraNode<T> o) {
			if (distance < o.distance) {
				return -1;
			} else if (distance > o.distance) {
				return 1;
			} else {
				return 0;
			}
		}

		@Override
		public int hashCode() {
			return vertex.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Dijkstra.DijkstraNode<?>) {
				final DijkstraNode<?> other = (DijkstraNode<?>) obj;
				return vertex.equals(other.vertex);
			} else {
				return false;
			}
		}
		
	}
}
