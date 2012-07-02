package se.gustavkarlsson.snap.domain;

import se.gustavkarlsson.snap.resources.Strings;
import se.gustavkarlsson.snap.util.FileUtils;

public class FileNode extends Node {
	
	private String fileSystemPath;

	public FileNode(String path) {
		this(path, FileUtils.extractFileNameFromPath(path));
	}

	public FileNode(String path, String name) {
		super(name);
		if (path == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL
					+ ": path");
		}
		this.fileSystemPath = path;
	}

	public String getFileSystemPath() {
		return fileSystemPath;
	}
	
	@Override
	public FileNode clone() {
		FileNode clone = (FileNode) super.clone();
		clone.fileSystemPath = fileSystemPath;
		return clone;
	}
}
