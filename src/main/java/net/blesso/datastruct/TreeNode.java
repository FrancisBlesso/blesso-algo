/**
 * 
 */
package net.blesso.datastruct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a node within a tree. It has a parent and children.
 * @param <T> The type of object in the tree.
 *
 * @author fblesso
 */
public class TreeNode<T> {

	private final T item;
	private final List<TreeNode<T>> children = new ArrayList<>();
	private TreeNode<T> parent;
	
	public TreeNode(T item) {
		this.item = item;
	}
	
	public TreeNode(T item, TreeNode<T> parent) {
		this(item);
		setParent(parent);
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	/**
	 * Sets the node's parent, adding this node to the new parent's children and removing 
	 * this node from the prior parent's children.
	 * @param newParent the node's new parent node
	 */
	public void setParent(TreeNode<T> newParent) {
		if (this.parent == newParent) {
			return;
		}
		if (this.parent != null){
			this.parent.children.remove(this);
		}
		this.parent = newParent;
		if(newParent != null) {
			newParent.children.add(this);
		}
	}

	public T getItem() {
		return item;
	}

	public List<TreeNode<T>> getChildren() {
		return Collections.unmodifiableList(children);
	}

	/**
	 * @return the first child, or null if there are no children
	 */
	public TreeNode<T> firstChild() {
		return children.size() == 0 ? null : children.get(0);
	}
	
}
