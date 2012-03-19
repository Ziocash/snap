package se.gustavkarlsson.snap.service.connection;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerSocketDataConnection extends SocketDataConnection {

	private ServerSocket serverSocket;

	public ServerSocketDataConnection(int port, InetAddress address,
			String encryptionKey, int compressionRate) throws BindException,
			IOException {
		super(port, address, encryptionKey);

		serverSocket = new ServerSocket(port, 1, address);
	}

	@Override
	public void open() throws IOException {
		socket = serverSocket.accept();
		serverSocket.close();
	}
}
