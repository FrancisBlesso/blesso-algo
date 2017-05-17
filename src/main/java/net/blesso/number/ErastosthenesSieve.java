package net.blesso.number;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Contains various ways to generate prime numbers.
 * 
 * @author fblesso
 */
public class ErastosthenesSieve {
	 

	/**
	 * Computes all the primes numbers up to <code>max</code> using the Sieve of Erastosthenes.
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
		
		final BitSet testedNumbers = new BitSet(max);
		//mark numbers zero through 5 tested. (the BitSet#set method doesn't set the end index)
		testedNumbers.set(0,6);  

		//remove all factors of 2, 3, and 5.
		int currVal = 6;
		while(currVal <= max) {
			final int remainder = currVal % 30;
			switch (remainder) {
			case 1:
			case 7:
			case 11:
			case 13:
			case 17:
			case 19:
			case 23:
			case 29:
				//cannot be a multiple of 2, 3, or 5
				break;
			default:
				//must be a multiple of 2, 3, or 5
				testedNumbers.set(currVal);
				break;
			}
					
			currVal ++;
		}
		
		//System.out.println("discovering all primes up to " + max);
		int currMultiplier = 1;
		final BitSet numbersTestedThisIteration = new BitSet(max);
		int currPrime;
		final double sqrtMax = Math.sqrt(max);
		while ((currPrime = testedNumbers.nextClearBit(0)) < sqrtMax) {
		
			numbersTestedThisIteration.clear();
			numbersTestedThisIteration.set(currPrime);
			primes.add(currPrime);
			//System.out.print("prime=" + currPrime + " numbers remaining to test=" + (max - testedNumbers.cardinality()) + "...");
			int notPrime = currPrime;
			currMultiplier = currPrime;
			while ((notPrime  = currPrime * currMultiplier) <= max){
				if (notPrime <= 0){ //handles integer overflow when the above product is bigger than Integer.MAX_VALUE
					break;
				}
				currMultiplier = testedNumbers.nextClearBit(currMultiplier + 1);
				//System.out.println("notPrime=" + notPrime);
				numbersTestedThisIteration.set(notPrime);
			}
			testedNumbers.or(numbersTestedThisIteration);
			//System.out.println("prime=" + currPrime + " removed " + numbersTestedThisIteration.cardinality() + " non-primes");
		}
		int remainingPrime = testedNumbers.nextClearBit(0);
		while(remainingPrime > 0 && remainingPrime <= max) {
			primes.add(remainingPrime);
			remainingPrime = testedNumbers.nextClearBit(remainingPrime + 1);
		}

		return primes;
	}

}
