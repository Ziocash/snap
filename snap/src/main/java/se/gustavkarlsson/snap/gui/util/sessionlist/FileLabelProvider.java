package se.gustavkarlsson.snap.gui.util.sessionlist;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;

import se.gustavkarlsson.snap.resources.Strings;

public class FileLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof File) {
			return ((File) element).getName();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ element.getClass().getCanonicalName());
	}
}