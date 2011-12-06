package se.gustavkarlsson.snap.main;

import java.io.IOException;
import java.net.BindException;
import java.util.Observable;

import se.gustavkarlsson.snap.connection.DataConnection;
import se.gustavkarlsson.snap.connection.ServerConfiguration;
import se.gustavkarlsson.snap.connection.ServerSocketDataConnection;

public class Sender extends Observable implements Runnable {

	@Override
	public void run() {

	}

	public void initConnection(ServerConfiguration config)
			throws BindException, IOException {
		DataConnection serverConnection = new ServerSocketDataConnection(config);
		serverConnection.open();
	}
}
