package net.blesso.datastruct.graph;

/**
 * An {@link EdgeWeight} that has a fixed value. 
 * @param <T> the type of object connected by the edge.
 * @author fblesso
 */
public class ConstantEdgeWeight<T> implements EdgeWeight<T> {
		private final double weight;
		
		private ConstantEdgeWeight(double weight) {
			this.weight = weight;
		}
		
		/**
		 * Factory method to create a {@link ConstantEdgeWeight}.
		 * @param weight the weight to use
		 * @return a {@link ConstantEdgeWeight} with the desired <code>weight</code>
		 */
		public static <S> ConstantEdgeWeight<S> of(double weight) {
			return new ConstantEdgeWeight<S>(weight);
		}
		
		/**
		 * Factory method to create a {@link ConstantEdgeWeight} for an int.
		 * @param weight the weight to use
		 * @return a {@link ConstantEdgeWeight} with the desired <code>weight</code>
		 */
		public static <S> ConstantEdgeWeight<S> of(int weight) {
			return new ConstantEdgeWeight<S>(weight);
		}
		@Override
		public double weight(Vertex<T> start, Vertex<T> end) {
			return weight;
		}
	}