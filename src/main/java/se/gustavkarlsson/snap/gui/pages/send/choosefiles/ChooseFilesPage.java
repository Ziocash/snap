package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.io.File;

import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.AbstractWizardPage;
import se.gustavkarlsson.snap.domain.SenderSettings;
import se.gustavkarlsson.snap.gui.listeners.UpdateWizardButtonsActionListener;
import se.gustavkarlsson.snap.gui.listeners.UpdateWizardButtonsListDataListener;
import se.gustavkarlsson.snap.gui.pages.SnapWizardPage;
import se.gustavkarlsson.snap.gui.pages.send.advancedoptions.AdvancedOptionsPage;

@SuppressWarnings("serial")
public class ChooseFilesPage extends SnapWizardPage{

	private static final String TITLE = "Choose what files and folders to send";
	private static final String DESCRIPTION = "You can also drag and drop from your desktop.";

	private SenderSettings settings;

	private JList fileList;
	private JScrollPane fileListScrollPane;
	private JRadioButton advancedOptionsRadioButton;

	private AbstractWizardPage advancedOptionsPage;

	public ChooseFilesPage(SenderSettings settings) {
		super(TITLE, DESCRIPTION);
		this.settings = settings;

		advancedOptionsPage = new AdvancedOptionsPage(settings);

		setupControls();
		layoutControls();
	}

	private void setupControls() {
		fileList = new JList();
		fileList.setModel(new SetListModel<File>(settings.getFilesToSend()));
		fileList.setDragEnabled(true);
		fileList.setCellRenderer(new FileRenderer());
		fileList.setTransferHandler(new FileTransferHandler());
		fileList.getModel().addListDataListener(new UpdateWizardButtonsListDataListener(this));

		fileListScrollPane = new JScrollPane(fileList);

		advancedOptionsRadioButton = new JRadioButton("Use advanced options", false);
		advancedOptionsRadioButton.addActionListener(new UpdateWizardButtonsActionListener(this));
	}

	private void layoutControls() {
		getPageContentPanel().setLayout(new MigLayout());

		getPageContentPanel().add(fileListScrollPane, "width 100%, height 100%, wrap");
		getPageContentPanel().add(advancedOptionsRadioButton);
	}

	@Override
	protected AbstractWizardPage getNextPage() {
		if (advancedOptionsRadioButton.isSelected()) {
			return advancedOptionsPage;
		} else {
			return null;
		}
	}

	@Override
	protected boolean isCancelAllowed() {
		return true;
	}

	@Override
	protected boolean isPreviousAllowed() {
		return true;
	}

	@Override
	protected boolean isNextAllowed() {
		return !settings.getFilesToSend().isEmpty();
	}

	@Override
	protected boolean isFinishAllowed() {
		return false;
	}

}
