package se.gustavkarlsson.snap.session;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.gustavkarlsson.snap.resources.Directories;

public class SessionManager {
	private final File sessionsDir = new File(Directories.SESSIONS);
	private final FileFilter sessionFilter = new SessionFilter();

	private List<File> sessions = new ArrayList<File>();

	public List<File> getSessions() {
		return sessions;
	}

	public void update() {
		File[] sessionsTemp = sessionsDir.listFiles(sessionFilter);

		if (sessionsTemp == null) {
			sessions = new ArrayList<File>();
		} else {
			sessions = Arrays.asList(sessionsTemp);
		}
	}

	private class SessionFilter implements FileFilter {

		private final String EXTENSION = ".odb";

		@Override
		public boolean accept(File pathName) {
			return pathName.getName().endsWith(EXTENSION);
		}
	}
}
