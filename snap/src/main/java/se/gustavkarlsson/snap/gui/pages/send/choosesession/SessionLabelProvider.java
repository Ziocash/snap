package se.gustavkarlsson.snap.gui.pages.send.choosesession;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;

import se.gustavkarlsson.snap.resources.Strings;

public class SessionLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof File) {
			return ((File) element).getName();
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ element.getClass().getCanonicalName());
	}
}