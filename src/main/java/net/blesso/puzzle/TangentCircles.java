package net.blesso.puzzle;

import java.util.List;

import net.blesso.number.ErastosthenesSieve;

public class TangentCircles {
	
	private List<Long> primes;

	public static double determineInsideCircleRadius(long radiusA, long radiusB){
		final long ab = radiusA * radiusB;
		final double sqrtAB = Math.sqrt(ab);
		
		final double radiusC = ab /(radiusA + radiusB + 2*sqrtAB);
		return radiusC;
	}
	public static void main(String[] args) {
		ErastosthenesSieve.getPrimes(100000);
		long sumOfAll = 0;
		for (long radiusB = 1; radiusB <=0; radiusB++ ) {

			for (int radiusA = 1; radiusA <=radiusB; radiusA++ ) {
				final double radiusCDouble = determineInsideCircleRadius(radiusA, radiusB);
				long radiusC = Math.round(radiusCDouble);
				if (radiusCDouble == ((double)radiusC)) {
					System.out.print("it is a match! " + radiusA + " " + radiusB + " " + radiusCDouble);
					final long sum = radiusA + radiusB + radiusC;
					sumOfAll += sum;
					System.out.println(" sum: " + sum);
				}
			}
		}

		System.out.println(" sumOfAll: " + sumOfAll);
	}
	
	private void determineS(long limit) {
		
	}
}
