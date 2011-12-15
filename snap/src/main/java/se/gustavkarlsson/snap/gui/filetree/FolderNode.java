package se.gustavkarlsson.snap.gui.filetree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

public class FolderNode extends FileFolderNode {
	
	private final ArrayList<FileFolderNode> children = new ArrayList<FileFolderNode>();

	public FolderNode(String name) {
		super(name);
	}
	
	public List<FileFolderNode> getChildren() {
		return children;
	}
	
	public void addChild(FileFolderNode child) {
		child.setParent(this);
		children.add(child);
	}
	
	@Override
	protected void remove() {
		super.remove();
		
		while (!children.isEmpty()) {
			int index = children.size() - 1;
			FileFolderNode child = children.get(index);
			
			if (child instanceof FolderNode) {
				((FolderNode) child).remove();
			} else {
				child.remove();
			}
			
			children.remove(index);
		}
		children.trimToSize();
	}
	
	@Override
	public Image getImage() {
		return null; // TODO Return File Image
	}
}
