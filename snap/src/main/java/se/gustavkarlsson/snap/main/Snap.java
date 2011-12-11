package se.gustavkarlsson.snap.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;

import se.gustavkarlsson.snap.gui.SnapWizard;
import se.gustavkarlsson.snap.util.LoggerHelper;

public class Snap {

	private static final Logger LOG = LoggerHelper.getLogger();
	private static final String LOG_LAYOUT = "%-10r %-5p [%t]: %m%n";

	public static final String APP_NAME = "Snap";
	public static final String APP_VERSION = "2.0.0-SNAPSHOT";
	public static final String APP_DIR = getAppDir();

	private static final String LOG_FILE_EXTENSION = ".log";
	private static final String LOG_FILE_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss-S";
	private static final String LOG_FILE_NAME = getLogFileName();

	public static void main(String[] args) {
		configureLogging();
		runWizard();
	}

	private static void runWizard() {
		LOG.info("Creating GUI");
		Display.getDefault();
		Wizard wizard = new SnapWizard();
		WizardDialog dialog = new WizardDialog(null, wizard);
		dialog.create();
		LOG.info("Opening GUI");
		dialog.open();
		LOG.info("GUI closed");
	}

	private static void configureLogging() {
		BasicConfigurator.configure();
		Logger root = Logger.getRootLogger();
		root.removeAllAppenders();
		Layout layout = new PatternLayout(LOG_LAYOUT);
		root.addAppender(new ConsoleAppender(layout));
		LOG.info("Console logging started");

		String logFilePath = APP_DIR + LOG_FILE_NAME;
		try {
			root.addAppender(new FileAppender(layout, logFilePath));
			LOG.info("File logging started");
		} catch (IOException e) {
			LOG.warn("Failed to create FileAppender: " + e.getMessage());
		}
	}

	private static String getAppDir() {
		String fileSeparator = System.getProperty("file.separator");
		String osName = System.getProperty("os.name");

		String path;
		if (osName.toLowerCase().contains("win")) {
			path = System.getenv("APPDATA");
			path += fileSeparator + APP_NAME + fileSeparator;
		} else {
			path = System.getProperty("user.home");
			path += fileSeparator + "." + APP_NAME + fileSeparator;
		}

		return path;
	}

	private static String getLogFileName() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(LOG_FILE_DATE_FORMAT);
		return sdf.format(cal.getTime()) + LOG_FILE_EXTENSION;
	}
}

// TODO configure preferences.