package se.gustavkarlsson.snap.service.protocol;

public abstract class OpCodes {

	/**
	 * Encrypted? Compressed?
	 */
	public static final byte S_CONNECTION_INFO = 1;
	public static final byte S_CONNECTION_INFO_RECEIVED = 1;

	/**
	 * A list of file and folder names
	 */
	public static final byte S_FILE_LIST = 1;
	public static final byte R_FILE_LIST_ACCEPTED = 2;
	public static final byte R_FILE_LIST_REJECTED = 3;

	/**
	 * Size and relative path of the file
	 */
	public static final byte S_FILE_INFO = 4;
	public static final byte R_FILE_INFO_ACCEPTED = 5;
	public static final byte R_FILE_INFO_REJECTED = 6;

	public static final byte S_FILE_CHUNK = 7;
	public static final byte S_PAUSE = 8;
	public static final byte S_RESUME = 9;
	public static final byte S_ABORT = 10;

	public static final byte R_SEND_MORE = 11;
	public static final byte R_PAUSE = 12;
	public static final byte R_RESUME = 13;
	public static final byte R_ABORT = 14;

}
