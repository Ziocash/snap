package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

import se.gustavkarlsson.snap.domain.Node;

public class InternalFileDragListener extends DragSourceAdapter {
	private final StructuredViewer viewer;

	public InternalFileDragListener(StructuredViewer viewer) {
		this.viewer = viewer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void dragFinished(DragSourceEvent event) {
		if (!event.doit) {
			return;
		}
		
		// if the Node was moved, remove it from the source viewer
		if ((event.detail & DND.DROP_MOVE) == DND.DROP_MOVE) {
			IStructuredSelection fileTreeSelection = (IStructuredSelection) viewer
					.getSelection();

			for (Node selectedNode : (List<Node>) fileTreeSelection.toList()) {
				selectedNode.getParent().removeChild(selectedNode);
			}
			
			viewer.refresh();
		}
	}

	/**
	 * Method declared on DragSourceListener
	 */

	@Override
	public void dragSetData(DragSourceEvent event) {
		IStructuredSelection fileTreeSelection = (IStructuredSelection) viewer
				.getSelection();
		
		if (InternalFileTransfer.getInstance().isSupportedType(event.dataType)) {
			// TODO remove nodes that cannot be moved/copied
			event.data = fileTreeSelection.toArray();
		}
	}

	/**
	 * Method declared on DragSourceListener
	 */
	@Override
	public void dragStart(DragSourceEvent event) {
		event.doit = !viewer.getSelection().isEmpty();
	}
}
