package se.gustavkarlsson.snap.tree.filetree;

import java.io.File;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;

import se.gustavkarlsson.snap.resources.Directories;
import se.gustavkarlsson.snap.tree.LeafNode;
import se.gustavkarlsson.snap.tree.Parent;

public class FileTreeDropAdapter extends ViewerDropAdapter {

	public FileTreeDropAdapter(TreeViewer viewer) {
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
		Parent targetParent;
		switch (getCurrentLocation()) {
		case LOCATION_BEFORE:
			targetParent = ((LeafNode) getCurrentTarget()).getParent();
			break;
		case LOCATION_ON:
			targetParent = (Parent) getCurrentTarget();
			break;
		case LOCATION_AFTER:
			targetParent = ((LeafNode) getCurrentTarget()).getParent();
			break;
		case LOCATION_NONE:
			// Root
			targetParent = (Parent) getViewer().getInput();
			break;
		default:
			// TODO Error message
			return false;
		}

		String[] filePaths = (String[]) data;
		
		
		for (String filePath : filePaths) {
			addFileRecursively(targetParent, filePath);
		}
		getViewer().refresh();
		return true;
	}
	
	private static void addFileRecursively(Parent parent, String filePath) {
		File file = new File(filePath);
		
		if (file.isFile()) {
			parent.addChild(new FileNode(filePath));
		} else {
			FolderNode folder = new FolderNode(filePath.substring(filePath.lastIndexOf(Directories.FILE_SEPARATOR) + 1));
			if (file.listFiles() == null) {
				// TODO Error message (could not list)
			} else {
				for (File child : file.listFiles()) {
					addFileRecursively(folder, child.getAbsolutePath());
				}
			}
			parent.addChild(folder);
		}
	}
}
