package se.gustavkarlsson.snap.gui.filetree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import se.gustavkarlsson.snap.resources.Strings;

public class FileTreeContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object element) {
		return getChildren(element);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object element) {
		if (element instanceof FileNode) {
			return new Object[0];
		}
		if (element instanceof FolderNode) {
			return ((FolderNode) element).getChildren().toArray();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": " + element.getClass().getCanonicalName());
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof FileFolderNode) {
			return ((FileFolderNode) element).getParent();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": " + element.getClass().getCanonicalName());
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof FileNode) {
			return false;
		}
		if (element instanceof FolderNode) {
			return ((FolderNode) element).getChildren().size() > 0;
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": " + element.getClass().getCanonicalName());
	}
}
