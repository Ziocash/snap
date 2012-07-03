package se.gustavkarlsson.snap.gui.pages.general.welcome;

import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.snap.gui.SnapWizardPage;
import se.gustavkarlsson.snap.main.Snap;

public class WelcomePage extends SnapWizardPage {
	private static final long serialVersionUID = 1L;
	
	private static final String CANONICAL_NAME = WelcomePage.class.getCanonicalName();
	
	private static final String TITLE = "Welcome to " + Snap.APP_NAME;
	private static final String DESCRIPTION = "Do you want to save or receive files?";
	
	public static final String SEND_BUTTON_NAME = CANONICAL_NAME + ":sendButton";
	public static final String RECEIVE_BUTTON_NAME = CANONICAL_NAME + ":receiveButton";
	
	private JRadioButton sendButton = null;
	private JRadioButton receiveButton = null;
	
	public WelcomePage() {
		super(TITLE, DESCRIPTION);
		createControls();
		layoutControls();
	}

	private void createControls() {
		sendButton = new JRadioButton("Send", true);
		sendButton.setName(SEND_BUTTON_NAME);
		sendButton.setMnemonic(KeyEvent.VK_S);
		
		receiveButton = new JRadioButton("Receive");
		receiveButton.setName(RECEIVE_BUTTON_NAME);
		receiveButton.setMnemonic(KeyEvent.VK_R);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(sendButton);
		buttonGroup.add(receiveButton);
	}
	
	private void layoutControls() {
		setLayout(new MigLayout());
		
		add(sendButton, "wrap");
		add(receiveButton);
	}
}
