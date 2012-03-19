package se.gustavkarlsson.snap.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.gustavkarlsson.snap.util.LoggerHelper;

public abstract class PropertyManager {
	
	private static final Logger LOG = LoggerHelper.getLogger();
	
	private static final String PROPERTIES_FILE_PATH = Directories.APP_DATA
			+ "/snap.properties";

	private static final String PROPERTY_USE_ADVANCED_OPTIONS_KEY = "use_advanced_options";
	private static final boolean PROPERTY_USE_ADVANCED_OPTIONS_DEFAULT_VALUE = false;
	
	private static final String PROPERTY_USE_UPNP_KEY = "use_upnp";
	private static final boolean PROPERTY_USE_UPNP_DEFAULT_VALUE = true;
	
	private static final String PROPERTY_USE_NAT_PMP_KEY = "use_nat_pmp";
	private static final boolean PROPERTY_USE_NAT_PMP_DEFAULT_VALUE = true;

	private static final String PROPERTY_LISTENING_PORT_KEY = "listening_port";
	private static final int PROPERTY_LISTENING_PORT_DEFAULT_VALUE = 2121;

	private static Properties properties = new Properties();

	public static void load() {
		LOG.info("Loading properties from file: " + PROPERTIES_FILE_PATH);
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(PROPERTIES_FILE_PATH);
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			LOG.warn("Property file could not be read: " + PROPERTIES_FILE_PATH);
		} catch (IOException e) {
			LOG.error("Failed to load properties", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	public static void store() {
		LOG.info("Storing properties to file: " + PROPERTIES_FILE_PATH);
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(PROPERTIES_FILE_PATH);
			properties.store(outputStream, null);
		} catch (FileNotFoundException e) {
			LOG.error("Failed to write properties file: " + PROPERTIES_FILE_PATH, e);
		} catch (IOException e) {
			LOG.error("Failed to store properties", e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static boolean isUsingAdvancedOptions() {
		return getBoolean(PROPERTY_USE_ADVANCED_OPTIONS_KEY, PROPERTY_USE_ADVANCED_OPTIONS_DEFAULT_VALUE);
	}

	public static void setUsingAdvancedOptions(boolean value) {
		setBoolean(PROPERTY_USE_ADVANCED_OPTIONS_KEY, value);
	}

	public static boolean isUsingUpnp() {
		return getBoolean(PROPERTY_USE_UPNP_KEY, PROPERTY_USE_UPNP_DEFAULT_VALUE);
	}

	public static void setUsingUpnp(boolean value) {
		setBoolean(PROPERTY_USE_UPNP_KEY, value);
	}

	public static boolean isUsingNatPmp() {
		return getBoolean(PROPERTY_USE_NAT_PMP_KEY, PROPERTY_USE_NAT_PMP_DEFAULT_VALUE);
	}

	public static void setUsingNatPmp(boolean value) {
		setBoolean(PROPERTY_USE_NAT_PMP_KEY, value);
	}

	public static int getListeningPort() {
		String str = properties.getProperty(PROPERTY_LISTENING_PORT_KEY,
				Integer.toString(PROPERTY_LISTENING_PORT_DEFAULT_VALUE));
		int ret = PROPERTY_LISTENING_PORT_DEFAULT_VALUE; // default
		try {
			int newRet = Integer.parseInt(str);
			if (0 <= newRet && newRet <= 65535) {
				ret = newRet;
			} else {
				LOG.warn("Listening port is out of range: " + newRet);
			}
		} catch (NumberFormatException e) {
			LOG.warn("Could not parse listening port: " + str);
		}
		return ret;
	}

	public static void setListeningPort(int value) {
		properties.setProperty(PROPERTY_LISTENING_PORT_KEY,
				Integer.toString(value));
	}

	private static boolean getBoolean(String key, boolean defaultValue) {
		String str = properties.getProperty(key, Boolean.toString(defaultValue));
		boolean ret = Boolean.parseBoolean(str);
		return ret;
	}
	
	private static void setBoolean(String key, boolean value) {
		properties.setProperty(key,
				Boolean.toString(value));
	}

}
