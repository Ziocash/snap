package se.gustavkarlsson.snap.files;

import se.gustavkarlsson.snap.resources.Strings;

public class FileInfo {

	private final boolean isFile;
	private final String name;

	public FileInfo(boolean isFile, String name) {
		if (name == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL);
		}

		this.isFile = isFile;
		this.name = name;
	}

	public boolean isFile() {
		return isFile;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		String type = isFile ? "File" : "Directory";
		return type + " " + name;
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof FileInfo) {
			FileInfo that = (FileInfo) other;
			result = (isFile == that.isFile) && (name.equals(that.name));
		}
		return result;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}
}
