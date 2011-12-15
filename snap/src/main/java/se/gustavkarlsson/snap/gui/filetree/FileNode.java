package se.gustavkarlsson.snap.gui.filetree;

import java.io.File;

import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.resources.Strings;

public class FileNode extends FileFolderNode {

	private File file;
	
	public FileNode(File file) {
		super(file.getName());
		setFile(file);
	}

	public FileNode(File file, String name) {
		super(name);
		setFile(file);
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		if (file == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL + ": file");
		}
		
		this.file = file;
	}
	
	@Override
	public Image getImage() {
		return null; // TODO Return File Image
	}
}
