package se.gustavkarlsson.snap.gui.filetree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.resources.Strings;

public class FileTreeLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof FileFolderNode) {
			return ((FileFolderNode) element).getName();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": " + element.getClass().getCanonicalName());
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof FileFolderNode) {
			return ((FileFolderNode) element).getImage();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": " + element.getClass().getCanonicalName());
	}
}