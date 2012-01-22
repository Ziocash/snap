package se.gustavkarlsson.snap.service.protocol;

import java.nio.charset.Charset;

import se.gustavkarlsson.snap.resources.Strings;

public class ArrayPdu implements Pdu {
	
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final int BYTE_SIZE = 1;
	private static final int SHORT_SIZE = 2;
	private static final int INT_SIZE = 4;
	private static final int LONG_SIZE = 8;

	private byte[] array;
	private int position = 0;
	private int size = 0;

	public ArrayPdu() {
		array = new byte[10];
	}

	@Override
	public void putPad(final int length) {
		if (length < 0) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NEGATIVE
					+ ": " + length);
		}

		int newPosition = position + length;
		ensureArraySize(newPosition);

		for (int i = position; i < newPosition; i++) {
			array[i] = 0;
		}

		position = newPosition;
		ensurePduSize(position);
	}

	@Override
	public void putBooleanAsByte(boolean b) {
		byte[] bytes = new byte[] { (byte) (b ? 1 : 0) };

		putBytes(bytes);
	}

	@Override
	public void putByte(byte b) {
		byte[] bytes = new byte[] { b };

		putBytes(bytes);
	}

	@Override
	public void putBytes(byte[] bytes) {
		int newPosition = position + bytes.length;
		ensureArraySize(newPosition);

		System.arraycopy(bytes, 0, array, position, bytes.length);

		position = newPosition;
		ensurePduSize(position);
	}

	@Override
	public void putShort(short s) {
		byte[] bytes = primitiveToByteArray(s, SHORT_SIZE);
		
		putBytes(bytes);
	}

	@Override
	public void putInt(int i) {
		byte[] bytes = primitiveToByteArray(i, INT_SIZE);
		
		putBytes(bytes);
	}

	@Override
	public void putLong(long l) {
		byte[] bytes = primitiveToByteArray(l, LONG_SIZE);
		
		putBytes(bytes);
	}

	@Override
	public void putStringUtf8(String string) {
		byte[] stringBytes = string.getBytes(UTF_8);
		
		byte[] lengthBytes = primitiveToByteArray(stringBytes.length, INT_SIZE);

		putBytes(lengthBytes);
		putBytes(stringBytes);
	}

	@Override
	public void skip(int length) {
		if (length < 0) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NEGATIVE
					+ ": " + length);
		}
		int newPosition = position + length;
		checkPduSize(newPosition);
		
		position = newPosition;
	}

	@Override
	public boolean getBooleanAsByte() {
		byte[] bytes = getBytes(BYTE_SIZE);
		
		boolean b = (bytes[0] == 0) ? false : true;
		
		return b;
	}

	@Override
	public byte getByte() {
		byte[] bytes = getBytes(BYTE_SIZE);
		
		byte b = bytes[0];
		
		return b;
	}

	@Override
	public byte[] getBytes(int length) {
		if (length < 0) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NEGATIVE
					+ ": " + length);
		}
		int newPosition = position + length;
		checkPduSize(newPosition);
		
		byte[] bytes = new byte[length];
		System.arraycopy(array, position, bytes, 0, length);
		
		position = newPosition;
		return bytes;
	}

	@Override
	public short getShort() {
		byte[] bytes = getBytes(SHORT_SIZE);
		
		short s = (short) byteArrayToPrimitive(bytes);
		
		return s;
	}

	@Override
	public int getInt() {
		byte[] bytes = getBytes(INT_SIZE);
		
		int i = (int) byteArrayToPrimitive(bytes);
		
		return i;
	}

	@Override
	public long getLong() {
		byte[] bytes = getBytes(LONG_SIZE);
		
		long l = byteArrayToPrimitive(bytes);
		
		return l;
	}

	@Override
	public String getStringUtf8() {
		byte[] lengthBytes = new byte[INT_SIZE];
		System.arraycopy(array, position, lengthBytes, 0, INT_SIZE);
		int length = (int) byteArrayToPrimitive(lengthBytes);
		
		byte[] stringBytes = new byte[length];
		System.arraycopy(array, position + INT_SIZE, stringBytes, 0, length);
		String string = new String(stringBytes, UTF_8);
		
		skip(INT_SIZE);
		skip(length);
		return string;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int remaining() {
		return size - position;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public void setPosition(int position) {
		if (position < 0) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NEGATIVE
					+ ": " + position);
		}
		this.position = position;
	}

	@Override
	public void resetPosition() {
		position = 0;
	}

	@Override
	public byte[] toByteArray() {
		byte[] bytes = new byte[size];
		System.arraycopy(array, 0, bytes, 0, size);
		return bytes;
	}

	private void ensureArraySize(final int mustFit) {
		if (array.length < mustFit) {
			int newSize = array.length;
			do {
				newSize *= 2;
			} while (newSize < mustFit);

			byte[] newArray = new byte[newSize];
			System.arraycopy(array, 0, newArray, 0, size);

			array = newArray;
		}
	}

	private void ensurePduSize(final int possibleNewSize) {
		if (size < possibleNewSize) {
			size = possibleNewSize;
		}
	}
	
	private void checkPduSize(final int mustFit) {
		if (size < mustFit) {
			throw new IndexOutOfBoundsException("PDU position out of bounds: " + mustFit);
		}
	}
	
	private byte[] primitiveToByteArray(long value, int size) {
		byte[] bytes = new byte[size];
		
		for (int i = 0; i < size; i++) {
			int shift = (size - i - 1) * 8;
			bytes[i] = (byte)((value >> shift) & 0xff);
		}
		
		return bytes;
	}
	
	private long byteArrayToPrimitive(byte[] bytes) {
		long value = 0;
		
		for (int i = 0; i < bytes.length; i++) {
			int shift = (bytes.length - i - 1) * 8;
			value |= (long)(0xff & bytes[i]) << shift;
		}
		
		return value;
	}
}
