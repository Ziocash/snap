package se.gustavkarlsson.snap.service;

import se.gustavkarlsson.snap.service.session.SessionManager;

public class Service {
	
	private final SessionManager sessionManager = new SessionManager();

	public SessionManager getSessionManager() {
		return sessionManager;
	}

}
