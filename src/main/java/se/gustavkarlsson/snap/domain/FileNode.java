package se.gustavkarlsson.snap.domain;

import se.gustavkarlsson.snap.resources.Strings;
import se.gustavkarlsson.snap.util.FileUtils;

public class FileNode extends Node {
	
	private static final long serialVersionUID = 1L;
	private final String path;

	public FileNode(String path) {
		this(path, FileUtils.extractFileNameFromPath(path));
	}

	public FileNode(String path, String name) {
		super(name);
		if (path == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL
					+ ": path");
		}
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
