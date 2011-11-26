package se.gustavkarlsson.snap.connection;

import java.io.EOFException;
import java.io.IOException;

public interface DataConnection {

	public void open() throws IOException;

	public void close() throws IOException;

	public byte[] read(int length) throws EOFException, IOException;

	public void write(byte[] data) throws IOException;

}
