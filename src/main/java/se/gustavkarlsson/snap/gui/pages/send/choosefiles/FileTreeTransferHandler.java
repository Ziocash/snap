package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import se.gustavkarlsson.snap.domain.tree.Tree;

@SuppressWarnings("serial")
public class FileTreeTransferHandler extends TransferHandler {
	private final List<DataFlavor> flavors = new ArrayList<DataFlavor>();
	private DataFlavor nodesFlavor;
	private List<Tree<File>> nodesToRemove;

	public FileTreeTransferHandler() {
		try {
			nodesFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
			flavors.add(nodesFlavor);
			flavors.add(DataFlavor.javaFileListFlavor);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFound: " + e.getMessage());
		}
	}

	@Override
	public boolean canImport(TransferSupport transferSupport) {
		// Make sure transfer type is drop
		if (!transferSupport.isDrop()) {
			return false;
		}

		// Verify that the flavor is supported
		transferSupport.setShowDropLocation(true);
		boolean hasSupportedFlavor = false;
		for (DataFlavor flavor : flavors) {
			if (transferSupport.isDataFlavorSupported(flavor)) {
				hasSupportedFlavor = true;
				break;
			}
		}
		if (!hasSupportedFlavor) {
			return false;
		}

		// Do not allow a drop on the drag source selections.
		JTree.DropLocation dropLocation = (JTree.DropLocation) transferSupport.getDropLocation();
		JTree tree = (JTree) transferSupport.getComponent();
		int dropRow = tree.getRowForPath(dropLocation.getPath());
		int[] selectionRows = tree.getSelectionRows();
		for (int i = 0; i < selectionRows.length; i++) {
			if (selectionRows[i] == dropRow) {
				return false;
			}
		}

		// Do not allow MOVE-action drops if a non-leaf node is
		// selected unless all of its children are also selected.
		int action = transferSupport.getDropAction();
		if (action == MOVE) {
			return haveCompleteNode(tree);
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

	@SuppressWarnings("unchecked")
	@Override
	protected Transferable createTransferable(JComponent component) {
		JTree tree = (JTree) component;
		TreePath[] paths = tree.getSelectionPaths();
		if ((paths == null) || (paths.length == 0)) {
			return null;
		}

		// Make up a node array of copies for transfer and
		// another for/of the nodes that will be removed in
		// exportDone after a successful drop.
		List<Tree<File>> nodesToTransfer = new ArrayList<Tree<File>>();
		Tree<File> firstNode = (Tree<File>) paths[0].getLastPathComponent();
		for (int i = 1; i < paths.length; i++) {
			Tree<File> current = (Tree<File>) paths[i].getLastPathComponent();
			// Do not nodes on other levels than the first one. Underlying nodes will be copied recursively
			if (current.getLevel() == firstNode.getLevel()) {
				nodesToTransfer.add(current);
			}
		}
		return new NodesTransferable(nodesToTransfer);
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
	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
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
		Tree<File>[] nodes = (Tree<File>[]) transferable.getTransferData(nodesFlavor);

		// Get drop location info.
		JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
		int childIndex = dl.getChildIndex();
		TreePath dest = dl.getPath();
		Tree<File> parent = (Tree<File>) dest.getLastPathComponent();
		JTree tree = (JTree) support.getComponent();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		// Configure for drop mode.
		int index = childIndex; // DropMode.INSERT
		if (childIndex == -1) { // DropMode.ON
			index = parent.getChildCount();
		}
		// Add data to model.
		for (int i = 0; i < nodes.length; i++) {
			model.insertNodeInto(nodes[i], parent, index++);
		}
		return true;
	}

	private boolean importFilesData(TransferSupport support) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean haveCompleteNode(JTree tree) {
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
			if (first.isNodeChild(next)) {
				// Found a child of first.
				if (childCount > (selectionRows.length - 1)) {
					// Not all children of first are selected.
					return false;
				}
			}
		}
		return true;
	}

	private class NodesTransferable implements Transferable {
		private Collection<Tree<File>> nodes;

		public Collection<Tree<File>> getNodes() {
			return nodes;
		}

		public NodesTransferable(Collection<Tree<File>> nodes) {
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
			return flavors.toArray(new DataFlavor[flavors.size()]);
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return nodesFlavor.equals(flavor);
		}
	}
}
