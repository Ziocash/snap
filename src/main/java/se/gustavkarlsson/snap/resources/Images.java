package se.gustavkarlsson.snap.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Images {

	public static final BufferedImage FILE = loadImage(Paths.FILE_IMAGE);
	public static final BufferedImage FOLDER = loadImage(Paths.FOLDER_IMAGE);

	private static BufferedImage loadImage(String path) {
		File file = new File(path);

		BufferedImage image;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Log error and use default image;

			image = null;
		}

		return image;
	}
}
