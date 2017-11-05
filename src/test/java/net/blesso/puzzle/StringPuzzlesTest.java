/**
 * 
 */
package net.blesso.puzzle;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

/**
 * @author francis
 *
 */
public class StringPuzzlesTest {

	/**
	 * Test method for {@link net.blesso.puzzle.StringPuzzles#isOneAway(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testIsOneAway() {
		assertThat(StringPuzzles.isOneAway("", ""), CoreMatchers.equalTo(true));
		assertThat(StringPuzzles.isOneAway("hi", "hi"), CoreMatchers.equalTo(true));
		assertThat(StringPuzzles.isOneAway("ahi", "hi"), CoreMatchers.equalTo(true));
		assertThat(StringPuzzles.isOneAway("hi", "bhi"), CoreMatchers.equalTo(true));
		assertThat(StringPuzzles.isOneAway("hib", "hic"), CoreMatchers.equalTo(true));
		assertThat(StringPuzzles.isOneAway("hi", "hic"), CoreMatchers.equalTo(true));
		assertThat(StringPuzzles.isOneAway("hic", "hi"), CoreMatchers.equalTo(true));
		assertThat(StringPuzzles.isOneAway("hiab", "hic"), CoreMatchers.equalTo(false));
		assertThat(StringPuzzles.isOneAway("hi", "hihi"), CoreMatchers.equalTo(false));
		
	}

}
