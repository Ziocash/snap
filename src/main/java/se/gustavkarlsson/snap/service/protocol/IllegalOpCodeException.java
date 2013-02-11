package se.gustavkarlsson.snap.service.protocol;

public class IllegalOpCodeException extends Exception {
	private static final long serialVersionUID = -6164341375170458340L;

	public IllegalOpCodeException(byte expected, byte received) {
		super("Expected " + expected + ". Received " + received);
	}

	public IllegalOpCodeException(byte[] expected, byte received) {
		super("Expected " + expected + ". Received " + received);
	}

}
