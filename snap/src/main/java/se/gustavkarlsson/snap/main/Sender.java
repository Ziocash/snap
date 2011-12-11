package se.gustavkarlsson.snap.main;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.util.Observable;

import se.gustavkarlsson.snap.connection.DataConnection;
import se.gustavkarlsson.snap.connection.ServerSocketDataConnection;

public class Sender extends Observable implements Runnable {

	@Override
	public void run() {

	}

	public void initConnection(int port, InetAddress address, String encryptionKey, int compressionRate)
			throws BindException, IOException {
		DataConnection serverConnection = new ServerSocketDataConnection(port, address, encryptionKey, compressionRate);
		serverConnection.open();
	}
}
