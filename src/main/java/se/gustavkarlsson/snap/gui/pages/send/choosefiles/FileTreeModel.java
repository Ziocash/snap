package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import se.gustavkarlsson.snap.domain.tree.FileTreeRoot;
import se.gustavkarlsson.snap.domain.tree.Tree;

public class FileTreeModel implements TreeModel {

	private final Set<TreeModelListener> treeModelListeners = new HashSet<TreeModelListener>();
	private FileTreeRoot root;

	public FileTreeModel(FileTreeRoot root) {
		this.root = root;
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		//TODO handle unsafe cast
		Tree<File> parentTree = (Tree<File>) parent;
		return parentTree.listChildren().get(index);
	}

	@Override
	public int getChildCount(Object parent) {
		//TODO handle unsafe cast
		Tree<File> parentTree = (Tree<File>) parent;
		return parentTree.getChildCount();
	}

	@Override
	public boolean isLeaf(Object node) {
		//TODO handle unsafe cast
		Tree<File> treeNode = (Tree<File>) node;
		return !treeNode.hasChildren();
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		if (path.getLastPathComponent() == newValue) {
			throw new AssertionError("Unexpected change detected.");
		}
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		//TODO handle unsafe cast
		Tree<File> parentTree = (Tree<File>) parent;
		return parentTree.listChildren().indexOf(child);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		treeModelListeners.add(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		treeModelListeners.remove(l);
	}

}
