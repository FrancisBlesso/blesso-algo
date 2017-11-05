package net.blesso.puzzle;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class DependencyBuilderTest {

	@Test
	public void testGetBuildOrder1() {
		List<String> buildOrder = DependencyBuilder.getBuildOrder(Arrays.asList("z", "d", "a", "b", "c"), 
				new String[][] { {"a", "b"},
								 {"b", "c"},
								 {"c", "d"},
								 {"d", "z"},
								 });
		assertThat(buildOrder, CoreMatchers.equalTo(Arrays.asList("a", "b", "c", "d", "z")));
		
	}

	@Test
	public void testGetBuildOrder2() {
		List<String> buildOrder = DependencyBuilder.getBuildOrder(Arrays.asList("z", "d", "a", "b", "c"), 
				new String[][] { {"a", "b"},
								 {"a", "c"},
								 {"a", "d"},
								 {"z", "a"},
								 });
		assertThat(buildOrder.get(0), equalTo("z"));
		assertThat(buildOrder.get(1), CoreMatchers.equalTo("a"));
		
	}

	@Test
	public void testGetBuildOrder3() {
		List<String> buildOrder = DependencyBuilder.getBuildOrder(Arrays.asList("z", "d", "a", "b", "c"), 
				new String[][] { {"a", "z"},
			 					 {"a", "b"},
			 					 {"a", "c"},
								 {"a", "d"},
								 {"b", "c"},
								 {"b", "d"},
								 {"b", "z"},
								 {"c", "d"},
								 {"c", "z"},
								 {"d", "z"},
								 });
		assertThat(buildOrder, equalTo(Arrays.asList("a", "b", "c", "d", "z")));
		
	}

	@Test
	public void testGetBuildCycle() {
		List<String> buildOrder = DependencyBuilder.getBuildOrder(Arrays.asList("z", "a"), 
				new String[][] { {"a", "z"},
								 {"z", "a"},
								 });
		assertThat(buildOrder.size(), equalTo(0));
		
	}

	@Test
	public void testGetBuildOrderDfs1() {
		List<String> buildOrder = DependencyBuilder.getBuildOrderDfs(Arrays.asList("z", "d", "a", "b", "c"), 
				new String[][] { {"a", "b"},
								 {"b", "c"},
								 {"c", "d"},
								 {"d", "z"},
								 });
		assertThat(buildOrder, CoreMatchers.equalTo(Arrays.asList("a", "b", "c", "d", "z")));
		
	}

	@Test
	public void testGetBuildOrderDfs2() {
		List<String> buildOrder = DependencyBuilder.getBuildOrderDfs(Arrays.asList("z", "d", "a", "b", "c"), 
				new String[][] { {"a", "b"},
								 {"a", "c"},
								 {"a", "d"},
								 {"z", "a"},
								 });
		assertThat(buildOrder.get(0), equalTo("z"));
		assertThat(buildOrder.get(1), CoreMatchers.equalTo("a"));
		
	}

	@Test
	public void testGetBuildOrderDfs3() {
		List<String> buildOrder = DependencyBuilder.getBuildOrderDfs(Arrays.asList("z", "d", "a", "b", "c"), 
				new String[][] { {"a", "z"},
			 					 {"a", "b"},
			 					 {"a", "c"},
								 {"a", "d"},
								 {"b", "c"},
								 {"b", "d"},
								 {"b", "z"},
								 {"c", "d"},
								 {"c", "z"},
								 {"d", "z"},
								 });
		assertThat(buildOrder, equalTo(Arrays.asList("a", "b", "c", "d", "z")));
		
	}

	@Test
	public void testGetBuildOrderDfsIndependentGrpahs() {
		List<String> buildOrder = DependencyBuilder.getBuildOrderDfs(Arrays.asList("d", "a", "b", "c", "z"), 
				new String[][] { {"a", "b"},
			 					 {"a", "c"},
								 {"z", "d"},
								 });
		assertThat(buildOrder, equalTo(Arrays.asList("z", "d", "a", "b", "c")));
		
	}

	@Test(expected = RuntimeException.class)
	public void testGetBuildDfsCycle() {
		List<String> buildOrder = DependencyBuilder.getBuildOrderDfs(Arrays.asList("z", "a"), 
				new String[][] { {"a", "z"},
								 {"z", "a"},
								 });
		assertThat(buildOrder.size(), equalTo(0));
		
	}

}
