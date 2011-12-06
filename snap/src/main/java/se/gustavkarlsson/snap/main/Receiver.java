package se.gustavkarlsson.snap.main;

import java.io.IOException;
import java.net.BindException;
import java.util.Observable;

import se.gustavkarlsson.snap.connection.Configuration;
import se.gustavkarlsson.snap.connection.DataConnection;
import se.gustavkarlsson.snap.connection.SocketDataConnection;

public class Receiver extends Observable implements Runnable {

	@Override
	public void run() {

	}

	public void initConnection(Configuration config)
			throws BindException, IOException {
		DataConnection clientConnection = new SocketDataConnection(config);
		clientConnection.open();
	}

}
