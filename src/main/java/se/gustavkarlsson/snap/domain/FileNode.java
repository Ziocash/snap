package se.gustavkarlsson.snap.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.resources.Images;
import se.gustavkarlsson.snap.resources.Strings;
import se.gustavkarlsson.snap.util.FileUtils;

@Entity(name = "Files")
public class FileNode extends Node {

	@Column(name = "FileID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "Path", nullable = false)
	private String path;

	public FileNode() {
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public Image getImage() {
		return Images.FILE;
	}
}
