package se.gustavkarlsson.snap.tree.filetree;

import java.util.Comparator;

import org.eclipse.jface.viewers.ViewerComparator;

public class FileViewerComparator extends ViewerComparator {
	
	public FileViewerComparator() {
		super();
	}
	
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
