package net.blesso.datastruct.graph;

import net.blesso.datastruct.TreeNode;

/**
 * Contains a {@link Vertex}'s metadata managed by graph algorithm.
 * @author fblesso
 *
 * @param <T> the type of object the vertex refers to
 */
public final class VertexFlags<T> {
	
	/**
	 * Tracks the {@link Vertex}'s state transitions during the graph algorithm. 
	 * The valid transitions are {@link #UNDISCOVERED} => {@link #DISCOVERED} => {@link #PROCESSED}.
	 *
	 */
	public enum VertexState {
		UNDISCOVERED,
		DISCOVERED,
		PROCESSED,
	}

	/** The Vertex corresponding to the search state. */
	//TODO not sure whether we need this.
	private final Vertex<T> vertex;
	
	/** The internal tree node built by the graph algorithm. */
	private final TreeNode<Vertex<T>> treeNode;
	
	/** The sequence the algorithm started processing the vertex. */
	private int entryTime;
	
	/** The sequence the algorithm finished processing the vertex. */
	private int exitTime;
	
	/** Tracks what the graph algorithm has done with the vertex. */
	private VertexFlags.VertexState state = VertexState.UNDISCOVERED;
	
	public VertexFlags(Vertex<T> vertex) {
		this.vertex = vertex;
		treeNode = new TreeNode<>(vertex);
	}
	public int getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(int entryTime) {
		this.entryTime = entryTime;
	}
	public Vertex<T> getVertex() {
		return vertex;
	}
	public TreeNode<Vertex<T>> getTreeNode() {
		return treeNode;
	}
	public int getExitTime() {
		return exitTime;
	}
	public void setExitTime(int exitTime) {
		this.exitTime = exitTime;
	}
	public VertexFlags.VertexState getState() {
		return state;
	}
	public void setDiscovered() {
		if (state != VertexState.UNDISCOVERED) {
			throw new RuntimeException("Vertex is already discovered!" + this);
		}
		state = VertexState.DISCOVERED;
	}
	public void setProcessed() {
		if (state != VertexState.DISCOVERED) {
			throw new RuntimeException("Vertex is not discovered!" + this);
		}
		state = VertexState.PROCESSED;
	}
}