package se.gustavkarlsson.snap.filetree;

import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.resources.Directories;
import se.gustavkarlsson.snap.resources.Images;
import se.gustavkarlsson.snap.resources.Strings;

public class FileNode extends Node {

	private final String path;

	public FileNode(String path) {
		this(path, path
				.substring(path.lastIndexOf(Directories.FILE_SEPARATOR) + 1)); // TODO FILE_SEPARATOR really?
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

	@Override
	public Image getImage() {
		return Images.FILE;
	}
}
