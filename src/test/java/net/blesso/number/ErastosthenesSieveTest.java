/**
 * 
 */
package net.blesso.number;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Test case for {@link ErastosthenesSieve}. 
 * 
 * large prime numbers are from http://www.bigprimes.net/archive/prime/
 * @author fblesso
 */
public class ErastosthenesSieveTest {

	@Test
	public void test00() {
		assertThat(ErastosthenesSieve.getPrimes(0).size(), equalTo(0));
	}

	@Test
	public void test01() {
		assertThat(ErastosthenesSieve.getPrimes(1).size(), equalTo(0));
	}

	@Test
	public void test02() {
		assertThat(ErastosthenesSieve.getPrimes(2), equalTo(Arrays.asList(2)));
	}

	@Test
	public void test03() {
		assertThat(ErastosthenesSieve.getPrimes(3), equalTo(Arrays.asList(2, 3)));
	}

	@Test
	public void test10() {
		assertThat(ErastosthenesSieve.getPrimes(10), equalTo(Arrays.asList(2, 3, 5, 7)));
	}

	@Test
	public void test11() {
		assertThat(ErastosthenesSieve.getPrimes(11), equalTo(Arrays.asList(2, 3, 5, 7, 11)));
	}

	@Test
	public void test21() {
		assertThat(ErastosthenesSieve.getPrimes(21), equalTo(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMaxIntIllegalArgument() {
		ErastosthenesSieve.getPrimes(Integer.MAX_VALUE);
	}
	
	private void testNthPrime(int primeNumber, int numberOfPrimesToNumber) {
		final List<Integer> primes = ErastosthenesSieve.getPrimes(primeNumber);
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

	@Test
	public void test10Exp06() {
		testNthPrime(15485863, 1000*1000);
	}

	//@Test
	public void test10Exp07() {
		testNthPrime(179424673, 10*1000*1000);
	}

	//@Test
	public void test10Exp08() {
		testNthPrime(2038074743, 100*1000*1000);
	}

	//@Test
	//takes about 10 min to run
	public void testMaxiumIntValue() {
		//2,147,483,646
		assertThat(ErastosthenesSieve.getPrimes(Integer.MAX_VALUE - 1).size(), equalTo(105097564));
	}

}
