package se.gustavkarlsson.snap.service.session;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import se.gustavkarlsson.snap.resources.Directories;
import se.gustavkarlsson.snap.util.LoggerHelper;

public class SessionManager {
	private static final Logger LOG = LoggerHelper.getLogger();
	
	private final File sessionsDir = new File(Directories.SESSIONS);
	private final FileFilter sessionFilter = new SessionFilter();

	private List<File> sessions = new ArrayList<File>();
	private File currentSession = null;
	private boolean sessionChanged = false;

	public List<File> getSessions() {
		return sessions;
	}
	
	public File getCurrentSession() {
		return currentSession;
	}
	
	public void setCurrentSession(File session) {
		currentSession = session;
	}
	
	public boolean hasCurrentSession() {
		return currentSession != null;
	}

	public boolean hasSessionChanged() {
		return sessionChanged;
	}

	public void setSessionChanged(boolean sessionChanged) {
		this.sessionChanged = sessionChanged;
	}

	public void update() {
		if (!sessionsDir.exists()) {
			LOG.info("Sessions directory does not exist (" + sessionsDir.getAbsolutePath() + "). Creating...");
			if (sessionsDir.mkdir()) {
				LOG.info("Sessions directory successfully created");
			} else {
				LOG.error("Sessions directory could not be created");
				// TODO throw exception and catch in GUI to display error dialog
			}
		}
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
		public boolean accept(File file) {
			return file.isFile() && file.getName().endsWith(EXTENSION);
		}
	}
}
