package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import se.gustavkarlsson.snap.util.LoggerHelper;

public class InternalFileTransfer extends ByteArrayTransfer {
	private static final Logger logger = LoggerHelper.getLogger();
	private static final InternalFileTransfer instance = new InternalFileTransfer();
	private static final String TYPE_NAME = "internal-file-format";
	private static final int TYPE_ID = registerType(TYPE_NAME);

	public static InternalFileTransfer getInstance() {
		return instance;
	}

	/**
	 * Avoid explicit instantiation
	 */
	private InternalFileTransfer() {
	}

	@Override
	protected int[] getTypeIds() {
		return new int[] { TYPE_ID };
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] { TYPE_NAME };
	}

	@Override
	protected void javaToNative(Object object, TransferData transferData) {
		if (object == null) {
			return;
		}
		InternalFileDndPayload payload;
		try {
			payload = (InternalFileDndPayload) object;
			byte[] bytes = serialize(payload);
			super.javaToNative(bytes, transferData);
		} catch (ClassCastException e) {
			logger.error("Could not cast object of class: "
					+ object.getClass().getCanonicalName() + " to "
					+ InternalFileDndPayload.class.getCanonicalName(), e);
		} catch (IOException e) {
			logger.error("Could not serialize object of class: "
					+ object.getClass().getCanonicalName(), e);
		}
	}

	@Override
	protected Object nativeToJava(TransferData transferData) {
		byte[] bytes = (byte[]) super.nativeToJava(transferData);
		if (bytes == null) {
			return null;
		}
		
		try {
			InternalFileDndPayload payload = deserialize(bytes);
			return payload;
		} catch (ClassNotFoundException e) {
			logger.warn("Could not deserialize bytes", e);
		} catch (IOException e) {
			logger.warn("Could not deserialize bytes", e);
		}
		return null;
	}
	
	private byte[] serialize(InternalFileDndPayload payload) throws IOException {
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut);
		objectOut.writeObject(payload);
		return bytesOut.toByteArray();
	}
	
	private InternalFileDndPayload deserialize(byte[] data) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
		ObjectInputStream objectIn = new ObjectInputStream(bytesIn);
		Object object =  objectIn.readObject();
		InternalFileDndPayload payload = (InternalFileDndPayload) object;
		return payload;
	}
}
