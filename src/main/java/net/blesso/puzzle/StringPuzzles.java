package net.blesso.puzzle;

/**
 *  Questions from "Cracking the Coding Interview" by Gayle Laakmann McDowell.
 * @author francis
 */
public class StringPuzzles {
	
	/**
	 * Chapter 1, question 1.5. Do two strings differ by a single character insertion, deletion, or replacement.
	 * @param s1
	 * @param s2
	 * @return 
	 */
	public static boolean isOneAway(String s1, String s2) {
		//if the lengths differ by two or more, then there must be at least two insertions/deletions.
		if (Math.abs(s1.length() - s2.length()) > 1) {
			return false;
		}
		
		int lastPos = Math.min(s1.length(), s2.length());
		for (int pos = 0; pos < lastPos; pos++) {
			if (s1.charAt(pos) != s2.charAt(pos)) {
				// this point, the rest of the strings need to be equal
				return s1.substring(pos).equals(s2.substring(pos + 1)) || //checks for deletion of s2 at pos.
						s1.substring(pos + 1).equals(s2.substring(pos)) ||  //checks for deletion of s1 at pos.
						s1.substring(pos + 1).equals(s2.substring(pos + 1)); //check for replacement at pos.
			}
		}
		return true;
	}

}
