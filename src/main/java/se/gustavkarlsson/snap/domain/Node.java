package se.gustavkarlsson.snap.domain;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import se.gustavkarlsson.snap.resources.Strings;

public abstract class Node {

	private final String name;
	private FolderNode parent = null;

	public Node(String name) {
		if (name == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL
					+ ": name");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean hasParent() {
		return (parent != null);
	}

	public FolderNode getParent() {
		return parent;
	}

	protected void setParent(FolderNode parent) {
		this.parent = parent;
	}

	protected boolean isAncestor(Node node) {
		if (node == null) {
			return false;
		}
		if (node == this) {
			return true;
		}
		return isAncestor(node.getParent());
	}

	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder(17, 31);
		builder.append(name);
		return builder.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Node node = (Node) obj;
		boolean nameEquals = name == node.getName()
				|| (name != null && name.equals(node.getName()));
		return nameEquals;
	}
}
