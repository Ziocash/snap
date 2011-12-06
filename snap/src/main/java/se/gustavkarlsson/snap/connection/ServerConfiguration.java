package se.gustavkarlsson.snap.connection;

import java.net.InetAddress;

import se.gustavkarlsson.snap.constants.Messages;

public class ServerConfiguration extends Configuration {

	private final String encryptionKey;
	private final int compressionRate; // 0-9
	
	public ServerConfiguration(int port, InetAddress address, String encryptionKey, int compressionRate) {
		super(port, address);
		if (compressionRate < 0 || 9 < compressionRate) {
			throw new IllegalArgumentException(Messages.COMPRESSION_RATE_OUT_OF_RANGE + ": " + compressionRate);
		}

		this.encryptionKey = encryptionKey;
		this.compressionRate = compressionRate;
	}
	
	public boolean isEncrypted() {
		return encryptionKey != null;
	}
	
	public String getEncryptionKey() {
		return encryptionKey;
	}
	
	public boolean isCompressed() {
		return compressionRate != 0;
	}

	public int getCompressionRate() {
		return compressionRate;
	}
}
