package se.gustavkarlsson.snap.service;

import se.gustavkarlsson.snap.session.SessionManager;

public class Service {
	
	private final SessionManager sessionManager = new SessionManager();

	public SessionManager getSessionManager() {
		return sessionManager;
	}

}
