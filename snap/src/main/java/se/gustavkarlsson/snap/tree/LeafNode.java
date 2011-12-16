package se.gustavkarlsson.snap.tree;

import java.io.Serializable;

import se.gustavkarlsson.snap.resources.Strings;

public abstract class LeafNode implements Serializable {
	
	protected final Root root;

	private Parent parent = null;
	
	public LeafNode(Root root) {
		if (root == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL + ": root");
		}
		this.root = root;
	}
	
	public Parent getParent() {
		return parent;
	}
	
	void setParent(Parent parent) {
		this.parent = parent; 
	}
	
	void remove() {
		if (parent != null) {
			parent = null;
		}
	}
	
	Root getRoot() {
		return root;
	}
}
