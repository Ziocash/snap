package se.gustavkarlsson.snap.util;

public abstract class FileUtils {
	private static final String UNIX_SEPARATOR = "/";
	private static final String WINDOWS_SEPARATOR = "\\";

	public static String extractFileNameFromPath(String path) {
		if (path.contains(UNIX_SEPARATOR)) {
			return path.substring(path.lastIndexOf(UNIX_SEPARATOR) + 1);
		} else if (path.contains(WINDOWS_SEPARATOR)) {
			return path.substring(path.lastIndexOf(WINDOWS_SEPARATOR) + 1);
		} else {
			return path;
		}
	}
}
