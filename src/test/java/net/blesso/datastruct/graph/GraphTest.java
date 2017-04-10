/**
 * 
 */
package net.blesso.datastruct.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


/**
 * @author fblesso
 *
 */
public class GraphTest {
	
	private List<Vertex<Integer>> vertices;
	private List<Edge> edges;
	
	@Before
	public void setup() {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}

	/**
	 * Test method for {@link net.blesso.datastruct.graph.Graph#getEdgeCount()}.
	 */
	@Test
	public void testGetEdgeCount() {
		edges.add(new Edge(0, 1));
		final Graph<Integer> graph = new Graph<>(vertices, edges, true);
		assertThat(graph.getEdgeCount(), equalTo(1));
	}

	/**
	 * Test method for {@link net.blesso.datastruct.graph.Graph#findEdges(net.blesso.datastruct.graph.Vertex)}.
	 */
	@Test
	public void testFindEdgesVertexNoVertexIsEmptyCollection() {
		final Graph<Integer> graph = new Graph<>(vertices, edges, true);
		assertThat(graph.findEdges(123L).size(), equalTo(0));
	}
	@Test
	public void testFindEdgesVertexNoEdgesIsEmptyCollection() {
		vertices.add(Vertex.of(1));
		final Graph<Integer> graph = new Graph<>(vertices, edges, true);
		assertThat(graph.findEdges(Vertex.of(1)).size(), equalTo(0));
	}
	@Test
	public void testFindEdgesVertexOneEdges() {
		vertices.add(Vertex.of(1));
		edges.add(new Edge(1, 2));
		final Graph<Integer> graph = new Graph<>(vertices, edges, true);
		assertThat(graph.findEdges(Vertex.of(1)).size(), equalTo(1));
	}

	/**
	 * Test method for {@link net.blesso.datastruct.graph.Graph#findEdges(java.lang.Long)}.
	 */
	@Test
	public void testFindEdgesLong() {
		vertices.add(Vertex.of(1));
		edges.add(new Edge(1, 2));
		final Graph<Integer> graph = new Graph<>(vertices, edges, true);
		assertThat(graph.findEdges(1L).size(), equalTo(1));
	}

}
