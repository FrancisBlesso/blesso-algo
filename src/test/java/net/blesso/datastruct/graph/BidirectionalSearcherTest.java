/**
 * 
 */
package net.blesso.datastruct.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import net.blesso.datastruct.graph.BidirectionalSearcher.Node;

/**
 * @author fblesso
 *
 */
public class BidirectionalSearcherTest {
	
	private Node left;
	private Node right;
	private BidirectionalSearcher searcher;
	
	@Before
	public void setup() {
		left = new Node();
		right = new Node();
		searcher = new BidirectionalSearcher();
	}
	
	private List<Node> assertSuccess(int pathSize) {
		final List<Node> path = searcher.bidirectionalSearch(left, right);
		assertThat(path.size(), equalTo(pathSize));
		assertThat(path.get(0), equalTo(left));
		assertThat(path.get(pathSize -1), equalTo(right));
		return path;
	}
	
	private void addEdge(Node parent, Node child) {
		parent.addChild(child);
	}

	@Test
	public void whenNotConnectedThenReturnNull() {
		List<Node> path = searcher.bidirectionalSearch(left, right);
		assertThat(path, CoreMatchers.nullValue());
	}
	
	@Test
	public void whenLeftToRightThenFound() {
		addEdge(left, right);
		assertSuccess(2);
	}
	
	@Test
	public void whenRightToLeftThenFound() {
		addEdge(right, left);
		assertSuccess(2);
	}
	
	@Test
	public void whenBothDirectionsThenFound() {
		addEdge(right, left);
		addEdge(left, right);
		assertSuccess(2);
	}
	
	@Test
	public void whenMiddleThenFound() {
		final Node middle = new Node();
		addEdge(left, middle);
		addEdge(right, middle);
		final List<Node> path = assertSuccess(3);
		assertThat(path.get(1), equalTo(middle));
	}
	
	@Test
	public void whenMiddleBothDirectionsThenFound() {
		final Node middle = new Node();
		addEdge(left, middle);
		addEdge(right, middle);
		addEdge(middle, left);
		addEdge(middle, right);
		final List<Node> path = assertSuccess(3);
		assertThat(path.get(1), equalTo(middle));
	}
	
	@Test
	public void whenRightToLeftBigThenFound() {
		Node node = right;
		for (int i = 0; i<10; i++) {
			final Node child = new Node();
			addEdge(node, child);
			node = child;
		}
		addEdge(node, left);
		assertSuccess(12);
	}
	
	@Test
	public void whenLeftToRightBigThenFound() {
		Node node = left;
		for (int i = 0; i<10; i++) {
			final Node child = new Node();
			addEdge(node, child);
			node = child;
		}
		addEdge(node, right);
		assertSuccess(12);
	}
	
	@Test
	public void whenMultiplePathsThenShortestFound() {
		Node node = left;
		for (int i = 0; i<20; i++) {
			final Node child = new Node();
			addEdge(node, child);
			node = child;
		}
		addEdge(node, right);
		addEdge(right, node);
		addEdge(node, left);
		assertSuccess(3);
	}

}
