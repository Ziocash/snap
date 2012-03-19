package se.gustavkarlsson.snap.resources;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

public abstract class Images {
	
	public static final Image FILE = loadImage(Directories.IMAGES + "/file.png");
	public static final Image FOLDER = loadImage(Directories.IMAGES + "/folder.png");
	
	private static Image loadImage(String path) {
		ImageData data = new ImageData(path);
		Image image = new Image(Display.getCurrent(), data);
		
		return image;
	}
}
