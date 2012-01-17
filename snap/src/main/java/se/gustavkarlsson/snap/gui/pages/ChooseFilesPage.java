package se.gustavkarlsson.snap.gui.pages;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

import se.gustavkarlsson.snap.domain.FolderNode;
import se.gustavkarlsson.snap.gui.util.FileTreeDropAdapter;
import se.gustavkarlsson.snap.gui.util.filetree.FileNameComparator;
import se.gustavkarlsson.snap.gui.util.filetree.FileTreeContentProvider;
import se.gustavkarlsson.snap.gui.util.filetree.FileTreeLabelProvider;
import se.gustavkarlsson.snap.gui.util.filetree.FileTreeViewerComparator;
import se.gustavkarlsson.snap.resources.Strings;

public class ChooseFilesPage extends WizardPage {

	private final FolderNode fileTreeRoot = new FolderNode("root");

	private Button enableAdvancedOptionsButton;
	private TreeViewer fileTreeViewer;

	/**
	 * Create the wizard.
	 */
	public ChooseFilesPage() {
		super(ChooseFilesPage.class.getName());
		setTitle(Strings.CHOOSE_FILES_PAGE_TITLE);
		setDescription(Strings.CHOOSE_FILES_PAGE_DESCRIPTION);
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		fileTreeViewer = new TreeViewer(container, SWT.BORDER | SWT.MULTI);
		Tree fileTree = fileTreeViewer.getTree();
		fileTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));
		fileTreeViewer.setLabelProvider(new FileTreeLabelProvider());
		fileTreeViewer.setContentProvider(new FileTreeContentProvider());
		fileTreeViewer.setComparator(new FileTreeViewerComparator(
				new FileNameComparator()));
		fileTreeViewer.setInput(fileTreeRoot);

		int operations = DND.DROP_MOVE;
		Transfer[] transfers = new Transfer[] { FileTransfer.getInstance() };
		fileTreeViewer.addDropSupport(operations, transfers,
				new FileTreeDropAdapter(fileTreeViewer));

		enableAdvancedOptionsButton = new Button(container, SWT.CHECK);
		enableAdvancedOptionsButton.setText("Enable Advanced Options");
	}

	@Override
	public boolean canFlipToNextPage() {
		return fileTreeRoot.hasChildren(); // TODO needs update when modifying
											// tree
	}

	@Override
	public IWizardPage getNextPage() {
		if (!enableAdvancedOptionsButton.getSelection()) {
			return null; // TODO return Receive page
		}
		return super.getNextPage();
	}
}
