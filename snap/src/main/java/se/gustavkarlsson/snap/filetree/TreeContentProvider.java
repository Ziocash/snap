package se.gustavkarlsson.snap.filetree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import se.gustavkarlsson.snap.resources.Strings;

public class TreeContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object element) {
		return getChildren(element);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput == null || (newInput instanceof FolderNode)) {
			return;
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ newInput.getClass().getCanonicalName());
	}

	@Override
	public Object[] getChildren(Object element) {
		if (element instanceof FolderNode) {
			return ((FolderNode) element).listChildren();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ element.getClass().getCanonicalName());
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Node) {
			return ((Node) element).getParent();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ element.getClass().getCanonicalName());
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof FolderNode) {
			return ((FolderNode) element).hasChildren();
		}
		return false;
	}
}
