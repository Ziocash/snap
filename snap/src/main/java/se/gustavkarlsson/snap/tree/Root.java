package se.gustavkarlsson.snap.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class Root implements Parent {
	
	private final Set<LeafNode> decendants = new HashSet<LeafNode>();

	private final ArrayList<LeafNode> children = new ArrayList<LeafNode>();

	@Override
	public LeafNode[] listChildren() {
		LeafNode[] childArray = new LeafNode[children.size()];
		return children.toArray(childArray);
	}

	@Override
	public void addChild(LeafNode child) {
		if (decendants.contains(child)) {
			throw new DuplicateTreeNodeException();
		}
		child.setParent(this);
		children.add(child);
		decendants.add(child);
	}

	@Override
	public void removeChild(LeafNode child) {
		child.remove();
		children.remove(child);
		decendants.remove(child);
	}

	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	
	Set<LeafNode> getDecendants() {
		return decendants;
	}
}
