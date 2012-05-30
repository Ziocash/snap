package se.gustavkarlsson.snap.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import se.gustavkarlsson.snap.resources.Strings;

public class FolderNode extends Node {

	private final Set<Node> children = new HashSet<Node>();

	public FolderNode(String name) {
		super(name);
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

	public Set<Node> getChildren() {
		return Collections.unmodifiableSet(children);
	}

	public boolean addChild(Node child) {
		if (child == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL
					+ ": child");
		}
		if (isAncestor(child)) {
			throw new IllegalStateException(
					"Cannot add child node as it is an ancestor to this node.");
		}
		if (children.add(child)) {
			child.setParent(this);
			return true;
		}
		return false;
	}

	public boolean removeChild(Node child) {
		if (children.remove(child)) {
			child.setParent(null);
			return true;
		}
		return false;
	}

	public void clearChildren() {
		for (Node child : children) {
			child.setParent(null);
		}
		children.clear();
	}
}
