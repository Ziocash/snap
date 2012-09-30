package se.gustavkarlsson.snap.gui.pages.general.welcome;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.AbstractWizardPage;
import se.gustavkarlsson.snap.gui.pages.SnapWizardPage;
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

		receiveButton = new JRadioButton("Receive");
		receiveButton.setMnemonic(KeyEvent.VK_R);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(sendButton);
		buttonGroup.add(receiveButton);

		if ((System.currentTimeMillis() % 2) == 0) {
			getContentPanel().setBackground(Color.GREEN);
		}
	}

	private void layoutControls() {
		getContentPanel().setLayout(new MigLayout());

		getContentPanel().add(sendButton, "wrap");
		getContentPanel().add(receiveButton);
	}

	@Override
	protected AbstractWizardPage getNextPage() {
		// TODO Auto-generated method stub
		return new WelcomePage();
	}

	@Override
	protected boolean isCompleted() {
		return sendButton.isSelected() || receiveButton.isSelected();
	}

	@Override
	protected boolean canFinish() {
		// TODO Auto-generated method stub
		return false;
	}
}
