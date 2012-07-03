package se.gustavkarlsson.snap.resources;

import java.io.File;

public abstract class Paths {

	// Directories
	private static final String APP_DATA_DIR = getAppDir();
	public static final String LOGS_DIR = APP_DATA_DIR + File.separator
			+ "logs";
	public static final String IMAGES_DIR = "src" + File.separator + "main"
			+ File.separator + "resources" + File.separator + "images";

	// Files
	public static final String PROPERTIES_FILE = APP_DATA_DIR + File.separator
			+ "snap.properties";
	public static final String FILE_IMAGE = IMAGES_DIR + File.separator
			+ "file.png";
	public static final String FOLDER_IMAGE = IMAGES_DIR + File.separator
			+ "folder.png";

	private static String getAppDir() {
		String osName = System.getProperty("os.name");

		String path;
		if (osName.toLowerCase().contains("win")) {
			path = System.getenv("APPDATA") + File.separator + "Snap";
		} else {
			path = System.getProperty("user.home") + File.separator + ".snap";
		}

		return path;
	}
}
