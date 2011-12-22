package se.gustavkarlsson.snap.tree.filetree;

import java.io.File;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.resources.Images;
import se.gustavkarlsson.snap.resources.Strings;
import se.gustavkarlsson.snap.tree.Label;
import se.gustavkarlsson.snap.tree.LeafNode;

public class FileNode extends LeafNode implements Label {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private File file;

	public FileNode(File file) {
		this(file, file.getName());
	}

	public FileNode(File file, String name) {
		setFile(file);
		setName(name);
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL
					+ ": name");
		}
		this.name = name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		if (file == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL
					+ ": file");
		}

		this.file = file;
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
