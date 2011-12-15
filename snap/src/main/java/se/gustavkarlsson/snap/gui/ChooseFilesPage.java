package se.gustavkarlsson.snap.gui;

import java.io.File;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;

import se.gustavkarlsson.snap.gui.filetree.FileNode;
import se.gustavkarlsson.snap.gui.filetree.FileTreeContentProvider;
import se.gustavkarlsson.snap.gui.filetree.FileTreeLabelProvider;
import se.gustavkarlsson.snap.gui.filetree.FolderNode;
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
		fileTreeViewer.setLabelProvider(new FileTreeLabelProvider());
		fileTreeViewer.setContentProvider(new FileTreeContentProvider());
		// TODO Sorter
		fileTreeViewer.setInput(fileTreeRoot);

		// TODO Delete test code
		FolderNode folder1 = new FolderNode("Folder 1.1");
		folder1.addChild(new FolderNode("Folder 2.1"));
		folder1.addChild(new FileNode(new File("C:\\Windows\\win.ini")));
		folder1.addChild(new FolderNode("Folder 2.2"));
		fileTreeRoot.addChild(folder1);
		fileTreeRoot.addChild(new FileNode(new File("C:\\Windows\\win.ini"),
				"'Nother file"));
		// TODO End delete test code
		
		fileTreeViewer.refresh();

		btnEnableAdvancedOptions = new Button(container, SWT.CHECK);
		btnEnableAdvancedOptions.setText("Enable Advanced Options");
	}

	@Override
	public boolean canFlipToNextPage() {
		return (fileTreeRoot.getChildren().size() > 0);
	}

	@Override
	public IWizardPage getNextPage() {
		if (!btnEnableAdvancedOptions.getSelection()) {
			return null; // TODO return Receive page
		}
		return super.getNextPage();
	}
}
