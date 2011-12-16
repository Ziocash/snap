package se.gustavkarlsson.snap.tree;

import java.util.ArrayList;

public abstract class Node extends LeafNode implements Parent {

	public Node(Root root) {
		super(root);
	}

	private ArrayList<LeafNode> children = new ArrayList<LeafNode>();

	@Override
	public LeafNode[] listChildren() {
		LeafNode[] childArray = new LeafNode[children.size()];
		return children.toArray(childArray);
	}

	@Override
	public void addChild(LeafNode child) {
		if (root.getDecendants().contains(child)) {
			throw new DuplicateTreeNodeException();
		}
		child.setParent(this);
		children.add(child);
		root.getDecendants().add(child);
	}

	@Override
	public void removeChild(LeafNode child) {
		child.remove();
		children.remove(child);
	}

	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	@Override
	void remove() {
		super.remove();

		while (!children.isEmpty()) {
			int index = children.size() - 1;
			LeafNode child = children.get(index);

			if (child instanceof Node) {
				((Node) child).remove();
			} else {
				child.remove();
			}

			children.remove(index);
		}
		children.trimToSize();
	}
}
