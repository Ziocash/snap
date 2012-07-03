package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JTree;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.snap.gui.SnapWizardPage;

public class ChooseFilesPage extends SnapWizardPage {
	private static final long serialVersionUID = 1L;
	
	private static final String CANONICAL_NAME = ChooseFilesPage.class.getCanonicalName();
	
	private static final String TITLE = "Choose Files";
	private static final String DESCRIPTION = "Use the \"Browse\" button or drag and drop from your OS.";

	public static final String FILE_TREE_NAME = CANONICAL_NAME + ":fileTree";
	public static final String ENABLE_ADVANCED_OPTIONS_BUTTON_NAME = CANONICAL_NAME + ":enableAdvancedOptionsButton";

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
		fileTree.setName(FILE_TREE_NAME);
		fileTree.setBorder(BorderFactory.createLoweredBevelBorder());
		
		enableAdvancedOptionsButton = new JCheckBox("Enable Advanced Options");
		enableAdvancedOptionsButton.setName(ENABLE_ADVANCED_OPTIONS_BUTTON_NAME);
		enableAdvancedOptionsButton.setMnemonic(KeyEvent.VK_A);
	}

	private void layoutControls() {
		setLayout(new MigLayout("", "[grow]", "[grow][]"));
		add(fileTree, "grow, wrap");
		
		add(enableAdvancedOptionsButton);
	}
}
