package net.blesso.datastruct.graph;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Finds the shortest path between two {@link Vertex}s, searching progressively outward from
 * each vertex.
 */
public class BidirectionalSearcher<T> {
	
	/** The graph to search. */
	private final Graph<T> graph;
	
	/** First vertex to search between. */
	private final Vertex<T> left;
	
	/** Second vertex to search between. */
	private final Vertex<T> right;
	
	/** Tracks vertices we've visited from the left. */
	final Set<Vertex<T>> visitedLeft = new HashSet<>();
	
	/** Tracks vertices we've visited from the right. */
	final Set<Vertex<T>> visitedRight = new HashSet<>();
	
	/** for building the path back to the right vertex */
	final Map<Vertex<T>, Vertex<T>> rightParentMap = new HashMap<>();

	/** for building the path back to the left vertex */
	final Map<Vertex<T>, Vertex<T>> leftParentMap = new HashMap<>();
	
	public BidirectionalSearcher(Graph<T> graph, Vertex<T> left, Vertex<T> right) {
		this.graph = graph;
		this.left = left;
		this.right = right;
	}

	/**
	 * @return {@link List} of {@link Vertex} of the shortest path from left to right or null if no path exists.
	 */
	public List<Vertex<T>> bidirectionalSearch() {
		Searcher sleft = new Searcher(left, right, false);
		Searcher sright = new Searcher(right, left, true);
		while (sleft.hasMore() || sright.hasMore()) {
			List<Vertex<T>> pathr = sright.searchNext();
			if (pathr != null) {
				return pathr;
			}
			List<Vertex<T>> pathl = sleft.searchNext();
			if (pathl != null) {
				return pathl;
			}
		}
		return null;
	}
	
	private class Searcher {
		
		final Vertex<T> target;
		final boolean isRight;
		final Deque<Vertex<T>> queue = new LinkedList<>();
		final Set<Vertex<T>> processed = new HashSet<>();
		
		Searcher(Vertex<T> start, Vertex<T> target, boolean isRight) {
			//this.start = start;
			this.target = target;
			this.isRight = isRight;
			if (isRight) {
				visitedRight.add(start);
			} else {
				visitedLeft.add(start);
			}
			queue.add(start);
		}
		
		boolean hasMore() {
			return !queue.isEmpty();
		}
		/**
		 * Processes the next vertex in the search queue, adding its children to the search queue. It 
		 * also sets the child's pointer to its parent and records whether the vertex has been visited
		 * from the left or right. 
		 * @return a {@link List} of {@link Vertex} if the path has been found, otherwise null
		 */
		List<Vertex<T>> searchNext() {
			if (queue.isEmpty()) {
				return null;
			}
			final Vertex<T> currVertex = queue.removeFirst();
			for(Edge<T> edge : graph.findEdges(currVertex)) {
				final Vertex<T> child = graph.findVertex(edge.getEnd());
				if (processed.contains(child)) {
					continue;
				}
				if (isRight) {
					visitedRight.add(child);
					rightParentMap.put(child, currVertex);
				} else {
					visitedLeft.add(child);
					leftParentMap.put(child, currVertex);
				}
				if (target.equals(child) || (visitedLeft.contains(child) && visitedRight.contains(child))) {
					return buildPath(child);
				}
				queue.add(child);
			}
			processed.add(currVertex);
			return null;
		}
		
		/**
		 * @param vertex a vertex anywhere on the path.
		 * @return the List of {@link Vertex<T>} from original left vertex to the right vertex. 
		 */
		List<Vertex<T>> buildPath(Vertex<T> vertex) {
			final List<Vertex<T>> path = new ArrayList<>();
			path.add(vertex);
			Vertex<T> leftParent = leftParentMap.get(vertex);
			Vertex<T> rightParent = rightParentMap.get(vertex);
			while (leftParent != null) {
				path.add(0, leftParent);
				leftParent = leftParentMap.get(leftParent);
			}
			while (rightParent != null) {
				path.add(rightParent);
				rightParent = rightParentMap.get(rightParent);
			}
			return path;
		}
	}
}
