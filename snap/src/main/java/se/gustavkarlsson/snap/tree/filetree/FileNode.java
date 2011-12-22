package se.gustavkarlsson.snap.tree.filetree;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.resources.Directories;
import se.gustavkarlsson.snap.resources.Images;
import se.gustavkarlsson.snap.resources.Strings;
import se.gustavkarlsson.snap.tree.Label;
import se.gustavkarlsson.snap.tree.LeafNode;

public class FileNode extends LeafNode implements Label {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final String path;

	public FileNode(String path) {
		this(path, path.substring(path.lastIndexOf(Directories.FILE_SEPARATOR) + 1));
	}

	public FileNode(String path, String name) {
		if (name == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL
					+ ": name");
		}
		if (path == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL
					+ ": path");
		}

		this.path = path;
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	@Override
	public Image getImage() {
		return Images.FILE;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(name).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		if (Label.class.isAssignableFrom(obj.getClass())) {
			Label other = (Label) obj;
			return name.equals(other.getName());
		}
		return false;
	}
}
