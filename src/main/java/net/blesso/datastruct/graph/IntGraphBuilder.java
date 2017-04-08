package net.blesso.datastruct.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Builder for graphs of integers. It provides methods to add edges and vertices and to build the
 * immutable graph. 
 * @author fblesso
 */
public class IntGraphBuilder {
	
	private boolean directed;
	private final List<Vertex<Integer>> vertices = new ArrayList<>();
	private final List<Edge> edges = new ArrayList<>();
	
	/**
	 * @return the created graph using the accumulated vertices and edges
	 */
	public Graph<Integer> build() {
		final Graph<Integer> graph = new Graph<Integer>(vertices, edges, directed);
		return graph;
	}
	
	/**
	 * Creates a builder for a undirected graph.
	 * @return a new {@link IntGraphBuilder}
	 */
	public static IntGraphBuilder undirected() {
		final IntGraphBuilder builder = new IntGraphBuilder();
		builder.directed = false;
		return builder;
	}
	
	/**
	 * Creates a builder for a directed graph.
	 * @return a new {@link IntGraphBuilder}
	 */
	public static IntGraphBuilder directed() {
		final IntGraphBuilder builder = new IntGraphBuilder();
		builder.directed = true;
		return builder;
	}
	
	/**
	 * Adds <code>numVertices</code> starting from zero.
	 * @param numVertices the number of vertices to add
	 * @return this
	 */
	public IntGraphBuilder vertices(int numVertices) {
		for(int count = 0; count < numVertices; count++) {
			vertex(count);
		}
		return this;
	}
	
	/**
	 * Adds a single vertex with the passed in value.
	 * @param id the new vertex's value
	 * @return this
	 */
	public IntGraphBuilder vertex(int id) {
		vertices.add(Vertex.of(id));
		return this;
	}

	/**
	 * Adds {@link Edge}s to create a cycle from the start vertex to the end vertex
	 * @param firstVertex the cycle's start vertex's id
	 * @param lastVertex the cycle's end vertex's id
	 * @return this
	 */
	public IntGraphBuilder cycle(int firstVertex, int lastVertex) {
		for(int currStart = firstVertex; currStart < lastVertex; currStart++) {
			edge(currStart, currStart + 1);
		}
		edge(lastVertex, firstVertex);
		return this;
	}
	
	/**
	 * Adds an edge connecting the two vertices.
	 * @param start the Edge's start vertex
	 * @param end the Edge's end vertex
	 * @return this
	 */
	public IntGraphBuilder edge(int start, int end){
		final Edge edge = new Edge(start, end);
		edges.add(edge);
		if (!directed) {
			edges.add(new Edge(end, start));
		}
		return this;
	}
	/**
	 * Creates edges to link all the vertices to each other. It doesn't connect any vertex to 
	 * it self.
	 * @return
	 */
	public IntGraphBuilder maxEdges() {
		for (Vertex<Integer> v1 : vertices) {
			for (Vertex<Integer> v2 : vertices) {
				Integer int1 = v1.getItem();
				Integer int2 = v2.getItem();
				if(int1 != int2) {
					edge(int1, int2);
				}
			}
		}
		return this;
	}
	
	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(vertices.size() + "vertices: ");
		stringBuilder.append(vertices.toString());
		stringBuilder.append(edges.size() + " edges");
		edges.forEach(new Consumer<Edge>() {
			public void accept(Edge edge) {
				stringBuilder.append("\n  " + edge.toString());
			};
		});
		return stringBuilder.toString();
	}
}
