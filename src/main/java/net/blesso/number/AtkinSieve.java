package net.blesso.number;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Sieve of Atkin implementation.
 * 
 * @author fblesso
 */
public class AtkinSieve {
	 

	/**
	 * Computes all the primes numbers up to <code>max</code> using the Sieve of Atkin.
	 * @param max the limit of the primes to determine
	 * @return list of all prime numbers less than or equal to <code>max</code>
	 */
	public static List<Integer> getPrimes(int max) {
		//handle some simple cases here so we can later assume max >=6
		if (max <= 1L) {
			return Collections.emptyList();
		} else if (max == 2) {
			return Collections.singletonList(2);
		} else if (max <= 4) {
			return Collections.unmodifiableList(Arrays.asList(2, 3));
		} else if (max == 5) {
			return Collections.unmodifiableList(Arrays.asList(2, 3, 5));
		} else if (max == Integer.MAX_VALUE) {
			throw new IllegalArgumentException("can only calculate primes up to Integer#MAX_VALUE -1");
		}
		
		final List<Integer> primes = new Vector<>(Arrays.asList(2, 3, 5));
		
		final BitSet composites = new BitSet(max);
		//initially mark everything as composite.
		composites.flip(0, max + 1);

		int currVal = 0;
		while(currVal <= max) {
			final int flipCount = getFlipCount(currVal);
			if (flipCount % 2 == 1) {
				composites.flip(currVal);
			}
					
			currVal ++;
		}
		
		System.out.println("finished initial marking");
		int currMultiplier = 1;
		long currPrime;
		final double sqrtOfMax = Math.sqrt(max);
		while ((currPrime = composites.nextClearBit(0)) <= max) {
			final long primeSquared = currPrime * currPrime;
			primes.add((int)currPrime);
			long notPrime = currPrime;
			currMultiplier = 1;
			while ((currPrime <= sqrtOfMax) && (notPrime  = primeSquared * currMultiplier) <= max){
				composites.set((int) notPrime);
				//TODO should the currMulitplier only do primes?
				currMultiplier++;
			}
			composites.set((int)currPrime);
		}

		return primes;
	}
	
	static int getFlipCount(int n) {
		final int remainder = n % 60;
		switch (remainder) {
		case 1:
		case 13:
		case 17:
		case 29:
		case 37:
		case 41:
		case 49:
		case 53:
			return get4XSquaredPlusYSquaredSolutions(n);
		case 7:
		case 19:
		case 31:
		case 43:
			return get3XSquaredPlusYSquaredSolutions(n);
		case 11:
		case 23:
		case 47:
		case 59:
			return get3XSquaredMinusYSquaredSolutions(n);
			
		default:
			//must be a nonPrime
			return 0;
		}
	}
	
	/**
	 * Determines how many x,y solutions there are to the equation
	 * <pre> 4*x*x + y*y = n <pre> where x and y are positive integers.
	 * @param n the number to test 
	 * @return number of integer solutions
	 */
	static int get4XSquaredPlusYSquaredSolutions(int n) {
		//TODO is there a faster way to do this? 
		int solutionCount = 0;
		double limit = Math.sqrt(n)/2.0;
		for (int x = 1; x <= limit; x++) {
			double yDouble = Math.sqrt(n - 4*x*x);
			if (yDouble < 1.0) {
				continue;
			}
			int y = (int) yDouble;
			if (((double) y) == yDouble) {
				solutionCount++;
			}
		}
		return solutionCount;
	}
	
	/**
	 * Determines how many x,y solutions there are to the equation
	 * <pre> 3*x*x + y*y = n <pre> where x and y are positive integers.
	 * @param n the number to test 
	 * @return number of integer solutions
	 */
	static int get3XSquaredPlusYSquaredSolutions(int n) {
		int solutionCount = 0;
		double limit = Math.sqrt(n/3.0);
		for (int x = 1; x <= limit; x++) {
			double yDouble = Math.sqrt(n - 3*x*x);
			if (yDouble < 1.0) {
				continue;
			}
			int y = (int) yDouble;
			if (((double) y) == yDouble) {
				solutionCount++;
			}
		}
		return solutionCount;
	}
	
	/**
	 * Determines how many x,y solutions there are to the equation
	 * <pre> 3*x*x - y*y = n <pre> where x and y are positive integers.
	 * @param n the number to test 
	 * @return number of integer solutions
	 */
	static int get3XSquaredMinusYSquaredSolutions(int n) {
		
		int solutionCount = 0;
		double limit = Math.sqrt(((double)n)/2.0);
		for (int y = 1; y <= limit; y++) {
			double xDouble = Math.sqrt((y*y + n)/3);
			if (xDouble <= y ) {
				continue;
			}
			int x = (int) xDouble;
			if (((double) x) == xDouble) {
				solutionCount++;
			}
		}
		return solutionCount;
	}

}
