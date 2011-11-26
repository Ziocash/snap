package se.gustavkarlsson.snap.protocol;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import se.gustavkarlsson.snap.protocol.Pdu;

public abstract class PduTest {

	private static final int ITERATIONS_TO_RUN = 100;

	private Random random = new Random();
	protected Pdu pdu;

	protected abstract void createInstance();

	@Test(expected = IllegalArgumentException.class)
	public void putPadThrowsIllegalArgumentExceptionOnMinusOne()
			throws Exception {
		createInstance();

		pdu.putPad(-1);
	}

	@Test(expected = NullPointerException.class)
	public void putBytesThrowsNullPointerExceptionOnNull() throws Exception {
		createInstance();

		pdu.putBytes(null);
	}

	@Test(expected = NullPointerException.class)
	public void putStringThrowsNullPointerExceptionOnNull() throws Exception {
		createInstance();

		pdu.putStringUtf8(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void skipDoesNotAcceptMinusOne() throws Exception {
		createInstance();

		pdu.skip(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void skipThrowsIllegalArgumentExceptionOnMinusOne() throws Exception {
		createInstance();

		pdu.skip(-1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void skipDoesNotAcceptOneIfPDUIsEmpty() throws Exception {
		createInstance();

		pdu.skip(1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void skipDoesNotAcceptOneIfSizeIsOneAndPositionIsOne()
			throws Exception {
		createInstance();

		pdu.putPad(1);
		pdu.skip(1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getBooleanAsByteDoesNotWorkOnEmptyPDU() throws Exception {
		createInstance();

		pdu.getBooleanAsByte();
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getByteDoesNotWorkOnEmptyPDU() throws Exception {
		createInstance();

		pdu.getByte();
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getBytesDoesNotWorkOnEmptyPDU() throws Exception {
		createInstance();

		pdu.getBytes(10);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getShortDoesNotWorkOnEmptyPDU() throws Exception {
		createInstance();

		pdu.getShort();
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getIntDoesNotWorkOnEmptyPDU() throws Exception {
		createInstance();

		pdu.getInt();
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getLongDoesNotWorkOnEmptyPDU() throws Exception {
		createInstance();

		pdu.getLong();
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getStringDoesNotWorkOnEmptyPDU() throws Exception {
		createInstance();

		pdu.getStringUtf8();
	}

	@Test
	public void getBooleanAsByteReturnsSameAsWasPut() throws Exception {
		createInstance();

		boolean t = true;
		boolean f = false;

		pdu.putBooleanAsByte(t);
		pdu.resetPosition();
		assertEquals(t, pdu.getBooleanAsByte());

		createInstance();

		pdu.putBooleanAsByte(f);
		pdu.resetPosition();
		assertEquals(f, pdu.getBooleanAsByte());
	}

	@Test
	public void getByteReturnsSameAsWasPut() throws Exception {
		// Special values
		putAndGetByte((byte) 0);
		putAndGetByte((byte) 1);
		putAndGetByte((byte) -1);
		putAndGetByte(Byte.MAX_VALUE);
		putAndGetByte(Byte.MIN_VALUE);

		// Random values
		byte[] bytes = new byte[ITERATIONS_TO_RUN];
		random.nextBytes(bytes);
		for (int i = 0; i < bytes.length; i++) {
			putAndGetByte(bytes[i]);
		}
	}

	@Test
	public void getBytesReturnsSameAsWasPut() throws Exception {
		// Special values
		putAndGetBytes(new byte[] { (byte) 0, (byte) 1, (byte) -1,
				Byte.MAX_VALUE, Byte.MIN_VALUE });

		// Random values
		int testsLeft = ITERATIONS_TO_RUN;
		while (testsLeft > 0) {
			int arraySize = random.nextInt(20);
			if (arraySize > testsLeft) {
				arraySize = testsLeft;
			}
			testsLeft -= arraySize;

			byte[] bytes = new byte[arraySize];
			random.nextBytes(bytes);
			putAndGetBytes(bytes);
		}
	}

	@Test
	public void getShortReturnsSameAsWasPut() throws Exception {
		// Special values
		putAndGetShort((short) 0);
		putAndGetShort((short) 1);
		putAndGetShort((short) -1);
		putAndGetShort(Short.MAX_VALUE);
		putAndGetShort(Short.MIN_VALUE);

		// Random values
		for (int i = 0; i < ITERATIONS_TO_RUN / 2; i++) {
			putAndGetShort((short) random.nextInt(Short.MAX_VALUE));
			putAndGetShort((short) -random.nextInt(Math.abs(Short.MIN_VALUE)));
		}
	}

	@Test
	public void getIntReturnsSameAsWasPut() throws Exception {
		// Special values
		putAndGetInt(0);
		putAndGetInt(1);
		putAndGetInt(-1);
		putAndGetInt(Integer.MAX_VALUE);
		putAndGetInt(Integer.MIN_VALUE);

		// Random values
		for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
			putAndGetInt(random.nextInt());
		}
	}

	@Test
	public void getLongReturnsSameAsWasPut() throws Exception {
		// Special values
		putAndGetLong(0);
		putAndGetLong(1);
		putAndGetLong(-1);
		putAndGetLong(Long.MAX_VALUE);
		putAndGetLong(Long.MIN_VALUE);

		// Random values
		for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
			putAndGetLong(random.nextLong());
		}
	}

	@Test
	public void getStringReturnsSameAsWasPut() throws Exception {
		putAndGetString("");
		putAndGetString("hej");
		putAndGetString("Testar att skriva n�got lite l�ngre.");
		putAndGetString("=\")�@UGVM(�)?hv23");
		putAndGetString("65412");

		for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
			putAndGetString(Long.toString(Math.abs(random.nextLong()), 36));
		}
	}

	@Test
	public void sizeReturnsSizeOfData() throws Exception {
		createInstance();

		assertEquals(0, pdu.size());

		pdu.putPad(0);
		assertEquals(0, pdu.size());

		pdu.putPad(1);
		assertEquals(1, pdu.size());

		pdu.putPad(2);
		assertEquals(3, pdu.size());

		pdu.putByte((byte) 6);
		assertEquals(4, pdu.size());

		pdu.putByte((byte) 14);
		assertEquals(5, pdu.size());

		pdu.putBytes(new byte[] { (byte) 1, (byte) 2, (byte) 3 });
		assertEquals(8, pdu.size());

		pdu.putShort((short) 5);
		assertEquals(10, pdu.size());

		pdu.putInt(5);
		assertEquals(14, pdu.size());

		pdu.putLong(12356);
		assertEquals(22, pdu.size());
	}

	@Test
	public void remainingReturnsRemainingData() throws Exception {
		createInstance();

		nothingRemaining();

		pdu.putPad(0);
		nothingRemaining();

		pdu.putPad(1);
		nothingRemaining();

		pdu.putPad(2);
		nothingRemaining();

		createInstance();
		pdu.putPad(5);
		pdu.resetPosition();
		assertEquals(5, pdu.remaining());

		createInstance();
		pdu.putByte((byte) 1);
		pdu.resetPosition();
		assertEquals(1, pdu.remaining());

		createInstance();
		pdu.putBytes(new byte[] { (byte) 1, (byte) 2, (byte) 3 });
		pdu.resetPosition();
		assertEquals(3, pdu.remaining());

		createInstance();
		pdu.putShort((short) 1);
		pdu.resetPosition();
		assertEquals(2, pdu.remaining());

		createInstance();
		pdu.putInt(1);
		pdu.resetPosition();
		assertEquals(4, pdu.remaining());

		createInstance();
		pdu.putLong(1);
		pdu.resetPosition();
		assertEquals(8, pdu.remaining());

		createInstance();
		String testString = "Hall� d�r!%";
		pdu.putStringUtf8(testString);
		pdu.resetPosition();
		assertEquals((Integer.SIZE / Byte.SIZE)
				+ testString.getBytes("UTF-8").length, pdu.remaining());
	}

	@Test
	public void toByteArrayReturnsAccurateData() throws Exception {
		int testsLeft = ITERATIONS_TO_RUN;
		while (testsLeft > 0) {
			createInstance();

			int arraySize = random.nextInt(20);
			if (arraySize > testsLeft) {
				arraySize = testsLeft;
			}
			testsLeft -= arraySize;

			byte[] bytes = new byte[arraySize];
			random.nextBytes(bytes);

			pdu.putBytes(bytes);
			assertArrayEquals(bytes, pdu.toByteArray());
		}
	}

	@Test
	public void resetPositionResetsPosition() throws Exception {
		createInstance();

		resetResets();

		pdu.putInt(4);
		resetResets();

		pdu.putStringUtf8("hejsan");
		resetResets();

		pdu.getLong();
		resetResets();
	}

	@Test
	public void getPositionReturnsThePosition() throws Exception {
		createInstance();

		assertEquals(0, pdu.getPosition());

		pdu.putPad(0);
		assertEquals(0, pdu.getPosition());

		pdu.putPad(1);
		assertEquals(1, pdu.getPosition());

		pdu.putPad(2);
		assertEquals(3, pdu.getPosition());

		pdu.resetPosition();
		pdu.putByte((byte) 6);
		assertEquals(1, pdu.getPosition());

		pdu.putByte((byte) 14);
		assertEquals(2, pdu.getPosition());

		pdu.putBytes(new byte[] { (byte) 1, (byte) 2, (byte) 3 });
		assertEquals(5, pdu.getPosition());

		pdu.putShort((short) 5);
		assertEquals(7, pdu.getPosition());

		pdu.putInt(5);
		assertEquals(11, pdu.getPosition());

		pdu.putLong(12356);
		assertEquals(19, pdu.getPosition());
	}

	private void putAndGetByte(byte value) {
		createInstance();

		pdu.putByte(value);
		pdu.resetPosition();
		assertEquals(value, pdu.getByte());
		nothingRemaining();
	}

	private void putAndGetBytes(byte[] bytes) {
		createInstance();

		pdu.putBytes(bytes);
		pdu.resetPosition();
		assertArrayEquals(bytes, pdu.getBytes(bytes.length));
		nothingRemaining();
	}

	private void putAndGetShort(short value) {
		createInstance();

		pdu.putShort(value);
		pdu.resetPosition();
		assertEquals(value, pdu.getShort());
		nothingRemaining();
	}

	private void putAndGetInt(int value) {
		createInstance();

		pdu.putInt(value);
		pdu.resetPosition();
		assertEquals(value, pdu.getInt());
		nothingRemaining();
	}

	private void putAndGetLong(long value) {
		createInstance();

		pdu.putLong(value);
		pdu.resetPosition();
		assertEquals(value, pdu.getLong());
		nothingRemaining();
	}

	private void putAndGetString(String value) {
		createInstance();

		pdu.putStringUtf8(value);
		pdu.resetPosition();
		assertEquals(value, pdu.getStringUtf8());
		nothingRemaining();
	}

	private void nothingRemaining() {
		assertEquals(0, pdu.remaining());
	}

	private boolean resetResets() {
		pdu.resetPosition();
		return pdu.getPosition() == 0;
	}
}
