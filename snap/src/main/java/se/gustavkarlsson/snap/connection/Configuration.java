package se.gustavkarlsson.snap.connection;

import java.net.InetAddress;
import java.net.UnknownHostException;

import se.gustavkarlsson.snap.constants.Messages;

public class Configuration {

	protected static final int MAX_PORT = 65535;

	protected final int port;
	protected final InetAddress address;

	public Configuration(int port, InetAddress address) {
		if (port < 0 || MAX_PORT < port) {
			throw new IllegalArgumentException(Messages.PORT_OUT_OF_RANGE + ": " + port);
		} else if (address == null) {
			throw new IllegalArgumentException(Messages.ARGUMENT_IS_NULL);
		}
		
		this.port = port;
		this.address = address;
	}

	public Configuration() throws UnknownHostException {
		this(0, InetAddress.getByAddress(new byte[] { 0, 0, 0, 0 }));
	}

	public int getPort() {
		return port;
	}

	public InetAddress getAddress() {
		return address;
	}
}
