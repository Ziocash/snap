package se.gustavkarlsson.snap.domain.tree;

import java.io.File;

public class FileTreeRoot extends Tree<File> {

	public FileTreeRoot() {
		super(null);
	}

	@Override
	protected boolean isChildrenAllowed() {
		return true;
	}

	@Override
	public String toString() {
		return "root";
	}
}
