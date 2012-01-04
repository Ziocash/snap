package se.gustavkarlsson.snap.filetree;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.resources.Directories;
import se.gustavkarlsson.snap.resources.Images;
import se.gustavkarlsson.snap.resources.Strings;

@Entity(name = "Files")
public class FileNode extends Node {

	@SuppressWarnings("unused")
	@Column(name = "FileID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "Path", nullable = false)
	private String path;

	public FileNode() {
	}

	public FileNode(String path) {
		this(path, path
				.substring(path.lastIndexOf(Directories.FILE_SEPARATOR) + 1)); // TODO
																				// FILE_SEPARATOR
																				// really?
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
