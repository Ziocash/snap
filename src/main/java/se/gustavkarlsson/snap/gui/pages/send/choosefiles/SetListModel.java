package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class SetListModel<T> extends AbstractListModel {
	private List<T> elements;

	public SetListModel(List<T> elements) {
		setElements(elements);
	}

	public SetListModel() {
		this(new ArrayList<T>());
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Object getElementAt(int index) {
		return elements.get(index);
	}

	public int size() {
		return elements.size();
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		if (elements == null) {
			throw new IllegalArgumentException("elements can't be null");
		}
		this.elements = elements;
	}

	public boolean contains(T elem) {
		return elements.contains(elem);
	}

	public int indexOf(T elem) {
		return elements.indexOf(elem);
	}

	public void add(T elem) {
		if (!elements.contains(elem)) {
			elements.add(elem);
			int lastIndex = elements.size()-1;
			fireIntervalAdded(this, lastIndex, lastIndex);
		}
	}

	public void add(Collection<T> newElements) {
		int lowestIndex = elements.size();
		if (!elements.addAll(newElements)) {
			return;
		}
		int highestIndex = elements.size()-1;
		fireIntervalRemoved(this, lowestIndex, highestIndex);
	}

	public boolean remove(T elem) {
		int index = indexOf(elem);
		boolean removed = elements.remove(elem);
		if (removed) {
			fireIntervalRemoved(this, index, index);
		}
		return removed;
	}

	public T remove(int index) {
		T removed = elements.remove(index);
		fireIntervalRemoved(this, index, index);
		return removed;
	}

	public void remove(int fromIndex, int toIndex) {
		if (fromIndex > toIndex) {
			throw new IllegalArgumentException("fromIndex must be <= toIndex");
		}
		if (fromIndex < 0) {
			throw new ArrayIndexOutOfBoundsException(fromIndex);
		}
		if (toIndex >= elements.size()) {
			throw new ArrayIndexOutOfBoundsException(toIndex);
		}

		for (int i = toIndex; i >= fromIndex; i--) {
			elements.remove(i);
		}
		fireIntervalRemoved(this, fromIndex, toIndex);
	}

	//  TODO: Implement public void remove(Collection<T> elements
	//	public void remove(Collection<T> elements) {
	//
	//	}

	public void clear() {
		int lastIndex = elements.size()-1;
		elements.clear();
		if (lastIndex >= 0) {
			fireIntervalRemoved(this, 0, lastIndex);
		}
	}

	@Override
	public String toString() {
		return elements.toString();
	}
}
