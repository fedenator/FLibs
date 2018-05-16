package org.fpalacios.flibs.util;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Loader {
	public static BufferedImage loadBufferedImage(String path) {
		BufferedImage image = null;
		BufferedImage convertedImage = null;
		try {
		    image = ImageIO.read(new File(path));
		    convertedImage = convertToCompatibleImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return convertedImage;
	}

	public static boolean saveImage(BufferedImage image, String path, String format) {
		boolean flag = true;

		try {
			File file = new File(path);
			file.createNewFile();
			ImageIO.write(image, format, file);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}

	public static BufferedImage loadOriginalBufferImage(String path) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return image;
	}

	public static BufferedImage convertToCompatibleImage(BufferedImage image) {
		BufferedImage convertedImage = null;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
	    GraphicsDevice gd = ge.getDefaultScreenDevice ();
	    GraphicsConfiguration gc = gd.getDefaultConfiguration ();
	    convertedImage = gc.createCompatibleImage (image.getWidth (),
	                                               image.getHeight (),
	                                               image.getTransparency () );
	    Graphics2D g2d = convertedImage.createGraphics ();
	    g2d.drawImage ( image, 0, 0, image.getWidth (), image.getHeight (), null );
	    g2d.dispose();

	    return convertedImage;
	}
}
