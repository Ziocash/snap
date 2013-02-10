package se.gustavkarlsson.snap.domain.tree;

import java.io.File;

public class FileTree extends Tree<File> implements Comparable<FileTree> {

	public FileTree(File file) {
		super(file);

		if (file == null) {
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
	public int compareTo(FileTree other) {
		if (other == null) {
			return 1;
		}
		if (this.getValue().isDirectory() && other.getValue().isFile()) {
			return 1;
		}
		if (this.getValue().isFile() && other.getValue().isDirectory()) {
			return -1;
		}

		return this.getValue().compareTo(other.getValue());
	}

}
