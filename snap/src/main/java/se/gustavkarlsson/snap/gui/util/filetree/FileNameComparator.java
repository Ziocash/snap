package se.gustavkarlsson.snap.gui.util.filetree;

import java.util.Comparator;

public class FileNameComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		int compared = o1.compareToIgnoreCase(o2);

		if (compared == 0) {
			compared = o1.compareTo(o2);
		}

		return compared;
	}
}
