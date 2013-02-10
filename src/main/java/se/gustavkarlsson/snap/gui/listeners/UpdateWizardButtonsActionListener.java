package se.gustavkarlsson.snap.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import se.gustavkarlsson.gwiz.AbstractWizardPage;

public class UpdateWizardButtonsActionListener implements ActionListener {

	private final AbstractWizardPage page;

	public UpdateWizardButtonsActionListener(AbstractWizardPage page) {
		this.page = page;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		page.updateWizardButtons();
	}

}
