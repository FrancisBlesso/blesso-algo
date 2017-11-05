/**
 * 
 */
package net.blesso.puzzle;

import java.util.HashMap;
import java.util.Map;

/**
 * Determines the number of ways to make change for a certain amount (in cents).
 *
 * @author francis
 */
public class CoinComboCounter {
	/** Remembers previously calculated combinations. */
	final Map<Integer, Integer> combosInDimesNickelsAndPennies = new HashMap<>();
	
	public CoinComboCounter() {
	}
	
	public int getChangeCombos(int amt){
		assert(amt >= 0);
		if (amt <= 0) {
			return 0;
		} else if (amt < 5) {
			return 1;
		} else if (amt < 10) {
			return 2;
		}
		//TODO generalized for an arbitrary number of denominations and denomination values
		int sum = 0;
		int totalQuarters = amt/25;
		for (int quarterCount = 0; quarterCount <= totalQuarters; quarterCount++) {
			int quartersValue = 25 * quarterCount;
			sum += getCombosDimesNickelsPennies(amt - quartersValue);
		}
		return sum;
	}

	private int getCombosDimesNickelsPennies(int amt) {
		if (combosInDimesNickelsAndPennies.containsKey(amt)) {
			return combosInDimesNickelsAndPennies.get(amt);
		}
		int sum = 0;
		int numDimes = amt/10;
		for (int dimeCount = 0; dimeCount <= numDimes; dimeCount++) {
			int dimeValue = dimeCount * 10;
			sum += getComboNickelsPennies(amt - dimeValue);
		}
		
		//memoize the number of combos for the amount
		combosInDimesNickelsAndPennies.put(amt, sum); 
		return sum;
	}

	private int getComboNickelsPennies(int amt) {
		return amt/5 + 1;
	}
}
