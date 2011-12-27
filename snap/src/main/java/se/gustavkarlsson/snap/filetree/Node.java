package se.gustavkarlsson.snap.filetree;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import se.gustavkarlsson.snap.resources.Strings;

public abstract class Node implements Label {

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

	void setParent(FolderNode parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(name).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof Label) { // TODO is instanceof enough??
			Label other = (Label) obj;
			return name.equals(other.getName());
		}
		return false;
	}
}
