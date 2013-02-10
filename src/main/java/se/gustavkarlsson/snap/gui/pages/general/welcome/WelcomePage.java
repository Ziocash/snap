package se.gustavkarlsson.snap.gui.pages.general.welcome;

import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.AbstractWizardPage;
import se.gustavkarlsson.snap.domain.SenderSettings;
import se.gustavkarlsson.snap.gui.listeners.UpdateWizardButtonsActionListener;
import se.gustavkarlsson.snap.gui.pages.SnapWizardPage;
import se.gustavkarlsson.snap.gui.pages.send.choosefiles.ChooseFilesPage;
import se.gustavkarlsson.snap.main.Snap;

@SuppressWarnings("serial")
public class WelcomePage extends SnapWizardPage {

	private static final String TITLE = "Welcome to " + Snap.APP_NAME;
	private static final String DESCRIPTION = "Do you want to save or receive files?";

	private JRadioButton sendButton = null;
	private JRadioButton receiveButton = null;

	private AbstractWizardPage chooseFilesPage = new ChooseFilesPage(new SenderSettings());

	public WelcomePage() {
		super(TITLE, DESCRIPTION);
		setupControls();
		layoutControls();
	}

	private void setupControls() {
		sendButton = new JRadioButton("Send", true);
		sendButton.setMnemonic(KeyEvent.VK_S);
		sendButton.addActionListener(new UpdateWizardButtonsActionListener(this));

		receiveButton = new JRadioButton("Receive");
		receiveButton.setMnemonic(KeyEvent.VK_R);
		receiveButton.addActionListener(new UpdateWizardButtonsActionListener(this));

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(sendButton);
		buttonGroup.add(receiveButton);
	}

	private void layoutControls() {
		getPageContentPanel().setLayout(new MigLayout());

		getPageContentPanel().add(sendButton, "wrap");
		getPageContentPanel().add(receiveButton);
	}

	@Override
	protected AbstractWizardPage getNextPage() {
		return sendButton.isSelected() ? chooseFilesPage : null;
	}

	@Override
	protected boolean isCancelAllowed() {
		return true;
	}

	@Override
	protected boolean isPreviousAllowed() {
		return false;
	}

	@Override
	protected boolean isNextAllowed() {
		return sendButton.isSelected() || receiveButton.isSelected();
	}

	@Override
	protected boolean isFinishAllowed() {
		return false;
	}
}
