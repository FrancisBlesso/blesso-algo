/**
 * 
 */
package net.blesso.datastruct.graph;

/**
 * Specifies the cost between a start and end vertex connected by an edge.
 *
 * @author fblesso
 */
@FunctionalInterface
public interface EdgeWeight<T> {
	double weight(Vertex<T> start, Vertex<T> end);
}
