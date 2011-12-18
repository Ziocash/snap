package se.gustavkarlsson.snap.tree;

import java.io.Serializable;

public interface Parent extends Serializable {

	LeafNode[] listChildren();

	boolean addChild(LeafNode child);

	boolean removeChild(LeafNode child);

	boolean hasChildren();
}
