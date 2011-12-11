package se.gustavkarlsson.snap.gui;

import org.eclipse.jface.wizard.Wizard;

import se.gustavkarlsson.snap.main.Snap;

public class SnapWizard extends Wizard {

	public SnapWizard() {
		setWindowTitle(Snap.APP_NAME + " " + Snap.APP_VERSION);
	}

	@Override
	public void addPages() {
		addPage(new WelcomePage());
	}

	@Override
	public boolean performFinish() {
		return false;
	}

}
