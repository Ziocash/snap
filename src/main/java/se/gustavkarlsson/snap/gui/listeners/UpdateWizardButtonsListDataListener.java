package se.gustavkarlsson.snap.gui.listeners;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import se.gustavkarlsson.gwiz.AbstractWizardPage;

public class UpdateWizardButtonsListDataListener implements ListDataListener {

	private final AbstractWizardPage page;

	public UpdateWizardButtonsListDataListener(AbstractWizardPage page) {
		this.page = page;
	}
	@Override
	public void intervalAdded(ListDataEvent e) {
		page.updateWizardButtons();

	}
	@Override
	public void intervalRemoved(ListDataEvent e) {
		page.updateWizardButtons();

	}
	@Override
	public void contentsChanged(ListDataEvent e) {
		page.updateWizardButtons();
	}

}
