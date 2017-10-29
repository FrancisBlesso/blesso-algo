/**
 * 
 */
package net.blesso.datastruct.graph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

/**
 * @author fblesso
 *
 */
public class BidirectionalSearcherTest {
	
	private final static int LEFT = -1;
	private final static int RIGHT = -2;
	
	private IntGraphBuilder graphBuilder;
	
	@Before
	public void setup() {
		graphBuilder = IntGraphBuilder.directed()
				.vertex(LEFT)
				.vertex(RIGHT);
	}

	private BidirectionalSearcher<Integer> createSearcher() {
		final Graph<Integer> graph = graphBuilder.build();
		final Vertex<Integer> left = graph.findVertex((long) LEFT);
		final Vertex<Integer> right = graph.findVertex((long) RIGHT);

		return new BidirectionalSearcher<Integer>(graph, left, right);
	}
	
	private List<Vertex<Integer>> assertSuccess(int pathSize) {
		final Graph<Integer> graph = graphBuilder.build();
		final Vertex<Integer> left = graph.findVertex((long) LEFT);
		final Vertex<Integer> right = graph.findVertex((long) RIGHT);

		final BidirectionalSearcher<Integer> searcher = createSearcher();
		
		final List<Vertex<Integer>> path = searcher.bidirectionalSearch();
		assertThat(path.size(), equalTo(pathSize));
		assertThat(path.get(0), equalTo(left));
		assertThat(path.get(pathSize -1), equalTo(right));
		return path;
	}
	
	private void addEdge(int parent, int child) {
		graphBuilder.edge(parent, child);
	}

	@Test
	public void whenNotConnectedThenReturnNull() {
		final List<Vertex<Integer>> path = createSearcher().bidirectionalSearch();
		assertThat(path, CoreMatchers.nullValue());
	}
	
	@Test
	public void whenLeftToRightThenFound() {
		addEdge(LEFT, RIGHT);
		assertSuccess(2);
	}
	
	@Test
	public void whenRightToLeftThenFound() {
		addEdge(RIGHT, LEFT);
		assertSuccess(2);
	}
	
	@Test
	public void whenBothDirectionsThenFound() {
		addEdge(RIGHT, LEFT);
		addEdge(LEFT, RIGHT);
		assertSuccess(2);
	}
	
	@Test
	public void whenMiddleThenFound() {
		final int middle = 123;
		graphBuilder.vertex(middle);
		addEdge(LEFT, middle);
		addEdge(RIGHT, middle);
		final List<Vertex<Integer>> path = assertSuccess(3);
		assertThat(path.get(1).getItem(), equalTo(middle));
	}
	
	@Test
	public void whenMiddleBothDirectionsThenFound() {
		final int middle = 123;
		graphBuilder.vertex(middle);
		addEdge(LEFT, middle);
		addEdge(RIGHT, middle);
		addEdge(middle, LEFT);
		addEdge(middle, RIGHT);
		final List<Vertex<Integer>> path = assertSuccess(3);
		assertThat(path.get(1).getItem(), equalTo(middle));
	}
	
	/**
	 * Adds vertices from 0 to <code>size</code> and edges between them.
	 * @param size number of vertices to add.
	 */
	private void addBigPath(int size) {
		graphBuilder.vertices(size);
		for (int i = 0; i < size -1; i++) {
			addEdge(i, i+1);
		}
	}
	@Test
	public void whenRightToLeftBigThenFound() {
		addBigPath(10);
		addEdge(RIGHT, 0);
		addEdge(9, LEFT);
		assertSuccess(12);
	}
	
	@Test
	public void whenLeftToRightBigThenFound() {
		addBigPath(10);
		addEdge(LEFT, 0);
		addEdge(9, RIGHT);
		assertSuccess(12);
	}
	
	@Test
	public void whenMultiplePathsThenShortestFound() {
		addBigPath(10);
		addEdge(LEFT, 0);
		addEdge(9, RIGHT);
		addEdge(LEFT, 9);
		assertSuccess(3);
	}

	@Test
	public void whenCycleItStillWorks() {
		addBigPath(10);
		addEdge(LEFT, 0);
		addEdge(0, LEFT);
		addEdge(9, RIGHT);
		addEdge(RIGHT, 9);
		addEdge(5, 3);
		assertSuccess(12);
	}

}
