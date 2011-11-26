package se.gustavkarlsson.snap.connection;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import se.gustavkarlsson.snap.constants.Messages;
import se.gustavkarlsson.snap.util.LoggerHelper;

public class SocketDataConnection implements DataConnection {

	private static final Logger log = LoggerHelper.getLogger();
	private static final int MAX_PORT = 65535;
	private static final int BUFFER_SIZE = 65536;

	protected InetAddress address;
	protected int port;
	protected Socket socket;

	private InputStream in;
	private OutputStream out;

	public SocketDataConnection(InetAddress address, int port)
			throws IllegalArgumentException {
		if (port < 0 || MAX_PORT < port) {
			throw new IllegalArgumentException(Messages.PORT_OUT_OF_RANGE
					+ ": " + port);
		} else if (address == null) {
			throw new IllegalArgumentException(Messages.ARGUMENT_IS_NULL);
		}

		this.address = address;
		this.port = port;
	}

	@Override
	public void open() throws IOException {
		socket = new Socket(address, port);
		setup();
	}

	@Override
	public void close() throws IOException {
		if (socket != null && !socket.isClosed()) {
			socket.close();
		}
	}

	@Override
	public byte[] read(int length) throws EOFException, IOException {
		byte[] data = new byte[length];
		int receivedTotal = 0;

		do {
			int received = in.read(data, receivedTotal, length - receivedTotal);

			if (received == -1) {
				throw new EOFException();
			}

			receivedTotal += received;
		} while (receivedTotal < length);

		return data;
	}

	@Override
	public void write(byte[] data) throws IOException {
		out.write(data);
		out.flush();
	}

	protected void setup() throws IOException {
		try {
			socket.setKeepAlive(true);
		} catch (SocketException e) {
			log.warn("Could not set SO_KEEPALIVE", e);
		}
		try {
			socket.setSoTimeout(0);
		} catch (SocketException e) {
			log.warn("Could not set SO_TIMEOUT", e);
		}
		try {
			socket.setSendBufferSize(BUFFER_SIZE);
		} catch (SocketException e) {
			log.warn("Could not set SO_SNDBUF", e);
		}
		try {
			socket.setReceiveBufferSize(BUFFER_SIZE);
		} catch (SocketException e) {
			log.warn("Could not set SO_RCVBUF", e);
		}

		in = socket.getInputStream();
		out = socket.getOutputStream();
	}
}
