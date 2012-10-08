package se.gustavkarlsson.snap.domain.old;

import java.io.File;

public class FileEntry {

	private String name;
	private File file;

	public FileEntry(String name, File file) {
		setName(name);
		this.file = file;
	}

	public FileEntry(String name) {
		this(name, null);
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		if (newName == null) {
			throw new IllegalArgumentException("newName can't be null");
		}
		name = newName;
	}

	public File getFile() {
		return file;
	}

}
