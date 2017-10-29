/**
 * 
 */
package net.blesso.datastruct.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Graph that stores edges in an adjacency list for each vertex.
 *
 * @author fblesso
 */
public class Graph<T> {
	private final List<Vertex<T>> vertices;
	private final Map<Long, Vertex<T>> vertexMap = new HashMap<>();
	
	private final Map<Long, List<Edge<T>>> adjacencies = new HashMap<>();
	private final boolean directed;
	private int edgeCount;

	/**
	 * @param vertices
	 * @param edges
	 * @param directed
	 * 
	 * @throws IllegaArgumentException if there is a bad vertex reference.
	 */
	public Graph(Collection<Vertex<T>> vertices, Collection<Edge<T>> edges, boolean directed) {
		this.directed = directed;
		this.vertices = Collections.unmodifiableList(new ArrayList<>(vertices));
		for (Vertex<T> vertex : vertices) {
			addVertex(vertex);
		}
		
		for(Edge<T> edge : edges) {
			addEdge(edge);
		}
		validateEdges();
	}

	private void addEdge(Edge<T> edge) {
		final List<Edge<T>> adjacencyList;
		if (adjacencies.containsKey(edge.getStart())) {
			adjacencyList = adjacencies.get(edge.getStart());
		} else {
			adjacencyList = new ArrayList<>();
			adjacencies.put(edge.getStart(), adjacencyList);
		}
		adjacencyList.add(edge);
		edgeCount++;
	}

	private void addVertex(Vertex<T> vertex) {
		if (vertexMap.containsKey(vertex.getId())) {
			throw new IllegalArgumentException("the graph already contains the vertex" + vertex);
		}
		vertexMap.put(vertex.getId(), vertex);
	}

	public boolean isDirected() {
		return directed;
	}
	
	public List<Vertex<T>> getVertices() {
		return vertices;
	}
	
	public int getEdgeCount() {
		return edgeCount;
	}

	public Vertex<T> findVertex(Long id) {
		return vertexMap.get(id);
	}
	public List<Edge<T>> findEdges(Vertex<T> vertex) {
		return findEdges(vertex.getId());
	}
	public List<Edge<T>> findEdges(Long vertexId) {
		final List<Edge<T>> adjacentEdges = adjacencies.get(vertexId);
		if (adjacentEdges == null) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(adjacentEdges);
	}
	
	/**
	 * Ensures that all edges refer to existing vertices.
	 * @throws IllegaArgumentException if there is a bad vertex reference.
	 */
	public void validateEdges() {
		for (Collection<Edge<T>> edges : adjacencies.values()) {
			for (Edge<T> edge : edges) {
				if (findVertex(edge.getStart()) == null) {
					throw new IllegalArgumentException("no start vertex for " + edge);
				}
				if (findVertex(edge.getEnd()) == null) {
					throw new IllegalArgumentException("no end vertex for " + edge);
				}
			}
		}
	}
}
