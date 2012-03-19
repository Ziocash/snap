package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tree;

import se.gustavkarlsson.snap.domain.FolderNode;
import se.gustavkarlsson.snap.resources.PropertyManager;
import se.gustavkarlsson.snap.service.persistance.PersistanceManager;
import se.gustavkarlsson.snap.service.session.SessionManager;

public class ChooseFilesPage extends WizardPage {

	private final FolderNode fileTreeRoot = new FolderNode("root");

	private Button enableAdvancedOptionsButton;
	private TreeViewer fileTreeViewer;

	private SessionManager sessionManager;

	/**
	 * Create the wizard.
	 */
	public ChooseFilesPage(SessionManager sessionManager) {
		super(ChooseFilesPage.class.getName());
		setTitle("Choose Files");
		setDescription("Use the \"Browse\" button or by drag and drop from your OS.");

		this.sessionManager = sessionManager;
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

		Group filesGroup = new Group(container, SWT.NONE);
		filesGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));
		filesGroup.setText("Files");
		filesGroup.setLayout(new GridLayout(1, false));

		fileTreeViewer = new TreeViewer(filesGroup, SWT.BORDER | SWT.MULTI);
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
				new FileTreeDropListener(this, fileTreeViewer));

		enableAdvancedOptionsButton = new Button(container, SWT.CHECK);
		enableAdvancedOptionsButton.setText("Enable Advanced Options");
		enableAdvancedOptionsButton.setSelection(PropertyManager.isUsingAdvancedOptions());
		enableAdvancedOptionsButton.addSelectionListener(new EnableAdvancedOptionsAdapter());
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			if (sessionManager.hasCurrentSession()
					&& sessionManager.hasSessionChanged()) {
				PersistanceManager persistanceManager = new PersistanceManager(
						sessionManager.getCurrentSession().getPath());
				fileTreeViewer.setInput(persistanceManager.getRoot());
				sessionManager.setSessionChanged(false);
			}
		}
		super.setVisible(visible);
	}

	@Override
	public boolean isPageComplete() {
		return fileTreeRoot.hasChildren();
	}

	@Override
	public IWizardPage getNextPage() {
		if (!enableAdvancedOptionsButton.getSelection()) {
			return null; // TODO return page
		}
		return super.getNextPage();
	}
	
	private class EnableAdvancedOptionsAdapter extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent event) {
			PropertyManager.setUsingAdvancedOptions(enableAdvancedOptionsButton.getSelection());
		}
	}
}