package se.gustavkarlsson.snap.gui.filetree;

import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.resources.Strings;

public abstract class FileFolderNode {
	
	private String name;
	private FolderNode parent;
	
	public FileFolderNode(String name) {
		setName(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException(Strings.ARGUMENT_IS_NULL + ": name");
		}
		this.name = name;
	}
	
	public FolderNode getParent() {
		return parent;
	}
	
	protected void setParent(FolderNode parent) {
		this.parent = parent;
	}
	
	protected void remove() {
		if (parent != null) {
			parent.getChildren().remove(this);
		}
		parent = null;
	}
	
	public abstract Image getImage();
}
