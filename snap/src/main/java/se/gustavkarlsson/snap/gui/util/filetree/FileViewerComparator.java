package se.gustavkarlsson.snap.gui.util.filetree;

import java.util.Comparator;

import org.eclipse.jface.viewers.ViewerComparator;

import se.gustavkarlsson.snap.domain.FileNode;
import se.gustavkarlsson.snap.domain.FolderNode;

public class FileViewerComparator extends ViewerComparator {

	public FileViewerComparator() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public FileViewerComparator(Comparator comparator) {
		super(comparator);
	}

	@Override
	public int category(Object element) {
		if (element instanceof FolderNode) {
			return 1;
		}
		if (element instanceof FileNode) {
			return 2;
		}
		return super.category(element);
	}
}
