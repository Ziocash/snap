package se.gustavkarlsson.snap.connection;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import org.apache.log4j.Logger;

import se.gustavkarlsson.snap.util.LoggerHelper;

public class ServerSocketDataConnection extends SocketDataConnection {

	private static final Logger log = LoggerHelper.getLogger();

	private ServerSocket serverSocket;

	public ServerSocketDataConnection(Configuration config)
			throws BindException, IOException {
		super(config);

		serverSocket = new ServerSocket(this.config.getPort(), 1, this.config.getAddress());
	}

	@Override
	public void open() throws IOException {
		socket = serverSocket.accept();
		serverSocket.close();
	}
}
