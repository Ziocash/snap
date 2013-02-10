package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.io.File;

import javax.swing.JList;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.AbstractWizardPage;
import se.gustavkarlsson.snap.domain.SenderSettings;
import se.gustavkarlsson.snap.gui.pages.SnapWizardPage;
import se.gustavkarlsson.snap.main.Snap;

@SuppressWarnings("serial")
public class ChooseFilesPage extends SnapWizardPage{

	private static final String TITLE = "Welcome to " + Snap.APP_NAME;
	private static final String DESCRIPTION = "Do you want to save or receive files?";

	private SenderSettings settings;

	private JList fileList;
	private JScrollPane scrollPane;

	public ChooseFilesPage(SenderSettings settings) {
		super(TITLE, DESCRIPTION);
		this.settings = settings;

		setupControls();
		layoutControls();
	}

	private void setupControls() {
		fileList = new JList();
		fileList.setModel(new SetListModel<File>());
		fileList.setDragEnabled(true);
		fileList.setCellRenderer(new FileRenderer());
		fileList.setTransferHandler(new FileTransferHandler());

		scrollPane = new JScrollPane(fileList);
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
