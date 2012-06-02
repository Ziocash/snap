package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.io.File;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;

import se.gustavkarlsson.snap.domain.FileNode;
import se.gustavkarlsson.snap.domain.FolderNode;
import se.gustavkarlsson.snap.domain.Node;
import se.gustavkarlsson.snap.util.FileUtils;

public class FileTreeDropListener extends ViewerDropAdapter {

	public FileTreeDropListener(TreeViewer viewer) {
		super(viewer);
	}

	@Override
	public boolean validateDrop(Object target, int op, TransferData type) {
		switch (getCurrentLocation()) {
		case LOCATION_BEFORE:
			return true;
		case LOCATION_ON:
			return (target instanceof FolderNode);
		case LOCATION_AFTER:
			return true;
		case LOCATION_NONE:
			// Root
			return true;
		default:
			// TODO Error message
			return false;
		}
	}

	@Override
	public boolean performDrop(Object data) {
		FolderNode targetParent;
		switch (getCurrentLocation()) {
		case LOCATION_BEFORE:
			targetParent = ((Node) getCurrentTarget()).getParent();
			break;
		case LOCATION_ON:
			targetParent = (FolderNode) getCurrentTarget();
			break;
		case LOCATION_AFTER:
			targetParent = ((Node) getCurrentTarget()).getParent();
			break;
		case LOCATION_NONE:
			// Root
			targetParent = (FolderNode) getViewer().getInput();
			break;
		default:
			// TODO Error message
			return false;
		}

		if (data instanceof String[]) {
			String[] filePaths = (String[]) data;

			for (String filePath : filePaths) {
				addFileTreeFromPath(targetParent, filePath);
			}
		} else if (data instanceof Object[]) {
			for (Object object : (Object[]) data) {
				Node node = (Node) object;
				targetParent.addChild(node);
			}
		} else {
			// TODO error message
			return false;
		}
		
		getViewer().refresh();
		return true;
	}

	private static void addFileTreeFromPath(FolderNode parent, String filePath) {
		File file = new File(filePath);

		if (file.isFile()) {
			parent.addChild(new FileNode(filePath));
		} else {
			FolderNode folder = new FolderNode(
					FileUtils.extractFileNameFromPath(filePath));
			if (file.listFiles() == null) {
				// TODO Error message (could not list)
			} else {
				for (File child : file.listFiles()) {
					addFileTreeFromPath(folder, child.getAbsolutePath());
				}
			}
			parent.addChild(folder);
		}
	}
}
