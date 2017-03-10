package net.blesso.datastruct;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import net.blesso.datastruct.Heap;

/**
 * Test class for {@link Heap}.
 *
 * @author francis
 */
public class HeapTest {

	@Test
	public void testHeapify() {
		Integer[] arr = {1,2,3,4,5,6,7,8,9};
		Heap.max(arr);
		assertThat(arr, equalTo(new Integer[]{9,8,7,4,5,6,3,2,1}));
	}

	@Test
	public void testSiftDown() {
		Integer[] arr = {1,2,3};
		Heap.max(arr).siftDown(0, 3);
		assertThat(arr[0], equalTo(3));
		assertThat(arr[1], equalTo(2));
		assertThat(arr[2], equalTo(1));
		assertThat(arr, equalTo(new Integer[]{3,2,1}));
	}

	@Test
	public void testArrayHeapPop() {
		final Heap<Integer> heap = Heap.max(new Integer[]{1,2,3,4});
		assertThat(heap.pop(), equalTo(4));
		assertThat(heap.pop(), equalTo(3));
		assertThat(heap.pop(), equalTo(2));
		assertThat(heap.pop(), equalTo(1));
		assertThat(heap.pop(), nullValue());
	}

	@Test
	public void testMinArrayHeapPop() {
		final Heap<Integer> heap = Heap.min(new Integer[]{1,2,3,4});
		assertThat(heap.pop(), equalTo(1));
		assertThat(heap.pop(), equalTo(2));
		assertThat(heap.pop(), equalTo(3));
		assertThat(heap.pop(), equalTo(4));
		assertThat(heap.pop(), nullValue());
	}

	@Test
	public void testPush() {
		Integer[] arr = {1,null, null, null, 2, null, null, null};
		final Heap<Integer> heap = Heap.max(arr);
		heap.push(5);
		assertThat(arr, equalTo(new Integer[]{5, 1, 2, null, null, null, null, null}));
		heap.push(8);
		assertThat(arr, equalTo(new Integer[]{8, 5, 2, 1, null, null, null, null}));
		heap.push(1);
		assertThat(arr, equalTo(new Integer[]{8, 5, 2, 1, 1, null, null, null}));
		heap.push(3);
		assertThat(arr, equalTo(new Integer[]{8, 5, 3, 1, 1, 2, null, null}));
	}

	@Test
	public void testLargest() {
		final Integer[] arr = {1,2,3,4};
		final Heap<Integer> heap = Heap.max(arr);
		assertThat(heap.largest(0, 4), equalTo(0));
		assertThat(heap.largest(1, 4), equalTo(1));
		assertThat(heap.largest(2, 4), equalTo(2));
		assertThat(heap.largest(3, 4), equalTo(3));
	}

	@Test
	public void testLargestOutOfBounds() {
		final Integer[] arr = {1,2,3};
		final Heap<Integer> heap = Heap.max(arr);
		assertThat(heap.largest(2, 3), equalTo(2));
		assertThat(heap.largest(3, 3), equalTo(3));
	}

	@Test
	public void testLargestLeftNull() {
		Integer[] arr = {3,null,1};
		final Heap<Integer> heap = Heap.max(arr);
		assertThat(heap.largest(0,3 ), equalTo(0));
	}

	@Test
	public void testLargestRightNull() {
		Integer[] arr = {3,1,null};
		final Heap<Integer> heap = Heap.max(arr);
		assertThat(heap.largest(0, 3), equalTo(0));
	}
	
	@Test
	public void getSize_whenAllNullIsZero() {
		final Heap<Integer> heap = Heap.max(new Integer[]{null, null});
		assertThat(heap.getSize(), equalTo(0));
	}
	
	@Test
	public void getSize_whenEmptyIsZero() {
		final Heap<Integer> heap = Heap.max(new Integer[]{});
		assertThat(heap.getSize(), equalTo(0));
	}
	
	@Test
	public void getSize_whenNullInMiddleIsOneLess() {
		Integer[] arr = {3,null,1};
		final Heap<Integer> heap = Heap.max(arr);
		assertThat(heap.getSize(), equalTo(2));
	}
	
	@Test
	public void getSize_whenFullIsCorrect() {
		Integer[] arr = {3,10,1};
		final Heap<Integer> heap = Heap.max(arr);
		assertThat(heap.getSize(), equalTo(3));
	}
	
	@Test
	public void getSize_afterPopIsOneLess() {
		Integer[] arr = {3,10,1};
		final Heap<Integer> heap = Heap.max(arr);
		heap.pop();
		assertThat(heap.getSize(), equalTo(2));
	}
	
	@Test
	public void getSize_afterPushIsOneMore() {
		Integer[] arr = {3,10,null,null,5};
		final Heap<Integer> heap = Heap.max(arr);
		heap.push(2);
		assertThat(heap.getSize(), equalTo(4));
	}
	
	@Test
	public void testSort() {
		final Integer[] arr = new Integer[]{4,2,9,4,5,12,1};
		final Heap<Integer> heap = Heap.max(arr);
		heap.sort();
		assertThat(arr, equalTo(new Integer[]{1, 2, 4, 4, 5, 9, 12}));
	}

}
