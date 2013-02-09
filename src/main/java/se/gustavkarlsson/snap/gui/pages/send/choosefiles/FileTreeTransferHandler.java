package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.TreePath;

import se.gustavkarlsson.snap.domain.tree.FileTree;
import se.gustavkarlsson.snap.domain.tree.Tree;

@SuppressWarnings("serial")
public class FileTreeTransferHandler extends TransferHandler {
	private DataFlavor nodesFlavor;
	private DataFlavor filesFlavor;
	private List<Tree<File>> nodesToRemove;

	public FileTreeTransferHandler() {
		try {
			nodesFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
			filesFlavor = DataFlavor.javaFileListFlavor;
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFound: " + e.getMessage());
		}
	}

	@Override
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Transferable createTransferable(JComponent component) {
		List<Tree<File>> nodesToTransfer = new ArrayList<Tree<File>>();

		JTree tree = (JTree) component;
		TreePath[] paths = tree.getSelectionPaths();
		if (paths == null) {
			return null;
		}

		Tree<File> parent = ((Tree<File>) paths[0].getLastPathComponent()).getParent();
		if (parent == null) {
			return null;
		}
		for (int i = 0; i < paths.length; i++) {
			Tree<File> current = (Tree<File>) paths[i].getLastPathComponent();
			// Only transfer siblings
			if (current.getParent() == parent) {
				nodesToTransfer.add(current);
			}
		}
		if (nodesToTransfer.isEmpty()) {
			return null;
		}
		System.out.println("created transferable with " + nodesToTransfer.size() + " nodes");
		return new NodesTransferable(nodesToTransfer);
	}

	@Override
	public boolean canImport(TransferSupport transferSupport) {
		// Make sure transfer type is drop
		if (!transferSupport.isDrop()) {
			return false;
		}

		// Verify that the flavor is supported
		transferSupport.setShowDropLocation(true);
		if (!transferSupport.isDataFlavorSupported(nodesFlavor) && !transferSupport.isDataFlavorSupported(filesFlavor)) {
			return false;
		}

		// Do not allow a drop on the drag source selections.
		JTree.DropLocation dropLocation = (JTree.DropLocation) transferSupport.getDropLocation();
		JTree tree = (JTree) transferSupport.getComponent();
		int dropRow = tree.getRowForPath(dropLocation.getPath());
		int[] selectionRows = tree.getSelectionRows();
		if (selectionRows != null) {
			for (int i = 0; i < selectionRows.length; i++) {
				if (selectionRows[i] == dropRow) {
					return false;
				}
			}
		}

		// Do not allow MOVE-action drops if a non-leaf node is
		// selected unless all of its children are also selected.
		int action = transferSupport.getDropAction();
		if ((action == MOVE) && (selectionRows != null)) {
			return isCompleteNodeSelected(tree);
		}

		// TODO: Verify if this is needed
		// Do not allow a non-leaf node to be copied to a level
		// which is less than its source level.
		//		TreePath targetPath = dropLocation.getPath();
		//		Tree<File> target = (Tree<File>) targetPath.getLastPathComponent();
		//		TreePath sourcePath = tree.getPathForRow(selectionRows[0]);
		//		Tree<File> firstSource = (Tree<File>) sourcePath.getLastPathComponent();
		//		if ((firstSource.listChildren().size() > 0) && (target.getLevel() < firstSource.getLevel())) {
		//			return false;
		//		}

		return true;
	}

	private boolean isCompleteNodeSelected(JTree tree) {
		int[] selectionRows = tree.getSelectionRows();
		TreePath path = tree.getPathForRow(selectionRows[0]);
		Tree<File> first = (Tree<File>) path.getLastPathComponent();
		int childCount = first.getChildCount();
		// first has children and no children are selected.
		if ((childCount > 0) && (selectionRows.length == 1)) {
			return false;
		}
		// first may have children.
		for (int i = 1; i < selectionRows.length; i++) {
			path = tree.getPathForRow(selectionRows[i]);
			Tree<File> next = (Tree<File>) path.getLastPathComponent();
			if (first.listChildren().contains(next)) {
				// Found a child of first.
				if (childCount > (selectionRows.length - 1)) {
					// Not all children of first are selected.
					return false;
				}
			}
		}
		return true;
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		if ((action & MOVE) == MOVE) {
			Collection<Tree<File>> nodes = ((NodesTransferable) data).getNodes();
			for (Tree<File> node : nodes) {
				node.setParent(null);
			}
		}
	}

	@Override
	public boolean importData(TransferSupport support) {
		if (!canImport(support)) {
			return false;
		}
		// Extract transfer data.
		if (support.isDataFlavorSupported(nodesFlavor)) {
			return importNodesData(support);
		} else if (support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			return importFilesData(support);
		}

		return false;
	}

	private boolean importNodesData(TransferSupport support) {
		Transferable transferable = support.getTransferable();
		List<Tree<File>> nodes = null;
		try {
			nodes = (List<Tree<File>>) transferable.getTransferData(nodesFlavor);
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get drop location info.
		JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
		TreePath targetPath = dl.getPath();
		if (targetPath == null) {
			JTree tree = (JTree) support.getComponent();
			Tree<File> root = (Tree<File>) tree.getModel().getRoot();
			for (Tree<File> node : nodes) {
				node.setParent(root);
			}
			return true;
		}
		Tree<File> parent = (Tree<File>) dl.getPath().getLastPathComponent();
		for (Tree<File> node : nodes) {
			node.setParent(parent);
		}
		return true;
	}

	private boolean importFilesData(TransferSupport support) {
		Transferable transferable = support.getTransferable();
		List<File> files = null;
		try {
			files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get drop location info.
		JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
		TreePath targetPath = dl.getPath();
		if (targetPath == null) {
			JTree tree = (JTree) support.getComponent();
			Tree<File> root = (Tree<File>) tree.getModel().getRoot();
			for (File file : files) {
				Tree<File> fileTree = new FileTree(file);
				fileTree.setParent(root);
			}
			return true;
		}
		Tree<File> parent = (Tree<File>) dl.getPath().getLastPathComponent();
		for (File file : files) {
			Tree<File> fileTree = new FileTree(file);
			fileTree.setParent(parent);
		}
		return true;
	}

	private class NodesTransferable implements Transferable {
		private List<Tree<File>> nodes;

		public List<Tree<File>> getNodes() {
			return nodes;
		}

		public NodesTransferable(List<Tree<File>> nodes) {
			this.nodes = nodes;
		}

		@Override
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
			if (!isDataFlavorSupported(flavor)) {
				throw new UnsupportedFlavorException(flavor);
			}
			return nodes;
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] {nodesFlavor, filesFlavor};
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return nodesFlavor.equals(flavor);
		}
	}
}
