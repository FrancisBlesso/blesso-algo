package net.blesso.sort;

import java.lang.reflect.Array;

/**
 * Demonstrates merge sort algorithm. 
 * 
 * @author francis
 * @param <T> the type of elements to sort
 */
public class MergeSorter<T extends Comparable<T>> {
	
	/** The array to sort. */
	private final T[] arr;
	
	/** Class of objects to sort, for creating arrays of <T> objects. */
	private final Class<T> clazz;

	public MergeSorter(T[] a, Class<T> clazz) {
		this.arr = a;
		this.clazz = clazz;
	}

	public T[] sort(){
		final T[] cloned = arr.clone();
		 T[] b = mergeSort(cloned, 0, arr.length -1);
		 for(int i=0; i< arr.length; i++) {
			 arr[i] = b[i];
		 }
		 return arr;
	 }
	
	private T[] newArray(int size) {
		return (T[]) Array.newInstance(clazz, size);
	}

	/**
	 * Splits the sub-array into two and recursively invokes itself until there is a single element remaining. After sorting the two arrays, merges them back together.
	 * @param a the array to sort
	 * @param low start of the subarray
	 * @param high end of the subarray
	 * @return the sorted array.
	 */
	private T[] mergeSort(T[] a, int low, int high) {
		if (high <= low) {
			T[] singleArray = newArray(1);
			singleArray[0] = a[low];
			return singleArray;
		}
		int middle = (low + high)/2;
		T[] b = mergeSort(a, low, middle);
		T[] c = mergeSort(a, middle + 1, high);
		return combine(b, c);
	}

	/**
	 * Merges the two arrays so the new array is sorted.
	 * @return the merged array
	 */
	T[] combine(T[] b, T[] c) {
		final T[] combined =  newArray(b.length + c.length);
		
		int bIndex = 0;
		int cIndex = 0;
		while (bIndex < b.length && cIndex < c.length) {
			final int diff = b[bIndex].compareTo(c[cIndex]);
			if(diff <= 0) {
				combined[bIndex + cIndex] = b[bIndex];
				bIndex++;
			} else {
				combined[bIndex + cIndex] = c[cIndex];
				cIndex++;
			}
		}

		while (bIndex < b.length) {
			combined[bIndex + cIndex] = b[bIndex];
			bIndex++;
		}
		while (cIndex < c.length) {
			combined[bIndex + cIndex] = c[cIndex];
			cIndex++;
		}
		
		
		return combined;
	}	 
	 
}
