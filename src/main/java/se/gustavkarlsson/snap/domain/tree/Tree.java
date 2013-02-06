package se.gustavkarlsson.snap.domain.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tree<T> {

	private Set<Tree<T>> treesSet = new HashSet<Tree<T>>();
	private Tree<T> parent = null;
	private List<Tree<T>> children = new ArrayList<Tree<T>>();
	private Node<T> node = null;

	Set<Tree<T>> getTreesSet() {
		return treesSet;
	}

	List<Tree<T>> getChildren() {
		return children;
	}

	void resetTreesSet(Set<Tree<T>> newTreeNodes) {
		// Remove from old treeNodes (others might still use it)
		treesSet.remove(this);
		// Set new treeNodes
		treesSet = newTreeNodes;
		// Add this to new TreeNodes
		treesSet.add(this);

		// Recursively do same thing on all children
		for (Tree<T> child : children) {
			child.resetTreesSet(treesSet);
		}
	}

	private boolean isChildrenAllowed() {
		return node.isChildrenAllowed();
	}

	public Tree(Node<T> node) {
		if (node == null) {
			throw new IllegalArgumentException("node can't be null");
		}
		this.node = node;
		treesSet.add(this);
	}

	public Node<T> getNode() {
		return node;
	}

	public T getNodeValue() {
		return node.getValue();
	}

	public void setNodeValue(T newValue) {
		node.setValue(newValue);
	}

	public Tree<T> getParent() {
		return parent;
	}

	public boolean setParent(Tree<T> newParent) {
		if ((newParent != null) && !newParent.isChildrenAllowed()) {
			return false;
		}

		// Remove from current parents children
		if (parent != null) {
			parent.getChildren().remove(this);
		}

		// Set new parent
		parent = newParent;
		if (parent != null) {
			// Add this to new parents children
			parent.getChildren().add(this);
		}

		// Reset the treesSet for all children
		if (parent != null) {
			resetTreesSet(newParent.getTreesSet());
		} else {
			resetTreesSet(new HashSet<Tree<T>>());
		}

		return true;
	}

	public List<Tree<T>> listChildren() {
		return Collections.unmodifiableList(children);
	}

	public int getLevel() {
		if (parent == null) {
			return 1;
		}

		return 1 + parent.getLevel();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public String toString() {
		return "Tree: " + getNodeValue();
	}

}
