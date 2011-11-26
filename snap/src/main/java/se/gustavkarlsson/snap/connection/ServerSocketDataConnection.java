package se.gustavkarlsson.snap.connection;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

import org.apache.log4j.Logger;

import se.gustavkarlsson.snap.util.LoggerHelper;

public class ServerSocketDataConnection extends SocketDataConnection {

	private static final Logger log = LoggerHelper.getLogger();

	private ServerSocket serverSocket;

	public ServerSocketDataConnection(InetAddress address, int port)
			throws BindException, IOException, IllegalArgumentException {
		super(address, port);

		serverSocket = new ServerSocket(this.port, 1, this.address);
	}

	@Override
	public void open() throws IOException {
		socket = serverSocket.accept();
		serverSocket.close();
	}
}
