package se.gustavkarlsson.snap.main;

import org.apache.log4j.Logger;

import se.gustavkarlsson.snap.gui.SnapWizard;
import se.gustavkarlsson.snap.gui.StartPage;
import se.gustavkarlsson.snap.gui.Wizard;
import se.gustavkarlsson.snap.util.LoggerHelper;

public class Snap {

	private static final Logger log = LoggerHelper.getLogger();
	public static String APP_NAME = "Snap";
	public static String VERSION = "2.0.0-SNAPSHOT";

	public static void main(String[] args) {
		Wizard wiz = new SnapWizard();
		wiz.setTitle(APP_NAME + " " + VERSION);
		wiz.setPage(new StartPage());
		wiz.show();
	}
}

// TODO Config logging
