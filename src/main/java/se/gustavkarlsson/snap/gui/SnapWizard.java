package se.gustavkarlsson.snap.gui;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.Wizard;

import se.gustavkarlsson.snap.gui.pages.general.welcome.WelcomePage;
import se.gustavkarlsson.snap.gui.pages.send.advancedoptions.AdvancedOptionsPage;
import se.gustavkarlsson.snap.gui.pages.send.choosefiles.ChooseFilesPage;
import se.gustavkarlsson.snap.gui.pages.send.choosesession.ChooseSessionPage;
import se.gustavkarlsson.snap.main.Snap;
import se.gustavkarlsson.snap.service.Service;
import se.gustavkarlsson.snap.util.LoggerHelper;

public class SnapWizard extends Wizard {

	private static final Logger LOG = LoggerHelper.getLogger();

	private final Service service = new Service();

	public SnapWizard(Service service) {
		setWindowTitle(Snap.APP_NAME + " " + Snap.APP_VERSION);
	}

	@Override
	public void addPages() {
		LOG.debug("Adding pages to Wizard");
		addPage(new WelcomePage(service.getSessionManager()));

		// Sender
		addPage(new ChooseSessionPage(service.getSessionManager()));
		addPage(new ChooseFilesPage(service.getSessionManager()));
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