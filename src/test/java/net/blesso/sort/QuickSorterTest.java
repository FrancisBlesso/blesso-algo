package net.blesso.sort;

import static org.junit.Assert.assertThat;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import net.blesso.sort.QuickSorter;

/**
 * Test case for {@link QuickSorter}.
 * 
 * @author francis
 */
public class QuickSorterTest {
	
	@Test
	public void testSort8() {
		final Integer[] in = {3,12,8, 2, 20, 45, 1, 2};
		final Integer[] expected = {1, 2, 2, 3, 8, 12, 20, 45};
		testSort(in, expected);
	}

	@Test
	public void testSort7() {
		final Integer[] in = {3,12,8, 2, 20, 45, 1};
		final Integer[] expected = {1, 2, 3, 8, 12, 20, 45};
		testSort(in, expected);
	}

	@Test
	public void testSort6() {
		final Integer[] in = {3,12,8, 2, 20, 45};
		final Integer[] expected = {2, 3, 8, 12, 20, 45};
		testSort(in, expected);
	}

	@Test
	public void testSort5() {
		final Integer[] in = {3,12,8, 2, 20};
		final Integer[] expected = {2, 3, 8, 12, 20};
		testSort(in, expected);
	}

	@Test
	public void testSort4() {
		final Integer[] in = {3,12,8, 2};
		final Integer[] expected = {2, 3, 8, 12};
		testSort(in, expected);
	}

	@Test
	public void testSort3() {
		final Integer[] in = {3,12,8};
		final Integer[] expected = {3, 8, 12};
		testSort(in, expected);
	}

	@Test
	public void testSort2() {
		final Integer[] in = {12, 8};
		final Integer[] expected = {8, 12};
		testSort(in, expected);
	}

	@Test
	public void testRandom() {
		for(int i=1; i< 7; i++) {
			testSortRandom((int) Math.pow(10,  i), 2);
		}
	}

	private void testSortRandom(int size, int bound) {
		final Integer[] in = random(size, bound);
		final Integer[] expected = in.clone();
		Instant before = Instant.now();
		Arrays.sort(expected);
		Instant after = Instant.now();
		System.out.println("size=" + size + " java sortTime=" + Duration.between(before, after));
		testSort(in, expected);
	}

	private void testSort(Integer[] in, Integer[] expected) {
		final QuickSorter<Integer> sorter = new QuickSorter<>(in);

		Instant before = Instant.now();
		Integer[] actual = sorter.sort();
		Instant after = Instant.now();
		System.out.println("size=" + in.length + " MY sortTime=" + Duration.between(before, after));
		System.out.println(in.length + " => " + actual.length);
		assertThat(actual, CoreMatchers.equalTo(expected));
	}
	
	private Integer[] random(int size, int bound) {
		final Integer[] ints = new Integer[size];
		for(int i=0; i < size; i++){
			ints[i] = new Random().nextInt(bound);
		}
		return ints;
	}
}
