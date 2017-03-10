/**
 * 
 */
package net.blesso.datastruct;

import java.util.Comparator;

/**
 * @author francis
 *
 */
public class ArrayUtils {
	
	/**
	 * Compares two items in the array, using the supplied {@link Comparator} or the element's natural 
	 * ordering. 
	 * @param arr the array containing the elements to compare
	 * @param pos1 the position of the first element
	 * @param pos2 the position of the first element
	 * @param comparator specifies the compare operation, if null uses the element's natural ordering
	 * @return a negative number if the first is smaller than the second, a positive number if the first is bigger than the second
	 */
	public static <T extends Comparable<T>> int compare(T[] arr, int pos1, int pos2, Comparator<T> comparator) {
		final T t1 = arr[pos1];
		final T t2 = arr[pos2];
		if (t1 == t2) {
			return 0;
		}
		
		final int compareValue = (comparator == null) ? t2.compareTo(t1) : comparator.compare(t1, t2);
		return compareValue;
		
	}
	
	/**
	 * Exchanges two elements in the array. 
	 * @param arr the array containing the elements to swap
	 * @param pos1 the position of the first element
	 * @param pos2 the position of the second element
	 */
	public static <T extends Comparable<T>> void swap(T[] arr, int pos1, int pos2) {
		final T temp = arr[pos1];
		arr[pos1] = arr[pos2];
		arr[pos2] = temp;		
	}

}
