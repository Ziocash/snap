package se.gustavkarlsson.snap.connection;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerProperties {

	private final int port;
	private final InetAddress address;
	private final boolean upnpEnabled;
	private final boolean natPnpEnabled;
	private final boolean encrypt;
	private final int compressionRate; // 0-9

	public ServerProperties(int port, InetAddress address, boolean upnpEnabled,
			boolean natPnpEnabled, boolean encrypt, int compressionRate) {
		this.port = port;
		this.address = address;
		this.upnpEnabled = upnpEnabled;
		this.natPnpEnabled = natPnpEnabled;
		this.encrypt = encrypt;
		this.compressionRate = compressionRate;
	}

	public ServerProperties() throws UnknownHostException {
		this(0, InetAddress.getByAddress(new byte[] { 0, 0, 0, 0 }), true,
				true, false, 0);
	}

	public int getPort() {
		return port;
	}

	public InetAddress getAddress() {
		return address;
	}

	public boolean isUpnpEnabled() {
		return upnpEnabled;
	}

	public boolean isNatPnpEnabled() {
		return natPnpEnabled;
	}

	public boolean isEncrypt() {
		return encrypt;
	}

	public int getCompressionRate() {
		return compressionRate;
	}
}
