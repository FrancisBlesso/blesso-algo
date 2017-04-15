/**
 * 
 */
package net.blesso.datastruct.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import net.blesso.datastruct.TreeNode;

/**
 * Test class for {@link Dijkstra}.
 * @author fblesso
 */
public class DijkstraTest {

	/**
	 * Test method for {@link net.blesso.datastruct.graph.Dijkstra#search()}.
	 */
	@Test
	public void testTwoVertices() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
				.vertices(2)
				.cycle(0, 1)
				.build();
		final Dijkstra<Integer> dijkstra = new Dijkstra<>(graph);
		TreeNode<Vertex<Integer>> node0 = dijkstra.search(0);
		assertThat(node0.getItem().getItem(), equalTo(0));
		final TreeNode<Vertex<Integer>> node1 = node0.firstChild();
		assertThat(node1.getItem().getItem(), equalTo(1));

		assertThat(node0.getParent(), CoreMatchers.nullValue());
		assertThat(node1.firstChild(), CoreMatchers.nullValue());

	}

	/**
	 * Test method for {@link net.blesso.datastruct.graph.Dijkstra#search()}.
	 */
	@Test
	public void testSearchAlternateWeight() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
				.vertices(6)
				.edge(0, 5, 0.0)
				.edge(0,  1, 17.0)
				.edge(0, 2, 5.0).edge(2, 1, 10)  //cheaper to get from zero to 1 via 2 than directly
				.edge(2, 3, 7).edge(3, 4, 2.0).edge(4, 1, 0.1) //even cheaper to go 0-2-3-4-1
				.build();
		final Dijkstra<Integer> dijkstra = new Dijkstra<>(graph);
		final TreeNode<Vertex<Integer>> rootNode = dijkstra.search();
		assertPath(rootNode, 0, 5);
		assertPath(rootNode, 0, 2, 3, 4, 1);
	}

	/**
	 * Test method for {@link net.blesso.datastruct.graph.Dijkstra#search()}.
	 */
	@Test
	public void testSearchByVertexIndex() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
				.vertices(4)
				.vertex(200)
				.cycle(0, 3)
				.edge(0, 200)
				.edge(200, 3)
				.build();
		final Dijkstra<Integer> dijkstra = new Dijkstra<>(graph);
		
		final TreeNode<Vertex<Integer>> node3 = dijkstra.search(3);
		assertThat(node3.getItem().getItem(), equalTo(3));
		assertThat(node3.getParent(), nullValue());
		
		final TreeNode<Vertex<Integer>> node0 = node3.findChild(Vertex.of(0));
		assertThat(node0.getItem().getItem(), equalTo(0));
		assertThat(node0.getParent(), sameInstance(node3));
		
		final TreeNode<Vertex<Integer>> node1 = node0.findChild(Vertex.of(1));
		assertThat(node1.getItem().getItem(), equalTo(1));
		assertThat(node1.getParent(), sameInstance(node0));

		final TreeNode<Vertex<Integer>> node2 = node1.findChild(Vertex.of(2));
		assertThat(node2.getItem().getItem(), equalTo(2));
		assertThat(node2.getParent(), sameInstance(node1));

		assertThat(node2.firstChild(), nullValue());

		final TreeNode<Vertex<Integer>> node200 = node0.findChild(Vertex.of(200));
		assertThat(node200.getItem().getItem(), equalTo(200));
		assertThat(node200.getParent(), CoreMatchers.sameInstance(node0));

		assertPath(node3, 3, 0, 1, 2);
	}

	/**
	 * Test method for {@link net.blesso.datastruct.graph.Dijkstra#search()}.
	 */
	@Test
	public void testSearch() {

		final Graph<Integer> graph = IntGraphBuilder.undirected()
				.vertices(4)
				.clique()
				.build();
		final Dijkstra<Integer> dijkstra = new Dijkstra<>(graph);
		
		final TreeNode<Vertex<Integer>> node0 = dijkstra.search();
		assertPath(node0, 0, 1);
		assertPath(node0, 0, 2);
		assertPath(node0, 0, 3);
	}
	
	/**
	 * Verifies that there is a path within the tree starting at the top that has 
	 * a vertex with each value in the list of vertices.  
	 * @param treeNode the tree's root
	 * @param vertexValues the vertices to test.
	 */
	private void assertPath(TreeNode<Vertex<Integer>> treeNode, Integer...vertexValues) {
		assertThat(treeNode.getParent(), nullValue());
		TreeNode<Vertex<Integer>> currNode = treeNode;
		for(int index = 0; index < vertexValues.length; index++) {
			assertThat(currNode.getItem().getItem(), equalTo(vertexValues[index]));
			if (index == vertexValues.length - 1) {
				assertThat(currNode.firstChild(), nullValue());
			} else {
				currNode = currNode.findChild(Vertex.of(vertexValues[index + 1]));
			}
		}
	}

	/**
	 * Test method for {@link net.blesso.datastruct.graph.Dijkstra#search(net.blesso.datastruct.graph.Vertex)}.
	 */
	@Test
	public void testSearchVertexOfT() {
		final Graph<Integer> graph = IntGraphBuilder.undirected()
				.vertex(4)
				.vertex(7)
				.vertex(10)
				.vertex(100)
				.edge(7, 4)
				.edge(4, 10)
				.edge(4, 100)
				.edge(7, 10)
				.edge(7, 100)
				.edge(10, 100)
				.build();
		final Dijkstra<Integer> dijkstra = new Dijkstra<>(graph);
		
		final TreeNode<Vertex<Integer>> node7 = dijkstra.search(Vertex.of(7));
		assertPath(node7, 7, 4);
		assertPath(node7, 7, 10);
		assertPath(node7, 7, 100);
		
	}

	/**
	 * Verifies that a vertices that are unconnected from the start vertex are not in the tree.
	 */
	@Test
	public void testUnconnected() {
		final Graph<Integer> graph = IntGraphBuilder.undirected()
				.vertices(3)
				.edge(2, 1)
				.build();
		final Dijkstra<Integer> dijkstra = new Dijkstra<>(graph);
		
		final TreeNode<Vertex<Integer>> node0 = dijkstra.search();
		assertThat(node0.firstChild(), nullValue());
		assertPath(node0, 0);
	}

}
