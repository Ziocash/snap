package se.gustavkarlsson.snap.gui.pages.general.welcome;

import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;
import se.gustavkarlsson.gwiz.AbstractWizardPage;
import se.gustavkarlsson.snap.main.Snap;

@SuppressWarnings("serial")
public class WelcomePage extends AbstractWizardPage {

	private static final String TITLE = "Welcome to " + Snap.APP_NAME;
	private static final String DESCRIPTION = "Do you want to save or receive files?";

	private JLabel titleLabel = null;
	private JLabel descriptionLabel = null;
	private JRadioButton sendButton = null;
	private JRadioButton receiveButton = null;

	public WelcomePage() {
		setupControls();
		layoutControls();
	}

	private void setupControls() {
		titleLabel = new JLabel(TITLE);
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

		descriptionLabel = new JLabel(DESCRIPTION);
		descriptionLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 10));

		sendButton = new JRadioButton("Send", true);
		sendButton.setMnemonic(KeyEvent.VK_S);

		receiveButton = new JRadioButton("Receive");
		receiveButton.setMnemonic(KeyEvent.VK_R);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(sendButton);
		buttonGroup.add(receiveButton);
	}

	private void layoutControls() {
		setLayout(new MigLayout("wrap 1", "[grow]"));

		add(titleLabel, "align center");
		add(descriptionLabel, "align center");

		JPanel buttonsPanel = new JPanel(new MigLayout());
		buttonsPanel.add(sendButton);
		buttonsPanel.add(receiveButton);

		add(buttonsPanel, "align center");
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
