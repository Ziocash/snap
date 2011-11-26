package se.gustavkarlsson.snap.protocol;

import se.gustavkarlsson.snap.protocol.ArrayPdu;

public class ArrayPduTest extends PduTest {
	@Override
	public void createInstance() {
		pdu = new ArrayPdu();
	}
}
