package se.gustavkarlsson.snap.tree;

import java.util.HashSet;
import java.util.Set;

import se.gustavkarlsson.snap.resources.Strings;

public abstract class Node extends LeafNode implements Parent {

	private static final long serialVersionUID = 1L;
	
	private final Set<LeafNode> children = new HashSet<LeafNode>();

	@Override
	public LeafNode[] listChildren() {
		LeafNode[] childArray = new LeafNode[children.size()];
		return children.toArray(childArray);
	}

	@Override
	public boolean addChild(LeafNode child) {
		if (child == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL + ": child");
		}
		boolean added = children.add(child);
		if (added) {
			child.setParent(this);
		}
		return added;
	}

	@Override
	public boolean removeChild(LeafNode child) {
		boolean removed = children.remove(child);
		if (removed) {
			child.remove();
		}
		return removed;
	}

	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	@Override
	void remove() {
		super.remove();
		for (LeafNode child : children) {
			if (child instanceof Node) {
				((Node) child).remove();
			} else {
				child.remove();
			}
		}
		children.clear();
	}
}
