/**
 * 
 */
package net.blesso.datastruct;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test class for {@link ArrayUtils}.
 *
 * @author francis
 */
public class ArrayUtilsTest {

	/**
	 * Test method for {@link net.blesso.datastruct.ArrayUtils#compare(T[], int, int, java.util.Comparator)}.
	 */
	@Test
	public void testCompare_whenBothNullThenZero() {
		assertThat(ArrayUtils.compare(new Integer[]{null, null}, 0, 1, null), equalTo(0));
	}
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testCompare_whenBothOutOfBoundsThenException() {
		ArrayUtils.compare(new Integer[]{1, 2}, 100, 1900, null);
	}	
	@Test
	public void testCompare_whenFirstEqualToSecondThenZero() {
		assertThat(ArrayUtils.compare(new Integer[]{3, 3}, 0, 1, null), equalTo(0));
	}
	
	@Test(expected = NullPointerException.class)
	public void testCompare_whenFirstNullThenException() {
		assertThat(ArrayUtils.compare(new Integer[]{null, 2}, 0, 1, null), equalTo(1));
	}
	@Test
	public void testCompare_whenFirstSmallerThanSecondThenPositive() {
		assertThat(ArrayUtils.compare(new Integer[]{1, 2}, 0, 1, null), equalTo(1));
	}
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testCompare_whenFirstOutOfBoundsThenException() {
		assertThat(ArrayUtils.compare(new Integer[]{3, 3}, 100, 1, null), equalTo(1));
	}

	@Test(expected = NullPointerException.class)
	public void testCompare_whenSecondNullThenException() {
		assertThat(ArrayUtils.compare(new Integer[]{2, null}, 0, 1, null), equalTo(-1));
	}
	@Test
	public void testCompare_whenFirstBiggerThanSecondThenNegative() {
		assertThat(ArrayUtils.compare(new Integer[]{2, 1}, 0, 1, null), equalTo(-1));
	}
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testCompare_whenSecondOutOfBoundsThenException() {
		assertThat(ArrayUtils.compare(new Integer[]{3, 3}, 1, 100, null), equalTo(-1));
	}

	/**
	 * Test method for {@link net.blesso.datastruct.ArrayUtils#swap(T[], int, int)}.
	 */
	@Test
	public void testSwap() {
		final Integer[] arr = {1,2};
		ArrayUtils.swap(arr, 0, 1);
		assertThat(arr[0], equalTo(2));
		assertThat(arr[1], equalTo(1));
	}

}
