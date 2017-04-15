/**
 * 
 */
package net.blesso.datastruct.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author fblesso
 *
 */
public class IntGraphBuilderTest {
	
	/**
	 * Verifies that the edge has the correct start and end
	 * @param edge the {@link Edge} to test
	 * @param start the edge's expected start
	 * @param end the edge's expected end
	 */
	private void assertEdge(Edge<Integer> edge, int start, int end) {
		assertThat(edge.getStart(), equalTo(Long.valueOf(start)));
		assertThat(edge.getEnd(), equalTo(Long.valueOf(end)));
	}

	/**
	 * Test method for {@link net.blesso.datastruct.graph.IntGraphBuilder#vertices(int)}.
	 */
	@Test
	public void testVertices() {
		final Graph<Integer> graph = new IntGraphBuilder().vertices(2).build();
		assertThat(graph.getVertices().size(), equalTo(2));
		assertThat(graph.getVertices().get(0).getItem(), equalTo(Integer.valueOf(0)));
		assertThat(graph.getVertices().get(1).getItem(), equalTo(Integer.valueOf(1)));
		assertThat(graph.getVertices().get(0).getId(), equalTo(Long.valueOf(0)));
		assertThat(graph.getVertices().get(1).getId(), equalTo(Long.valueOf(1)));
	}

	/**
	 * Test method for {@link net.blesso.datastruct.graph.IntGraphBuilder#vertex(int)}.
	 */
	@Test
	public void testVertex() {

		final Graph<Integer> graph = new IntGraphBuilder().vertex(77).build();
		assertThat(graph.getVertices().size(), equalTo(1));
		assertThat(graph.getVertices().get(0).getItem(), equalTo(Integer.valueOf(77)));
		assertThat(graph.getVertices().get(0).getId(), equalTo(Long.valueOf(77)));
	}

	/**
	 * Test method for {@link net.blesso.datastruct.graph.IntGraphBuilder#cycle(int, int)}.
	 */
	@Test
	public void testCycle() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
				.vertices(5)
				.cycle(1, 3)
				.build();
		assertThat(graph.findEdges(1L).size(), equalTo(1));
		assertThat(graph.findEdges(2L).size(), equalTo(1));
		assertThat(graph.findEdges(3L).size(), equalTo(1));

		assertEdge(graph.findEdges(1L).get(0), 1, 2);
		assertEdge(graph.findEdges(2L).get(0), 2, 3);
		assertEdge(graph.findEdges(3L).get(0), 3, 1);
	}

	/**
	 * Test method for {@link net.blesso.datastruct.graph.IntGraphBuilder#edge(int, int)}.
	 */
	@Test
	public void testEdge() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
				.vertices(3)
				.edge(2, 1)
				.build();
		assertThat(graph.findEdges(2L).size(), equalTo(1));

		assertEdge(graph.findEdges(2L).get(0), 2, 1);
	}

	@Test
	public void testEdgeDirectedOnlyAddsOneEdge() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
				.vertices(3)
				.edge(2, 1)
				.build();
		assertThat(graph.getEdgeCount(), equalTo(1));
	}
	
	@Test
	public void testEdgeUndirectedAddsTwoEdges() {
		final Graph<Integer> graph = IntGraphBuilder.undirected()
				.vertices(3)
				.edge(2, 1)
				.build();
		assertThat(graph.getEdgeCount(), equalTo(2));
	}
	
	
	@Test
	public void testMaxEdges() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
				.vertices(4)
				.clique()
				.build();

		assertThat(graph.findEdges(0L).size(), equalTo(3));
		assertThat(graph.findEdges(1L).size(), equalTo(3));
		assertThat(graph.findEdges(2L).size(), equalTo(3));
		assertThat(graph.findEdges(3L).size(), equalTo(3));

		assertEdge(graph.findEdges(2L).get(0), 2, 0);
		assertEdge(graph.findEdges(2L).get(1), 2, 1);
		assertEdge(graph.findEdges(2L).get(2), 2, 3);
	}
}
