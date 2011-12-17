package se.gustavkarlsson.snap.tree.filetree;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.resources.Images;
import se.gustavkarlsson.snap.resources.Strings;
import se.gustavkarlsson.snap.tree.Label;
import se.gustavkarlsson.snap.tree.LeafNode;
import se.gustavkarlsson.snap.tree.Root;

public class FileNode extends LeafNode implements Label {

	private String name;
	private File file;

	public FileNode(Root root, File file) {
		this(root, file, file.getName());
	}

	public FileNode(Root root, File file, String name) {
		super(root);
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
}
