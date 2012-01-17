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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

import se.gustavkarlsson.snap.domain.FolderNode;
import se.gustavkarlsson.snap.gui.util.filetree.FileComparator;
import se.gustavkarlsson.snap.gui.util.filetree.FileTreeDropAdapter;
import se.gustavkarlsson.snap.gui.util.filetree.FileViewerComparator;
import se.gustavkarlsson.snap.gui.util.filetree.TreeContentProvider;
import se.gustavkarlsson.snap.gui.util.filetree.TreeLabelProvider;
import se.gustavkarlsson.snap.resources.Strings;

public class ChooseFilesPage extends WizardPage {

	private final FolderNode fileTreeRoot = new FolderNode("root");

	private Button btnEnableAdvancedOptions;
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

		Label lblChooseFiles = new Label(container, SWT.NONE);
		lblChooseFiles.setText("Files");

		fileTreeViewer = new TreeViewer(container, SWT.BORDER | SWT.MULTI);
		Tree fileTree = fileTreeViewer.getTree();
		fileTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));
		fileTreeViewer.setLabelProvider(new TreeLabelProvider());
		fileTreeViewer.setContentProvider(new TreeContentProvider());
		fileTreeViewer.setComparator(new FileViewerComparator(
				new FileComparator()));
		fileTreeViewer.setInput(fileTreeRoot);

		int operations = DND.DROP_MOVE;
		Transfer[] transfers = new Transfer[] { FileTransfer.getInstance() };
		fileTreeViewer.addDropSupport(operations, transfers,
				new FileTreeDropAdapter(fileTreeViewer));

		btnEnableAdvancedOptions = new Button(container, SWT.CHECK);
		btnEnableAdvancedOptions.setText("Enable Advanced Options");
	}

	@Override
	public boolean canFlipToNextPage() {
		return fileTreeRoot.hasChildren(); // TODO needs update when modifying tree
	}

	@Override
	public IWizardPage getNextPage() {
		if (!btnEnableAdvancedOptions.getSelection()) {
			return null; // TODO return Receive page
		}
		return super.getNextPage();
	}
}
