package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import javax.swing.JScrollPane;
import javax.swing.JTree;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.AbstractWizardPage;
import se.gustavkarlsson.snap.gui.pages.SnapWizardPage;
import se.gustavkarlsson.snap.main.Snap;

@SuppressWarnings("serial")
public class ChooseFilesPage extends SnapWizardPage{

	private static final String TITLE = "Welcome to " + Snap.APP_NAME;
	private static final String DESCRIPTION = "Do you want to save or receive files?";

	private JTree tree;
	private JScrollPane scrollPane;

	public ChooseFilesPage() {
		super(TITLE, DESCRIPTION);
		setupControls();
		layoutControls();
	}

	private void setupControls() {
		tree = new JTree();
		scrollPane = new JScrollPane(tree);
	}

	private void layoutControls() {
		getPageContentPanel().setLayout(new MigLayout());

		getPageContentPanel().add(scrollPane, "width 100%, height 100%");
	}

	@Override
	protected AbstractWizardPage getNextPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isCancelAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isPreviousAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isNextAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isFinishAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

}
