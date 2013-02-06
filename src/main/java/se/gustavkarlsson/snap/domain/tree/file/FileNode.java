package se.gustavkarlsson.snap.domain.tree.file;

import java.io.File;

import se.gustavkarlsson.snap.domain.tree.Node;

public class FileNode extends Node<File> {

	public FileNode(File file) {
		super(file);
	}

	@Override
	public boolean isChildrenAllowed() {
		return isFolder();
	}

	public boolean isFile() {
		return getValue() != null;
	}

	public boolean isFolder() {
		return getValue() == null;
	}

}
