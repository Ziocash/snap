package se.gustavkarlsson.snap.gui.pages.send.choosefiles.old;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class InternalFileDndPayload implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private final boolean copyAction;
	private final Set<List<String>> nodesPaths = new HashSet<List<String>>();
	
	public InternalFileDndPayload(Collection<List<String>> nodes, boolean copyAction) {
		this.nodesPaths.addAll(nodes);
		this.copyAction = copyAction;
	}
	
	public boolean isCopyAction() {
		return copyAction;
	}
	
	public Set<List<String>> getNodes() {
		return Collections.unmodifiableSet(nodesPaths);
	}
	
}
