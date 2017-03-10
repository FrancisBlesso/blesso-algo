package net.blesso.sort;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import net.blesso.sort.MergeSorter;

public class MergeSorterTest {
	
	private MergeSorter<Integer> sorter;

	@Test
	public void testSort2() {
		final Integer[] in = {3,12,8, 2, 20, 45, 1, 2};
		final Integer[] expected = {1, 2, 2, 3, 8, 12, 20, 45};
		testSort(in, expected);
	}

	@Test
	public void testSort1() {
		final Integer[] in = {12, 8};
		final Integer[] expected = {8, 12};
		testSort(in, expected);
	}

	@Test
	public void testCombineEasy() {
		sorter = new MergeSorter<Integer>(new Integer[0], Integer.class);
		final Integer[] a = {5};
		final Integer[] b = {3};
		final Integer[] expected = {3, 5};
		testCombine(a, b, expected);
	}

	@Test
	public void testCombine2y() {
		sorter = new MergeSorter<Integer>(new Integer[0], Integer.class);
		final Integer[] a = {1,2,5, 19};
		final Integer[] b = {3,7,7,21};
		final Integer[] expected = {1, 2, 3, 5, 7, 7, 19, 21};
		testCombine(a, b, expected);
	}
	
	private void testCombine(Integer[] a, Integer[] b, Integer[] expected) {
		sorter = new MergeSorter<Integer>(new Integer[0], Integer.class);
		assertThat(sorter.combine(a, b), CoreMatchers.equalTo(expected));
		assertThat(sorter.combine(b, a), CoreMatchers.equalTo(expected));
	}

	private void testSort(Integer[] in, Integer[] expected) {
		sorter = new MergeSorter<Integer>(in, Integer.class);
		assertThat(sorter.sort(), CoreMatchers.equalTo(expected));
	}
}
