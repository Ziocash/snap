package se.gustavkarlsson.snap.protocol;

public class ArrayPdu implements Pdu {

	@Override
	public void putPad(int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putBooleanAsByte(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putByte(byte b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putBytes(byte[] bytes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putShort(short s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putInt(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putLong(long l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putStringUtf8(String string) {
		// TODO Auto-generated method stub

	}

	@Override
	public void skip(int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getBooleanAsByte() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getByte() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getBytes(int length) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getShort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStringUtf8() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int remaining() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPosition(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetPosition() {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] toByteArray() {
		// TODO Auto-generated method stub
		return null;
	}

}
