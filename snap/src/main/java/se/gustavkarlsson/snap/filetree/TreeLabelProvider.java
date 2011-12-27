package se.gustavkarlsson.snap.filetree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.resources.Strings;

public class TreeLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof Label) {
			return ((Label) element).getName();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ element.getClass().getCanonicalName());
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof Label) {
			return ((Label) element).getImage();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ element.getClass().getCanonicalName());
	}
}