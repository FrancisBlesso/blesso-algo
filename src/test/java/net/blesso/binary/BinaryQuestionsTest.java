package net.blesso.binary;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class BinaryQuestionsTest {

	@Test
	public void testSwapBitPairs() {
		assertThat(new BinaryQuestions().swapBitPairs(1), CoreMatchers.equalTo(2));
		assertThat(new BinaryQuestions().swapBitPairs(2), CoreMatchers.equalTo(1));
		assertThat(new BinaryQuestions().swapBitPairs(3), CoreMatchers.equalTo(3));
		assertThat(new BinaryQuestions().swapBitPairs(4), CoreMatchers.equalTo(8));
		assertThat(new BinaryQuestions().swapBitPairs(6), CoreMatchers.equalTo(9));
	}

}
