package net.blesso.datastruct.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import net.blesso.datastruct.TreeNode;

/**
 * Test case for {@link DepthFirstSearcher}.
 * @author fblesso
 */
public class DepthFirstSearchTest {
	
	/**
	 * <pre>
	 *      0--1--2
	 *      |     |
	 *      4-----3
	 * </pre>
	 */
	@Test
	public void testAllVerticesProcessedInCycle() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
			.vertices(5)
			.cycle(0, 4)
			.build();
		final DepthFirstSearcher<Integer> graphWalker = new DepthFirstSearcher<Integer>(graph);
		DepthFirstSearcher<Integer>.GraphFlags graphFlags = graphWalker.search();
		for(Vertex<Integer> vertex : graph.getVertices()) {
			assertThat(graphFlags.getVertexState(vertex), equalTo(VertexFlags.VertexState.PROCESSED));
		}
	}
	
	/**
	 * Verify the algorithm operating against a cycle creates a tree like this
	 *  <pre>
	 * 0
	 *  \
	 *   1
	 *    \
	 *     2
	 *      \
	 *       3
	 *        \
	 *         4
	 *         </pre>
	 */
	@Test
	public void testCycleTreeStructure() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
			.vertices(5)
			.cycle(0, 4)
			.build();
		final DepthFirstSearcher<Integer> graphWalker = new DepthFirstSearcher<Integer>(graph);
		DepthFirstSearcher<Integer>.GraphFlags graphFlags = graphWalker.search();
		TreeNode<Vertex<Integer>> treeNode = graphFlags.getTree();
		assertThat(treeNode.getItem().getItem(), equalTo(0));
		assertThat(treeNode.getChildren().size(), equalTo(1));
		treeNode = treeNode.firstChild();
		assertThat(treeNode.getItem().getItem(), equalTo(1));
		assertThat(treeNode.getChildren().size(), equalTo(1));
		treeNode = treeNode.firstChild();
		assertThat(treeNode.getItem().getItem(), equalTo(2));
		assertThat(treeNode.getChildren().size(), equalTo(1));
		treeNode = treeNode.firstChild();
		assertThat(treeNode.getItem().getItem(), equalTo(3));
		assertThat(treeNode.getChildren().size(), equalTo(1));
		treeNode = treeNode.firstChild();
		assertThat(treeNode.getItem().getItem(), equalTo(4));
		assertThat(treeNode.getChildren().size(), equalTo(0));
	}
	
	@Test
	public void testTreeStructure() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
			.vertices(5)
			.cycle(0, 3)
			.edge(0, 4)
			.build();
		final DepthFirstSearcher<Integer> graphWalker = new DepthFirstSearcher<Integer>(graph);
		final DepthFirstSearcher<Integer>.GraphFlags graphFlags = graphWalker.search();
		final TreeNode<Vertex<Integer>> treeNode = graphFlags.getTree();
		assertThat(treeNode.getItem().getItem(), equalTo(0));
		assertThat(treeNode.getChildren().size(), equalTo(2));
		final TreeNode<Vertex<Integer>> child1 = treeNode.firstChild();
		assertThat(child1.getItem().getItem(), equalTo(1));
		assertThat(child1.getChildren().size(), equalTo(1));
		final TreeNode<Vertex<Integer>> child2 = child1.firstChild();
		assertThat(child2.getItem().getItem(), equalTo(2));
		assertThat(child2.getChildren().size(), equalTo(1));
		final TreeNode<Vertex<Integer>> child3 = child2.firstChild();
		assertThat(child3.getItem().getItem(), equalTo(3));
		assertThat(child3.getChildren().size(), equalTo(0));
		final TreeNode<Vertex<Integer>> child4 = treeNode.getChildren().get(1);
		assertThat(child4.getItem().getItem(), equalTo(4));
		assertThat(child4.getChildren().size(), equalTo(0));
	}
	
	/**
	 * <pre>
	 *       0
	 *      / \
	 *     2   1
	 *    /
	 *   3</pre>      
	 *  The order should be 0, 2, 3 ,1.  
	 */
	@Test
	public void testEntryOrder() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
			.vertices(4)
			.edge(0, 2)
			.edge(0, 1)
			.edge(2, 3)
			.build();
		final List<Vertex<Integer>> vertices = graph.getVertices();
		final DepthFirstSearcher<Integer> graphWalker = new DepthFirstSearcher<Integer>(graph);
		final DepthFirstSearcher<Integer>.GraphFlags graphFlags = graphWalker.search();
		
		final VertexFlags<Integer> vertexFlags0 = graphFlags.getVertexFlags(vertices.get(0));
		final VertexFlags<Integer> vertexFlags1 = graphFlags.getVertexFlags(vertices.get(1));
		final VertexFlags<Integer> vertexFlags2 = graphFlags.getVertexFlags(vertices.get(2));
		final VertexFlags<Integer> vertexFlags3 = graphFlags.getVertexFlags(vertices.get(3));
		assertThat(vertexFlags0.getEntryTime(), equalTo(0));
		assertThat(vertexFlags2.getEntryTime(), equalTo(1));
		assertThat(vertexFlags3.getEntryTime(), equalTo(2));
		assertThat(vertexFlags3.getExitTime(), equalTo(3));
		assertThat(vertexFlags2.getExitTime(), equalTo(4));
		assertThat(vertexFlags1.getEntryTime(), equalTo(5));
		assertThat(vertexFlags1.getExitTime(), equalTo(6));
		assertThat(vertexFlags0.getExitTime(), equalTo(7));

	}

	
	/**
	 * <pre>
	 *        0
	 *       / \
	 *      1   2
	 *       \ /
	 *        3
	 *        </pre>
	 */
	@Test
	public void testMultipleEndEdgesParentIsFirstProcessedEdge() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
			.vertices(4)
			.edge(0, 1)
			.edge(0, 2)
			.edge(2, 3)
			.edge(1, 3)
			.build();
		final DepthFirstSearcher<Integer> graphWalker = new DepthFirstSearcher<Integer>(graph);
		final DepthFirstSearcher<Integer>.GraphFlags graphFlags = graphWalker.search();
		final TreeNode<Vertex<Integer>> treeNode = graphFlags.getVertexFlags(Vertex.of(3)).getTreeNode();
		TreeNode<Vertex<Integer>> parentTree = treeNode.getParent();
		final Vertex<Integer> parentVertex = parentTree.getItem();
		assertThat(parentVertex.getItem(), equalTo(1));
		
	}
	@Test
	public void testAllVerticesProcessedInTwoCycles() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
			.vertices(5)
			.cycle(0, 4)
			.edge(0,2)
			.edge(0,3)
			.edge(0,4)
			.build();
		final DepthFirstSearcher<Integer> searcher = new DepthFirstSearcher<Integer>(graph);
		DepthFirstSearcher<Integer>.GraphFlags graphFlags = searcher.search();
		for(Vertex<Integer> vertex : graph.getVertices()) {
			assertThat(graphFlags.getVertexState(vertex), CoreMatchers.equalTo(VertexFlags.VertexState.PROCESSED));
		}
	}
	
	@Test
	public void testUnconnectedVertexNotDiscovered() {
		final Graph<Integer> graph = IntGraphBuilder.directed()
			.vertices(5)
			.cycle(0, 3)
			.build();
		final DepthFirstSearcher<Integer> searcher = new DepthFirstSearcher<Integer>(graph);
		DepthFirstSearcher<Integer>.GraphFlags graphFlags = searcher.search();
		final Vertex<Integer> vertex = graph.findVertex(4L);
		assertThat(graphFlags.getVertexState(vertex), CoreMatchers.equalTo(VertexFlags.VertexState.UNDISCOVERED));
	}

	@Test
	public void testUnDirected() {
		final Graph<Integer> graph = IntGraphBuilder.undirected()
			.vertices(4)
			.edge(0, 1)
			.edge(1, 2)
			.edge(2, 3)
			.build();
		//the tree should only have connection from 1 to 3, but not from 2 to 3.
		//toDO also need an assert to make sure the tree is valid, ie no circles or		
		final DepthFirstSearcher<Integer> graphWalker = new DepthFirstSearcher<Integer>(graph);
		final DepthFirstSearcher<Integer>.GraphFlags graphFlags = graphWalker.search(3);
		final TreeNode<Vertex<Integer>> treeNode = graphFlags.getVertexFlags(Vertex.of(0)).getTreeNode();
		assertThat(treeNode.getParent().getItem().getItem(), equalTo(1));
		
	}
}
