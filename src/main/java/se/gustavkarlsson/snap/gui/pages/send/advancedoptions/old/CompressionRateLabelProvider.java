package se.gustavkarlsson.snap.gui.pages.send.advancedoptions.old;

import org.eclipse.jface.viewers.LabelProvider;

import se.gustavkarlsson.snap.resources.Strings;

public class CompressionRateLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof Integer) {
			int compressionRate = (Integer) element;

			switch (compressionRate) {
			case 1:
				return "Low";
			case 5:
				return "Medium";
			case 9:
				return "High";

			default:
				// TODO log error
				return new Integer(compressionRate).toString();
			}
		} else {
			throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE
					+ ": " + element.getClass().getCanonicalName());
		}
	}

}
