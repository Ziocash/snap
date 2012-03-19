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

import se.gustavkarlsson.snap.domain.FolderNode;
import se.gustavkarlsson.snap.gui.SnapWizard;
import se.gustavkarlsson.snap.resources.Directories;
import se.gustavkarlsson.snap.resources.PropertyManager;
import se.gustavkarlsson.snap.service.Service;
import se.gustavkarlsson.snap.service.persistance.PersistanceManager;
import se.gustavkarlsson.snap.util.LoggerHelper;

public class Snap {

	public static final String APP_NAME = "Snap";
	public static final String APP_VERSION = "2.0.0-SNAPSHOT";

	private static final Logger LOG = LoggerHelper.getLogger();
	private static final String LOG_LAYOUT = "%-10r %-5p [%t]: %m%n";

	private static final String LOG_FILE_EXTENSION = ".log";
	private static final String LOG_FILE_DATE_FORMAT = "yyyy-MM-dd HH_mm_ss_S";

	public static void main(String[] args) {
		configureLogging();
		runWizard();
	}

	private static void runWizard() {
		LOG.info("Loading properties");
		PropertyManager.load();

		LOG.info("Creating service");
		Service service = new Service();

		LOG.info("Creating GUI");
		Display.getDefault();
		Wizard wizard = new SnapWizard(service);
		WizardDialog dialog = new WizardDialog(null, wizard);
		dialog.create();

		LOG.info("Opening GUI");
		dialog.open();
		LOG.info("GUI closed");

		LOG.info("Saving properties");
		PropertyManager.save(); // Flytta denna
	}

	private static void configureLogging() {
		BasicConfigurator.configure();
		Logger root = Logger.getRootLogger();
		root.removeAllAppenders();
		Layout layout = new PatternLayout(LOG_LAYOUT);
		root.addAppender(new ConsoleAppender(layout));
		LOG.info("Console logging started");
		String logFileName = getLogFileName();
		String logFilePath = Directories.LOGS + "/" + logFileName;
		try {
			root.addAppender(new FileAppender(layout, logFilePath));
			LOG.info("File logging started");
		} catch (IOException e) {
			LOG.warn("Failed to create FileAppender: " + e.getMessage());
		}
	}

	private static String getLogFileName() {
		LOG.debug("Generating log file name");
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				LOG_FILE_DATE_FORMAT);
		String logFileName = simpleDateFormat.format(calendar.getTime())
				+ LOG_FILE_EXTENSION;
		LOG.debug("Log file name is: " + logFileName);
		return logFileName;
	}
}

// TODO configure preferences.