package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import se.gustavkarlsson.snap.domain.FolderNode;
import se.gustavkarlsson.snap.domain.Node;

public class InternalFileTransfer extends ByteArrayTransfer {
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
		try {
			byte[] bytes = serializeNodes((Object[]) object);
			if (bytes != null) {
				super.javaToNative(bytes, transferData);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected Object nativeToJava(TransferData transferData) {
		byte[] bytes = (byte[]) super.nativeToJava(transferData);
		try {
			Object nodes = deserializeNodes(bytes);
			return nodes;
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] serializeNodes(Object[] nodes) throws IOException {
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		
		Map<FolderNode, Node> parentChildMap = new HashMap<FolderNode, Node>();
		for (Object object : nodes) {
			Node node = (Node) object;
			FolderNode nodeParent = node.getParent();
			if (nodeParent != null) {
				// Detach node from tree (needed for serialization)
				node.getParent().removeChild(node);
				parentChildMap.put(nodeParent, node);
			}
		}

		try {
			ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut);
			objectOut.writeObject(nodes);
			return bytesOut.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			for (FolderNode nodeParent : parentChildMap.keySet()) {
				Node child = parentChildMap.get(nodeParent);
				if (nodeParent != null) {
					// Detach node from tree (needed for serialization)
					nodeParent.addChild(child);
				}
			}
		}
	}

	private static Object deserializeNodes(byte[] bytes) throws ClassNotFoundException,
			ClassCastException, IOException {
		ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);

		ObjectInputStream objectIn = new ObjectInputStream(bytesIn);
		try {
			Object nodes = objectIn.readObject();
			return nodes;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

}
