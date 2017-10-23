package net.blesso.datastruct.graph;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BidirectionalSearcher {
	
	private final Node left;
	private final Node right;
	
	/** Tracks nodes we've visited from the left side. */
	final Set<Node> visitedLeft = new HashSet<>();
	
	/** Tracks nodes we've visited from the right side. */
	final Set<Node> visitedRight = new HashSet<>();
	
	/** for building the path back to the right node */
	final Map<Node, Node> rightParentMap = new HashMap<>();

	/** for building the path back to the left node */
	final Map<Node, Node> leftParentMap = new HashMap<>();
	
	public BidirectionalSearcher(Node left, Node right) {
		this.left = left;
		this.right = right;
	}

	/**
	 * Finds the shortest path between two {@link Node}s, searching progressively outward from
	 * each node.
	 * @return {@link List} of {@link Node} of the shortest path from left to right or null if no path exists.
	 */
	public List<Node> bidirectionalSearch() {
		Searcher sleft = new Searcher(left, right, false);
		Searcher sright = new Searcher(right, left, true);
		while (sleft.hasMore() || sright.hasMore()) {
			List<Node> pathr = sright.searchNext();
			if (pathr != null) {
				return pathr;
			}
			List<Node> pathl = sleft.searchNext();
			if (pathl != null) {
				return pathl;
			}
		}
		return null;
	}
	
	public static class Node {
		final private List<Node> children = new ArrayList<>();
		
		public void addChild(Node child) {
			children.add(child);
		}
	}
	
	private class Searcher {
		
		//final Node start;
		final Node target;
		final boolean isRight;
		final Deque<Node> queue = new LinkedList<>();
		final Set<Node> processed = new HashSet<>();
		
		Searcher(Node start, Node target, boolean isRight) {
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
		 * Processes the next node in the search queue, adding its children to the search queue. It 
		 * also sets the child's pointer to its parent and records whether the node has been visited
		 * from the left or right. 
		 * @return a {@link List} of {@link Node} if the path has been found, otherwise null
		 */
		List<Node> searchNext() {
			if (queue.isEmpty()) {
				return null;
			}
			Node currNode = queue.removeFirst();
			for(Node child : currNode.children) {
				if (processed.contains(child)) {
					continue;
				}
				if (isRight) {
					visitedRight.add(child);
					rightParentMap.put(child, currNode);
				} else {
					visitedLeft.add(child);
					leftParentMap.put(child, currNode);
				}
				if (target.equals(child) || (visitedLeft.contains(child) && visitedRight.contains(child))) {
					return buildPath(child);
				}
				queue.add(child);
			}
			processed.add(currNode);
			return null;
		}
		
		/**
		 * @param node a node anywhere on the path.
		 * @return the List of {@link Node} from original left node to the right node. 
		 */
		List<Node> buildPath(Node node) {
			List<Node> path = new ArrayList<>();
			path.add(node);
			Node leftParent = leftParentMap.get(node);
			Node rightParent = rightParentMap.get(node);
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
