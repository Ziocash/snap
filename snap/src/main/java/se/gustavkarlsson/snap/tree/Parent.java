package se.gustavkarlsson.snap.tree;

import java.io.Serializable;

interface Parent extends Serializable {

	LeafNode[] listChildren();

	void addChild(LeafNode child);

	void removeChild(LeafNode child);

	boolean hasChildren();
}
