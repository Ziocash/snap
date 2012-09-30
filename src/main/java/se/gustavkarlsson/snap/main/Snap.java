package se.gustavkarlsson.snap.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import se.gustavkarlsson.gwiz.WizardController;
import se.gustavkarlsson.snap.gui.SnapWizard;
import se.gustavkarlsson.snap.gui.pages.general.welcome.WelcomePage;
import se.gustavkarlsson.snap.resources.Paths;
import se.gustavkarlsson.snap.resources.PropertyManager;
import se.gustavkarlsson.snap.util.LoggerHelper;

public class Snap {

	public static final String APP_NAME = "Snap";
	public static final String APP_VERSION = "2.0.0-SNAPSHOT";

	private static final Logger logger = LoggerHelper.getLogger();
	private static final String LOG_LAYOUT = "%-10r %-5p [%t]: %m%n";

	private static final String LOG_FILE_EXTENSION = ".log";
	private static final String LOG_FILE_DATE_FORMAT = "yyyy-MM-dd HH_mm_ss_S";

	public static void main(String[] args) {
		configureLogging();
		runWizard();
	}

	private static void runWizard() {
		logger.info("Loading properties");
		PropertyManager.load();

		logger.info("Creating GUI");
		setLookAndFeel();
		SnapWizard wizard = new SnapWizard(APP_NAME + " " + APP_VERSION);
		wizard.setVisible(true);
		WizardController controller = new WizardController(wizard);
		controller.startWizard(new WelcomePage());

		logger.info("Opening GUI");
	}

	private static void setLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (info.getName().equals("Nimbus")) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// TODO If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}

	private static void configureLogging() {
		BasicConfigurator.configure();
		Logger root = Logger.getRootLogger();
		root.removeAllAppenders();
		Layout layout = new PatternLayout(LOG_LAYOUT);
		root.addAppender(new ConsoleAppender(layout));
		logger.info("Console logging started");
		String logFileName = getLogFileName();
		String logFilePath = Paths.LOGS_DIR + "/" + logFileName;
		try {
			root.addAppender(new FileAppender(layout, logFilePath));
			logger.info("File logging started");
		} catch (IOException e) {
			logger.warn("Failed to create FileAppender: " + e.getMessage());
		}
	}

	private static String getLogFileName() {
		logger.debug("Generating log file name");
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				LOG_FILE_DATE_FORMAT);
		String logFileName = simpleDateFormat.format(calendar.getTime())
				+ LOG_FILE_EXTENSION;
		logger.debug("Log file name is: " + logFileName);
		return logFileName;
	}
}

// TODO configure preferences.