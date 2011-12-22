package se.gustavkarlsson.snap.tree;

import java.io.Serializable;

public abstract class LeafNode implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Parent parent = null;

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
}
