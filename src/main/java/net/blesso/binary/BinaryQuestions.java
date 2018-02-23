package net.blesso.binary;

public class BinaryQuestions {
	int swapBitPairs (int a) {
		int swapped = 0;
		for (int pair = 0; pair < Integer.BYTES/2; pair++) {
			int right = 2 * pair;
			int left = right + 1;
			int rightBit = a & (1 << right) >>> right;
			int leftBit = (a & ( 1 << left)) >>> left;
			swapped = swapped | (rightBit << left) | (leftBit << right);
		}
		return swapped;
	}

}
