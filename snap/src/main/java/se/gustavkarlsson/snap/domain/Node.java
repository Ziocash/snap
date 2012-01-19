package se.gustavkarlsson.snap.domain;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import se.gustavkarlsson.snap.resources.Strings;

@MappedSuperclass
public abstract class Node implements Label {

	@Column(name = "Name", nullable = false)
	private String name;

	@Column(name = "Parent_FK")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FolderID", nullable = true)
	private FolderNode parent = null;

	public Node() {
	}

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

	public void setName(String name) {
		this.name = name;
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
