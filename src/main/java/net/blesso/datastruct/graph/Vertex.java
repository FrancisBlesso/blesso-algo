/**
 * 
 */
package net.blesso.datastruct.graph;

/**
 * A vertex within a graph. We use a separate id instead of the actual item so that it is 
 * easier to specify vertexes when creating graphs
 * @author fblesso
 *
 * @param <T> the type of object managed by the vertex.
 */
public class Vertex<T> {
	/** A separate identifier from the item, which makes specifing graph's easier */ 
	private final Long id;
	
	/** The item in the graph. */
	private final T item;
	
	public Vertex(long id, T item) {
		this.id = id;
		this.item = item;
	}
	
	/**
	 * Creates a {@link Vertex} for an integer having its id equal to its value. 
	 * @param id the vertex's value and id.
	 * @return the new {@link Vertex}
	 */
	public static Vertex<Integer> of(int id){
		return new Vertex<Integer>(id, id);
	}
	
	/**
	 * Creates a {@link Vertex} for a Long having its id equal to its value. 
	 * @param id the vertex's value and id.
	 * @return the new {@link Vertex}
	 */
	public static Vertex<Long> of(Long id){
		return new Vertex<Long>(id, id);
	}

	public long getId() {
		return id;
	}

	public T getItem() {
		return item;
	}

	/**
	 * Uses the id's hash code.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	/** 
	 * Compares the vertex's id to the other's id.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		Vertex other = (Vertex) obj;
		return id.equals(other.id);
	}

	@Override
	public String toString() {
		return "Vertex id: " + id + " item:" + item;
	}
	
}
