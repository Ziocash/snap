package se.gustavkarlsson.snap.tree;

interface Parent {

	LeafNode[] listChildren();

	void addChild(LeafNode child);

	void removeChild(LeafNode child);
	
	boolean hasChildren();
}
