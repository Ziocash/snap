package se.gustavkarlsson.snap.resources;

public abstract class Directories {

	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String APP_DATA = getAppDir() + "Snap" + FILE_SEPARATOR;
	public static final String LOGS = APP_DATA + "logs" + FILE_SEPARATOR;
	public static final String IMAGES = "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "images" + FILE_SEPARATOR;

	
	private static String getAppDir() {
		String osName = System.getProperty("os.name");

		String path;
		if (osName.toLowerCase().contains("win")) {
			path = System.getenv("APPDATA");
			path += FILE_SEPARATOR;
			
			return path;
		}
		path = System.getProperty("user.home");
		path += FILE_SEPARATOR + ".";

		return path;
	}
}
