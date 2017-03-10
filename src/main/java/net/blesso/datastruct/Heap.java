/**
 * 
 */
package net.blesso.datastruct;

import java.util.Comparator;

/**
 * Heap implementation wrapping an existing array. It creates the heap and sorts it in place, allowing the consumer to 
 * immediately see any changes. The calling class needs to ensure the array is large enough for all put operations. 
 * 
 * Factory methods provide the capability to create either a min- or max-heap as well as to supply a {@link Comparator} to 
 * specify the ordering. 
 *
 * @param <T> the type of elements in the heap
 * 
 * @author francis
 */
public class Heap<T extends Comparable<T>> {

	private final static int MAX = 1;
	private final static int MIN = -1;
	
	/** The array containing the heap. */
	private final T[] arr;
	
	/** Whether we have a max or min heap */
	private final int dir;
	
	/** 
	 * Specifies the heap's ordering, if null the heap will use the element's natural ordering specified by 
	 * {@link Comparable#compareTo(Object)}. 
	 * */
	private final Comparator<T> comparator;
	
	private int size;

	/**
	 * Stores the reference to the array and turns it into a heap. 
	 * 
	 * @param a the array on which to build the heap
	 * @param dir whether to create a min- or max-heap
	 * @param comparator for specifying the comparison
	 */
	private Heap(T[] a, int dir, Comparator<T> comparator) {
		this.arr = a;
		this.dir = dir;
		this.comparator = comparator;
		heapify();
		size = 0;
		for(int i=0; i< arr.length; i++) {
			if (arr[i] != null) {
				++size;
			}
		}
	}
	
	/**
	 * @param arr the array to heapify
	 * @return a max heap ordered by element's default compareTo method
	 */
	public static <T extends Comparable<T>> Heap<T> max(T[] arr) {
		return new Heap<T>(arr, MAX, null);
	}
	
	/**
	 * @param arr the array to heapify
	 * @param comparator determines how to order the heap's elements
	 * @return a max heap ordered by the comparator
	 */
	public static <T extends Comparable<T>> Heap<T> max(T[] arr, Comparator<T> comparator) {
		return new Heap<T>(arr, MAX, comparator);
	}
	
	/**
	 * @param arr the array to heapify
	 * @return a min heap ordered by element's default compareTo method
	 */
	public static <T extends Comparable<T>> Heap<T> min(T[] arr) {
		return new Heap<T>(arr, MIN, null);
	}
	
	/**
	 * @param arr the array to heapify
	 * @param comparator determines how to order the heap's elements
	 * @return a min heap ordered by the comparator
	 */
	public static <T extends Comparable<T>> Heap<T> min(T[] arr, Comparator<T> comparator) {
		return new Heap<T>(arr, MIN, comparator);
	}
	
	/**
	 * @return the element at the top of the heap
	 */
	public T peek() {
		return arr[0];
	}
	
	/**
	 * Removes the heap's top element and rebuilds the heap so the next highest element is at the top.
	 * @return the heap's top element
	 */
	public T pop() {
		return pop(arr.length -1);
	}
	
	/**
	 * @param limit the start of the array that contains non-heap, used when sorting the heap
	 * @return the heap's top element
	 */
	private T pop(int limit) {
		final T topOfTheHeap = arr[0];
		int lastNonNull = limit;
		while(lastNonNull > 0 && arr[lastNonNull] == null) {
			lastNonNull--;
		}
		arr[0] = arr[lastNonNull];
		arr[lastNonNull] = null;
		siftDown(0, limit);
		size--;
		return topOfTheHeap;
	}
	
	/**
	 * Add the item to the heap, reordering it so the heap is consistent. 
	 * @param item the item to add to the heap
	 * @throws RuntimeException if there is no more space in the heap
	 */
	public void push(T item){
		int firstNull = 0;
		while(firstNull < arr.length && arr[firstNull] != null) {
			firstNull++;
		}
		if (firstNull >= arr.length) {
			throw new RuntimeException("not enough space to push a new element");
		}
		arr[firstNull] = item;
		siftUp(firstNull);
		size++;
	}
	
	public int getSize() {
		return size;
	}
	
	/**
	 * Reorganizes the array so that it is in a heap structure, with the top node at position 0.
	 */
	public void heapify(){
		for (int i = arr.length/2; i>=0; i--) {
			siftDown(i, arr.length);
		}
	}
	
	/**
	 * Converts the heap into a sorted array. If you want to restore the array to a heap, you'll need to call {@link #heapify()}.
	 */
	public void sort() {
		for(int i = arr.length -1; i > 0; i--){
			arr[i] = pop(i);
		}
	}
	
	/**
	 * Moves the item at <code>pos</code> down the heap until it is greater than both its children.
	 * @param pos the position of the element ot sift  down.
	 * @param limit the heap's end position in the array 
	 */
	void siftDown(int pos, int limit){
		
		final int largest = largest(pos, limit); 
		//if the node is greater than both its children, we don't need to do anything.
		if (largest == pos) {
			return;
		}
		//otherwise, a child is larger so we need to swap the child and parent and 
		//continue with the child node. 
		ArrayUtils.swap(arr, pos, largest);
		siftDown(largest, limit);
	}
	
	/**
	 * Moves the item at <code>pos</code> up the heap until it is less than its parent.
	 * @param pos the position of the element to sift up.
	 */
	void siftUp(int pos) {
		if (pos <= 0) {
			return;
		}
		int parent = (pos - 1) / 2;
		if (compareTo(pos, parent, arr.length) < 0) {
			ArrayUtils.swap(arr, pos, parent);
			siftUp(parent);
		}
	}

	/**
	 * Compares the value at <code>pos</code> with its children and returns the position of the greatest of the three.
	 * @return the index of the node with the greatest value.
	 */
	int largest(int pos, int limit) {
		final int left = 2 * pos + 1;
		final int right = left + 1;
		int largest = pos;
		if (compareTo(pos, left, limit) > 0) {
			largest = left;
		}
		if (compareTo(largest, right, limit) > 0) {
			largest = right;
		}
		return largest;
	}
	
	/**
	 * Compares the values at two positions excluding any that are at or beyond the end position. 
	 * If exactly one position is greater or equal to the end position or refers to a null value in the array, then the other position is greater.
	 * When sorting, we put the sorted elements at the end of the array. The <code>limit</code> parameter is the position of the first sorted elements.
	 * 
	 * @param pos1 position of first value to compare
	 * @param pos2 position of second value to compare
	 * @param limit max position within the array to consider. 
	 * @return a negative number, zero, or a positive number, corresponding to whether the item at position 1 is less than, equal to, or bigger than the item at position 2.
	 * @see ArrayUtils#compare(Comparable[], int, int, Comparator)
	 */
	private int compareTo(int pos1, int pos2, int limit) {
		final T t1 = pos1 < limit ? arr[pos1] : null;
		final T t2 = pos2 < limit ? arr[pos2] : null;
		if (t1 == t2) {
			return 0;
		}

		if (t1 == null) {
			return 1;
		} else if (t2 == null) {
			return -1;
		}
		int compareValue = ArrayUtils.compare(arr, pos1, pos2, comparator);
		return dir * compareValue;
	}
}
