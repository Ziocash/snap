package se.gustavkarlsson.snap.gui.pages.general.welcome;

<<<<<<< HEAD
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
=======
import java.awt.Color;
>>>>>>> b8c0167336a260717bfa546383138372fe9af2f6
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.AbstractWizardPage;
import se.gustavkarlsson.snap.gui.pages.SnapWizardPage;
<<<<<<< HEAD
import se.gustavkarlsson.snap.gui.pages.send.choosefiles.ChooseFilesPage;
=======
>>>>>>> b8c0167336a260717bfa546383138372fe9af2f6
import se.gustavkarlsson.snap.main.Snap;

@SuppressWarnings("serial")
public class WelcomePage extends SnapWizardPage {

	private static final String TITLE = "Welcome to " + Snap.APP_NAME;
	private static final String DESCRIPTION = "Do you want to save or receive files?";

	private JRadioButton sendButton = null;
	private JRadioButton receiveButton = null;

	public WelcomePage() {
		super(TITLE, DESCRIPTION);
		setupControls();
		layoutControls();
	}

	private void setupControls() {
		sendButton = new JRadioButton("Send", true);
		sendButton.setMnemonic(KeyEvent.VK_S);
		sendButton.addActionListener(new UpdateButtonsListener());

		receiveButton = new JRadioButton("Receive");
		receiveButton.setMnemonic(KeyEvent.VK_R);
		receiveButton.addActionListener(new UpdateButtonsListener());

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(sendButton);
		buttonGroup.add(receiveButton);

		if ((System.currentTimeMillis() % 2) == 0) {
			getContentPanel().setBackground(Color.GREEN);
		}
	}

	private void layoutControls() {
		getContentPanel().setLayout(new MigLayout());
<<<<<<< HEAD

		getContentPanel().add(sendButton, "wrap");
		getContentPanel().add(receiveButton);
	}

	@Override
	protected AbstractWizardPage getNextPage() {
		return sendButton.isSelected() ? new ChooseFilesPage() : null;
	}

	@Override
	protected boolean isCancelAllowed() {
		return true;
=======

		getContentPanel().add(sendButton, "wrap");
		getContentPanel().add(receiveButton);
>>>>>>> b8c0167336a260717bfa546383138372fe9af2f6
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

	private class UpdateButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			updateWizardButtons();
		}

	}
}
