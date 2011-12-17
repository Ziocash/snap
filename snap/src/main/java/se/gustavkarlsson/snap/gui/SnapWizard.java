package se.gustavkarlsson.snap.gui;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;

import se.gustavkarlsson.snap.main.Snap;
import se.gustavkarlsson.snap.util.LoggerHelper;

public class SnapWizard extends Wizard {
	
	private static final Logger LOG = LoggerHelper.getLogger();

	public SnapWizard() {
		setWindowTitle(Snap.APP_NAME + " " + Snap.APP_VERSION);
	}

	@Override
	public void addPages() {
		LOG.debug("Adding pages to Wizard");
		addPage(new WelcomePage());

		// Sender
		addPage(new ChooseFilesPage());
		addPage(new AdvancedOptionsPage());

		// Receiver
	}

	@Override
	public boolean performFinish() {
		return false;
	}

	@Override
	public boolean canFinish() {
		return false;
	}

}
