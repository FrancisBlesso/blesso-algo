/**
 * 
 */
package net.blesso.datastruct;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test case for {@link TreeNode}.
 *
 * @author fblesso
 */
public class TreeNodeTest {

	/**
	 * Test method for {@link net.blesso.datastruct.TreeNode#TreeNode(java.lang.Object, net.blesso.datastruct.TreeNode)}.
	 */
	@Test
	public void testTreeNodeTTreeNodeOfT() {
		final TreeNode<Integer> parent1 = new TreeNode<>(1);
		final TreeNode<Integer> child = new TreeNode<>(99, parent1);
		assertThat(child.getParent(), sameInstance(parent1));
		assertThat(parent1.getChildren().get(0), sameInstance(child));
	}

	/**
	 * Test method for {@link net.blesso.datastruct.TreeNode#setParent(net.blesso.datastruct.TreeNode)}.
	 */
	@Test
	public void testSetParent() {
		final TreeNode<Integer> parent1 = new TreeNode<>(1);
		final TreeNode<Integer> parent2 = new TreeNode<>(2);
		final TreeNode<Integer> child = new TreeNode<>(99);
		child.setParent(parent1);
		assertThat(parent1.getChildren().get(0), sameInstance(child));
		assertThat(child.getParent(), sameInstance(parent1));
		child.setParent(parent2);

		assertThat(parent1.getChildren().size(), equalTo(0));
		assertThat(parent2.getChildren().get(0), sameInstance(child));
		assertThat(child.getParent(), sameInstance(parent2));
		
		child.setParent(null);
	}
	

	@Test
	public void testSetParentNullAdjustsFirstChildOnPriorParent() {
		final TreeNode<Integer> parent = new TreeNode<>(99);
		final TreeNode<Integer> child1 = new TreeNode<>(1);
		final TreeNode<Integer> child2 = new TreeNode<>(2);
		child1.setParent(parent);
		child2.setParent(parent);
		child1.setParent(null);
		assertThat(parent.firstChild(), sameInstance(child2));
	}

	/**
	 * Test method for {@link net.blesso.datastruct.TreeNode#firstChild()}.
	 */
	@Test
	public void testFirstChild() {
		final TreeNode<Integer> parent1 = new TreeNode<>(1);
		assertThat(parent1.firstChild(), nullValue());
		final TreeNode<Integer> child = new TreeNode<>(99);
		child.setParent(parent1);
		assertThat(parent1.firstChild(), sameInstance(child));
		child.setParent(null);
		assertThat(parent1.firstChild(), nullValue());
	}
	
	public void findChild_notAChildIsNull(){
		final TreeNode<Integer> parent1 = new TreeNode<>(1);
		assertThat(parent1.findChild(99), nullValue());
	}
	/**
	 * Test method for {@link net.blesso.datastruct.TreeNode#firstChild()}.
	 */
	@Test
	public void testFindChild() {
		final TreeNode<Integer> parent1 = new TreeNode<>(1);
		assertThat(parent1.firstChild(), nullValue());
		final TreeNode<Integer> child = new TreeNode<>(99);
		child.setParent(parent1);
		assertThat(parent1.findChild(99), sameInstance(child));
	}
}
