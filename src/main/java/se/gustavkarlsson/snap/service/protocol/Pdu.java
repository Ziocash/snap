package se.gustavkarlsson.snap.service.protocol;

public interface Pdu {

	public void putPad(int length);

	public void putBooleanAsByte(boolean b);

	public void putByte(byte b);

	public void putBytes(byte[] bytes);

	public void putShort(short s);

	public void putInt(int i);

	public void putLong(long l);

	public void putStringUtf8(String string);

	public void skip(int length);

	public boolean getBooleanAsByte();

	public byte getByte();

	public byte[] getBytes(int length);

	public short getShort();

	public int getInt();

	public long getLong();

	public String getStringUtf8();

	public int size();

	public int remaining();

	public int getPosition();

	public void setPosition(int position);

	public void resetPosition();

	public byte[] toByteArray();

}
