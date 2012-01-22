package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.domain.FileFolderLabel;
import se.gustavkarlsson.snap.resources.Strings;

public class FileTreeLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof FileFolderLabel) {
			return ((FileFolderLabel) element).getName();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ element.getClass().getCanonicalName());
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof FileFolderLabel) {
			return ((FileFolderLabel) element).getImage();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ element.getClass().getCanonicalName());
	}
}