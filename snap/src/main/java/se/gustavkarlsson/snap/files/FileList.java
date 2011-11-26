package se.gustavkarlsson.snap.files;

import java.util.ArrayList;
import java.util.List;

import se.gustavkarlsson.snap.protocol.ArrayPdu;
import se.gustavkarlsson.snap.protocol.IllegalOpCodeException;
import se.gustavkarlsson.snap.protocol.OpCodes;
import se.gustavkarlsson.snap.protocol.Pdu;

@SuppressWarnings("serial")
public class FileList extends ArrayList<FileInfo> {

	public static Pdu createPDU(List<FileInfo> files) {
		Pdu pdu = new ArrayPdu();
		pdu.putByte(OpCodes.S_FILE_LIST);
		pdu.putInt(files.size());

		for (FileInfo file : files) {
			pdu.putBooleanAsByte(file.isFile());
			pdu.putStringUtf8(file.getName());
		}

		return pdu;
	}

	public static FileList parsePDU(Pdu pdu) throws IllegalOpCodeException {
		FileList list = new FileList();

		byte OPCode = pdu.getByte();
		if (OPCode != OpCodes.S_FILE_LIST) {
			throw new IllegalOpCodeException(OpCodes.S_FILE_LIST, OPCode);
		}

		int numberOfFiles = pdu.getInt();

		for (int i = 0; i < numberOfFiles; i++) {
			boolean isFile = pdu.getBooleanAsByte();
			String name = pdu.getStringUtf8();
			list.add(new FileInfo(isFile, name));
		}

		return list;
	}
}
