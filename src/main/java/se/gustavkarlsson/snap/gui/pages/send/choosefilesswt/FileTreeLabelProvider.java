package se.gustavkarlsson.snap.gui.pages.send.choosefilesswt;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.domain.FileNode;
import se.gustavkarlsson.snap.domain.FolderNode;
import se.gustavkarlsson.snap.domain.Node;
import se.gustavkarlsson.snap.resources.Images;
import se.gustavkarlsson.snap.resources.Strings;

public class FileTreeLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof Node) {
			return ((Node) element).getName();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ element.getClass().getCanonicalName());
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof FileNode) {
			return Images.FILE;
		}
		if (element instanceof FolderNode) {
			return Images.FOLDER;
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ element.getClass().getCanonicalName());
	}
}