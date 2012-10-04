package se.gustavkarlsson.snap.domain.tree;

public abstract class Node<T>  {

	private T value = null;

	public Node() {
	}

	public Node(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T newValue) {
		value = newValue;
	}

	@Override
	public String toString() {
		return "Node: " + value;
	}

	public abstract boolean isChildrenAllowed();
}
