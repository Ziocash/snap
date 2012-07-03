package se.gustavkarlsson.snap.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.ciscavate.cjwizard.PageFactory;
import org.ciscavate.cjwizard.WizardContainer;

import se.gustavkarlsson.snap.gui.SnapPageFactory;
import se.gustavkarlsson.snap.resources.Paths;
import se.gustavkarlsson.snap.resources.PropertyManager;
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

		LOG.info("Creating GUI");
		setLookAndFeel();
		JFrame snapFrame = new JFrame(APP_NAME + " " + APP_VERSION);
		PageFactory snapPageFactory = new SnapPageFactory();
		WizardContainer wizard = new WizardContainer(snapPageFactory); // TODO lägg till pagetemplate som wrappar allt
		snapFrame.add(wizard);
		snapFrame.setSize(400, 300);

		LOG.info("Opening GUI");
		snapFrame.setVisible(true);
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
		LOG.info("Console logging started");
		String logFileName = getLogFileName();
		String logFilePath = Paths.LOGS_DIR + "/" + logFileName;
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