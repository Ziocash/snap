package se.gustavkarlsson.snap.resources;

public abstract class Directories {

	public static final String APP_DATA = getAppDir();
	public static final String LOGS = APP_DATA + "/logs";
	public static final String IMAGES = "src/main/resources/images";

	
	private static String getAppDir() {
		String osName = System.getProperty("os.name");

		String path;
		if (osName.toLowerCase().contains("win")) {
			path = System.getenv("APPDATA") + "/Snap";
		} else {
			path = System.getProperty("user.home") + "/.snap";
		}
		
		return path;
	}
}
