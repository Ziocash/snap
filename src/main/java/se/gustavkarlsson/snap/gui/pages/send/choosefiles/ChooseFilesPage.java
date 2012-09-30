package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.AbstractWizardPage;
import se.gustavkarlsson.snap.gui.pages.SnapWizardPage;
import se.gustavkarlsson.snap.gui.pages.send.advancedoptions.AdvancedOptionsPage;

@SuppressWarnings("serial")
public class ChooseFilesPage extends SnapWizardPage {

	private static final String TITLE = "Choose Files";
	private static final String DESCRIPTION = "Use the \"Browse\" button or drag and drop from your OS.";

	private JTree fileTree = null;
	private JCheckBox enableAdvancedOptionsButton = null;

	/**
	 * Create the wizard.
	 */
	public ChooseFilesPage() {
		super(TITLE, DESCRIPTION);
		createControls();
		layoutControls();
	}

	private void createControls() {
		fileTree = new JTree();

		enableAdvancedOptionsButton = new JCheckBox("Enable Advanced Options");
		enableAdvancedOptionsButton.setMnemonic(KeyEvent.VK_A);
	}

	private void layoutControls() {
		getContentPanel().setLayout(new MigLayout("", "[grow]", "[grow][]"));

		JScrollPane treeView = new JScrollPane(fileTree);
		treeView.setBorder(BorderFactory.createLoweredBevelBorder());

		getContentPanel().add(treeView, "grow, wrap");

		getContentPanel().add(enableAdvancedOptionsButton);
	}

	@Override
	protected AbstractWizardPage getNextPage() {
		return new AdvancedOptionsPage();
	}

	@Override
	protected boolean isCompleted() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean canFinish() {
		// TODO Auto-generated method stub
		return false;
	}
}
