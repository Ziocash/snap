package se.gustavkarlsson.snap.domain.tree;

import java.io.File;

public class FileTree extends Tree<File> {

	public FileTree(File fileNode) {
		super(fileNode);

		if (fileNode == null) {
			throw new IllegalArgumentException("fileNode can't be null");
		}

		if (value.isDirectory()) {
			File[] children = value.listFiles();
			for (File child : children) {
				FileTree childTree = new FileTree(child);
				childTree.setParent(this);
			}
		}
	}

	@Override
	protected boolean isChildrenAllowed() {
		return value.isDirectory();
	}

	@Override
	public String toString() {
		return value.getName();
	}

}
