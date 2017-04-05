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
	private final List<Vertex<T>> vertices = new ArrayList<>();
	private final Map<Long, Vertex<T>> vertexMap = new HashMap<>();
	
	private final Map<Long, List<Edge>> adjacencies = new HashMap<>();
	private final boolean directed;

	public Graph(Collection<Vertex<T>> vertices, Collection<Edge> edges, boolean directed) {
		this.directed = directed;
		for (Vertex<T> vertex : vertices) {
			addVertex(vertex);
		}
		
		for(Edge edge : edges) {
			addEdge(edge);
		}
	}

	private void addEdge(Edge edge) {
		final List<Edge> adjacencyList;
		if (adjacencies.containsKey(edge.getStart())) {
			adjacencyList = adjacencies.get(edge.getStart());
		} else {
			adjacencyList = new ArrayList<>();
			adjacencies.put(edge.getStart(), adjacencyList);
		}
		adjacencyList.add(edge);
	}

	private void addVertex(Vertex<T> vertex) {
		if (vertexMap.containsKey(vertex.getId())) {
			throw new IllegalArgumentException("the graph already contains the vertex" + vertex);
		}
		vertices.add(vertex);
		vertexMap.put(vertex.getId(), vertex);
	}

	public boolean isDirected() {
		return directed;
	}
	
	public List<Vertex<T>> getVertices() {
		return Collections.unmodifiableList(vertices);
	}

	public Vertex<T> findVertex(Long id) {
		return vertexMap.get(id);
	}
	public List<Edge> findEdges(Vertex<T> vertex) {
		return Collections.unmodifiableList(adjacencies.get(vertex.getId()));
	}
	public List<Edge> findEdges(Long vertexId) {
		return Collections.unmodifiableList(adjacencies.get(vertexId));
	}
}
