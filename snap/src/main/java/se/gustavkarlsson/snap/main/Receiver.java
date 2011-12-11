package se.gustavkarlsson.snap.main;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.util.Observable;

import se.gustavkarlsson.snap.connection.DataConnection;
import se.gustavkarlsson.snap.connection.SocketDataConnection;

public class Receiver extends Observable implements Runnable {

	@Override
	public void run() {

	}

	public void initConnection(int port, InetAddress address,
			String encryptionKey) throws BindException, IOException {
		DataConnection clientConnection = new SocketDataConnection(port,
				address, encryptionKey);
		clientConnection.open();
	}

}
