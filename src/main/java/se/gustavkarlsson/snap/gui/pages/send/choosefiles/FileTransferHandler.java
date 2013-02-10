package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JList;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public class FileTransferHandler extends TransferHandler {

	@Override
	public boolean canImport(TransferSupport support) {
		// we only import FileList
		if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean importData(TransferSupport support) {
		if (!canImport(support)) {
			return false;
		}

		// Get the fileList that is being dropped.
		Transferable transferable = support.getTransferable();
		List<File> data;
		try {
			data = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		SetListModel<File> model = (SetListModel<File>) ((JList) support.getComponent()).getModel();
		for (File file : data) {
			if (!model.contains(file)) {
				model.add(file);
			}
		}
		return true;
	}
}
