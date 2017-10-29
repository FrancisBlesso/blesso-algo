package net.blesso.number;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class AtkinSieveTest {

	@Test
	public void get4XSquaredPlusYSquaredSolutions_1() {
		assertThat(AtkinSieve.get4XSquaredPlusYSquaredSolutions(1), CoreMatchers.equalTo(0));
	}

	@Test
	public void get4XSquaredPlusYSquaredSolutions_13() {
		assertThat(AtkinSieve.get4XSquaredPlusYSquaredSolutions(13), CoreMatchers.equalTo(1));
	}

	@Test
	public void get4XSquaredPlusYSquaredSolutions_17() {
		assertThat(AtkinSieve.get4XSquaredPlusYSquaredSolutions(17), CoreMatchers.equalTo(1));
	}

	@Test
	public void get4XSquaredPlusYSquaredSolutions_29() {
		assertThat(AtkinSieve.get4XSquaredPlusYSquaredSolutions(29), CoreMatchers.equalTo(1));
	}

	@Test
	public void get4XSquaredPlusYSquaredSolutions_37() {
		assertThat(AtkinSieve.get4XSquaredPlusYSquaredSolutions(37), CoreMatchers.equalTo(1));
	}

	@Test
	public void get4XSquaredPlusYSquaredSolutions_41() {
		assertThat(AtkinSieve.get4XSquaredPlusYSquaredSolutions(41), CoreMatchers.equalTo(1));
	}

	@Test
	public void get4XSquaredPlusYSquaredSolutions_49() {
		assertThat(AtkinSieve.get4XSquaredPlusYSquaredSolutions(49), CoreMatchers.equalTo(0));
	}

	@Test
	public void get4XSquaredPlusYSquaredSolutions_53() {
		assertThat(AtkinSieve.get4XSquaredPlusYSquaredSolutions(53), CoreMatchers.equalTo(1));
	}

	@Test
	public void get3XSquaredPluusYSquaredSolutions_7() {
		assertThat(AtkinSieve.get3XSquaredPlusYSquaredSolutions(7), CoreMatchers.equalTo(1));
	}

	@Test
	public void get3XSquaredMinusYSquaredSolutions_11() {
		assertThat(AtkinSieve.get3XSquaredMinusYSquaredSolutions(11), CoreMatchers.equalTo(1));
	}


	@Test
	public void test00() {
		assertThat(AtkinSieve.getPrimes(0).size(), equalTo(0));
	}

	@Test
	public void test01() {
		assertThat(AtkinSieve.getPrimes(1).size(), equalTo(0));
	}

	@Test
	public void test02() {
		assertThat(AtkinSieve.getPrimes(2), equalTo(Arrays.asList(2)));
	}

	@Test
	public void test03() {
		assertThat(AtkinSieve.getPrimes(3), equalTo(Arrays.asList(2, 3)));
	}

	@Test
	public void test10() {
		assertThat(AtkinSieve.getPrimes(10), equalTo(Arrays.asList(2, 3, 5, 7)));
	}

	@Test
	public void test11() {
		assertThat(AtkinSieve.getPrimes(11), equalTo(Arrays.asList(2, 3, 5, 7, 11)));
	}

	@Test
	public void test21() {
		assertThat(AtkinSieve.getPrimes(21), equalTo(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19)));
	}

	@Test
	public void testGetFlipCount01() {
		assertThat(AtkinSieve.getFlipCount(1), equalTo(0));
	}

	@Test
	public void testGetFlipCount07() {
		assertThat(AtkinSieve.getFlipCount(7), equalTo(1));
	}

	@Test
	public void testGetFlipCount08() {
		assertThat(AtkinSieve.getFlipCount(8), equalTo(0));
	}

	@Test
	public void testGetFlipCount11() {
		assertThat(AtkinSieve.getFlipCount(11), equalTo(1));
	}

	@Test
	public void testGetFlipCount203897() {
		assertThat(AtkinSieve.getFlipCount(203897), equalTo(1));
	}

	@Test
	public void testGetFlipCount203909() {
		assertThat(AtkinSieve.getFlipCount(203909), equalTo(1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMaxIntIllegalArgument() {
		AtkinSieve.getPrimes(Integer.MAX_VALUE);
	}
	
	@Test
	public void testBad() {
		final List<Integer> primes = AtkinSieve.getPrimes(203909);
		final List<Integer> goodPrimes = ErastosthenesSieve.getPrimes(203909);
		for (int i=0; i< goodPrimes.size(); i++) {
			assertThat(i+ "th prime", primes.get(i), equalTo(goodPrimes.get(i)));
		}
		assertThat(primes.get(primes.size() -1), equalTo(203909));
		assertThat(goodPrimes.size(), equalTo(primes.size()));
	}
	
	private void testNthPrime(int primeNumber, int numberOfPrimesToNumber) {
		final List<Integer> primes = AtkinSieve.getPrimes(primeNumber);
		assertThat(primes.get(primes.size() -1), equalTo(primeNumber));
		assertThat(primes.size(), equalTo(numberOfPrimesToNumber));
	}

	@Test
	public void test10Exp04() {
		testNthPrime(104729, 10*1000);
	}

	@Test
	public void test10Exp05() {
		testNthPrime(1299709, 100*1000);
	}

	//@Test
	public void test10Exp06() {
		testNthPrime(15485863, 1000*1000);
	}

	//still takes too long to run.
	//@Test
	public void test10Exp07() {
		testNthPrime(179424673, 10*1000*1000);
	}

	//@Test
	public void test10Exp08() {
		testNthPrime(2038074743, 100*1000*1000);
	}

	//@Test
	//still not ready to run this yet
	public void testMaxiumIntValue() {
		//2,147,483,646
		assertThat(AtkinSieve.getPrimes(Integer.MAX_VALUE - 1).size(), equalTo(105097564));
	}

}
