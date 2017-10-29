package net.blesso.puzzle;

public class NQueens {
	
	/**
	 * @param boardSize
	 * @return the number of possible ways to arrange N queens on a NxN board where N=<code>boardSize</code>
	 */
	int numPos(int boardSize) {
		int[] ranks = new int[boardSize];
		return numPos(ranks, 0, boardSize);
	}
	
	/**
	 * Determines the number of possible ways to place queens from one column onward,
	 * given that there are already queens placed in the prior columns. It does this by iterating through
	 * the valid ranks that can accept a queen in this column and recursively determine the number of combinations 
	 * with a queen at that place. 
	 * @param ranks current positions of queens in columns less than <code>column</code>
	 * @param column which column we are working on.
	 * @param boardSize
	 * @return number of ways to 
	 */
	int numPos(int[] ranks, int column, int boardSize){
		int sum = 0;
		for (int rank = 0; rank < boardSize; rank++) {
			if(isValidRank(column, rank, ranks)) {
				if (column == boardSize -1) {
					return 1;
				}
				ranks[column] = rank;
				sum += numPos(ranks, column + 1, boardSize);
			}
		}
		return sum;
	}
	
	/**
	 * Determines whether you can place a queen in a column and rank, given queens are
	 * already placed in the previous columns.
	 * @param column the test column
	 * @param testRank the test rank
	 * @param currRanks ranks of queens in previous columns
	 * @return whether the queen can be places without threatening any already placed queens.
	 */
	private boolean isValidRank(int column, int testRank, int[] currRanks) {
		for (int prevCol = 0; prevCol < column; prevCol++) {
			int prevColRank = currRanks[prevCol];
			int topDiagRank = testRank - (column - prevCol);
			int bottomDiagRank = testRank + (column - prevCol);
			if (prevColRank == testRank) {  //same rank
				return false;
			} else if (prevColRank == topDiagRank) {
				return false; //diagonal
			} else if (prevColRank == bottomDiagRank) {
				return false; // other diagonal
			}
		}
		return true;
	}

}
