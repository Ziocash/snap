package se.gustavkarlsson.snap.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class StartPage extends WizardPage {

	public StartPage() {
		initialize();

	}

	private void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JRadioButton rdbtnSend = new JRadioButton("Send");
		GridBagConstraints gbc_rdbtnSend = new GridBagConstraints();
		gbc_rdbtnSend.anchor = GridBagConstraints.WEST;
		gbc_rdbtnSend.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnSend.gridx = 0;
		gbc_rdbtnSend.gridy = 0;
		add(rdbtnSend, gbc_rdbtnSend);

		JRadioButton rdbtnReceive = new JRadioButton("Receive");
		GridBagConstraints gbc_rdbtnReceive = new GridBagConstraints();
		gbc_rdbtnReceive.anchor = GridBagConstraints.WEST;
		gbc_rdbtnReceive.gridx = 0;
		gbc_rdbtnReceive.gridy = 1;
		add(rdbtnReceive, gbc_rdbtnReceive);
	}

	@Override
	public void setWizard(Wizard w) {
		wizard = w;
		wizard.setPageTitle("Select action");
		wizard.setPageSubTitle("Do you want to send or receive files?");
		wizard.setFinishButtonVisible(false);
	}
}
