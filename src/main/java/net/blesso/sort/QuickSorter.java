package net.blesso.sort;

/**
 * Quicksort implementation.
 *
 * @param <T> the type of elements to sort
 *
 * @author francis
 */
public class QuickSorter<T extends Comparable<T>> {
	
	/** the array to sort. */
	private final T[] arr;

	public QuickSorter(T[] a) {
		this.arr = a;
	}

	/**
	 * Sorts the array using quicksort. 
	 * @return the sorted array
	 */
	public T[] sort(){
		quickSort(0, arr.length -1);
		return arr;
	 }

	/**
	 * Partitions the array and recursively sorts the subarrays on both sides of the partition until the partition size is one.
	 * @param low the left index of the subarray to sort
	 * @param high the right index of the subarray to sort
	 */
	private void quickSort(int low, int high) {
		if (high > low) {
			int partitionLocation = partition(low, high);
			quickSort(low, partitionLocation -1 );
			quickSort(partitionLocation + 1, high);
		}
	}
	
	private void swap(int pos1, int pos2){
		T temp = arr[pos1];
		arr[pos1] = arr[pos2];
		arr[pos2] = temp;
	}
	
	/**
	 * Identifies a partition value and then reorganizes the sub array so that one side has values less than the partition value,
	 * the other side has values greater than the partition value, and the partition is in between both sides.
	 * 
	 * @param low the left index of the subarray to partition
	 * @param high the right index of the subarray to partition
	 * @return position of the partition element, in between low and high
	 */
	private int partition(int low, int high) {
		int partitionGuess = (high + low)/2;
		final T partitionValue = arr[partitionGuess];
		
		//store the partition value in the last position
		swap(partitionGuess, high);
		
		int lowIndex = low;
		int highIndex = high - 1; //ignore the last position because it contains the partition
		while (true) {
			while (arr[lowIndex].compareTo(partitionValue) < 0 ) {
				lowIndex++;
			} 			while(highIndex > lowIndex && arr[highIndex].compareTo(partitionValue) > 0 ) {
				highIndex--;
			} 
			if (lowIndex >= highIndex){
				//swap the partition value back into the partition index
				swap(high, lowIndex);
				return lowIndex;
			}
			swap(lowIndex, highIndex);
			lowIndex++;
			highIndex--;
		
		}
	}
}
