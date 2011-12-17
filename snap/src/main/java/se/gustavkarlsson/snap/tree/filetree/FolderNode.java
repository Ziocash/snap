package se.gustavkarlsson.snap.tree.filetree;

import org.eclipse.swt.graphics.Image;

import se.gustavkarlsson.snap.resources.Strings;
import se.gustavkarlsson.snap.tree.Label;
import se.gustavkarlsson.snap.tree.Node;
import se.gustavkarlsson.snap.tree.Root;

public class FolderNode extends Node implements Label {

	private String name;

	public FolderNode(Root root, String name) {
		super(root);
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

	@Override
	public Image getImage() {
		return null; // TODO Return File Image
	}
}
