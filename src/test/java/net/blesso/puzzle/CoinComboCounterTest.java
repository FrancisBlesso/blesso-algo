package net.blesso.puzzle;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class CoinComboCounterTest {
	
	@Test
	public void test5() {
		assertThat(new CoinComboCounter().getChangeCombos(5), CoreMatchers.equalTo(2));
	}
	
	@Test
	public void test10() {
		assertThat(new CoinComboCounter().getChangeCombos(10), CoreMatchers.equalTo(4));
	}
	
	@Test
	public void test15() {
		assertThat(new CoinComboCounter().getChangeCombos(15), CoreMatchers.equalTo(6));
	}
	
	@Test
	public void test20() {
		assertThat(new CoinComboCounter().getChangeCombos(20), CoreMatchers.equalTo(9));
	}
	
	@Test
	public void test25() {
		assertThat(new CoinComboCounter().getChangeCombos(25), CoreMatchers.equalTo(13));
	}
	
	@Test
	public void test30() {
		assertThat(new CoinComboCounter().getChangeCombos(30), CoreMatchers.equalTo(18));
	}
	
	@Test
	public void test100() {
		assertThat(new CoinComboCounter().getChangeCombos(100), CoreMatchers.equalTo(242));
	}

}
