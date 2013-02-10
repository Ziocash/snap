package se.gustavkarlsson.snap.domain.tree;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Tree<T> {

	private Set<Tree<T>> treesSet = new HashSet<Tree<T>>();
	protected Tree<T> parent = null;
	protected Set<Tree<T>> children = new HashSet<Tree<T>>();
	protected T value = null;

	private Set<Tree<T>> getTreesSet() {
		return treesSet;
	}

	private Set<Tree<T>> getChildren() {
		return children;
	}

	private void resetTreesSet(Set<Tree<T>> newTreeNodes) {
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

	protected abstract boolean isChildrenAllowed();

	public Tree(T value) {
		this.value = value;
		treesSet.add(this);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Tree<T> getParent() {
		return parent;
	}

	public boolean setParent(Tree<T> newParent) {
		if (this == newParent) {
			return false;
		}

		if (parent == newParent) {
			return true;
		}

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

	public Set<Tree<T>> children() {
		return Collections.unmodifiableSet(children);
	}

	public int getChildCount() {
		return children.size();
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public int getLevel() {
		if (parent == null) {
			return 1;
		}

		return 1 + parent.getLevel();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Tree other = (Tree) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
