package net.blesso.puzzle;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NQueensTest {

	@Test
	public void testNumPos1() {
		assertThat(new NQueens().numPos(1), equalTo(1));
	}

	@Test
	public void testNumPos2() {
		assertThat(new NQueens().numPos(2), equalTo(0));
	}

	@Test
	public void testNumPos3() {
		assertThat(new NQueens().numPos(3), equalTo(0));
	}

	@Test
	public void testNumPos4() {
		assertThat(new NQueens().numPos(4), equalTo(2));
	}

	@Test
	public void testNumPos8() {
		assertThat(new NQueens().numPos(8), equalTo(92));
	}

}
