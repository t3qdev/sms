package com.b5m.sms.common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtil {
	public static final int RATIO = 0;
	public static final int SAME = -1;

	/**
	 * 이미지리사이징
	 * @since 2010. 9. 25. 
	 * @param in
	 * @param width
	 * @param height
	 * @param sExtensionNm
	 * @return 
	 */
	public static InputStream resize(InputStream in, int width, int height, String sExtensionNm) {
		Image srcImg = null;
		InputStream bais = null;
		
		try {
			srcImg = new ImageIcon(ByteUtil.inputStreamToByte(in)).getImage();

			int srcWidth = srcImg.getWidth(null);
			int srcHeight = srcImg.getHeight(null);

			int destWidth = -1, destHeight = -1;

			if (width == SAME) {
				destWidth = srcWidth;
			} else if (width > 0) {
				destWidth = width;
			}

			if (height == SAME) {
				destHeight = srcHeight;
			} else if (height > 0) {
				destHeight = height;
			}

			if (width == RATIO && height == RATIO) {
				destWidth = srcWidth;
				destHeight = srcHeight;
			} else if (width == RATIO) {
				double ratio = ((double) destHeight) / ((double) srcHeight);
				destWidth = (int) ((double) srcWidth * ratio);
			} else if (height == RATIO) {
				double ratio = ((double) destWidth) / ((double) srcWidth);
				destHeight = (int) ((double) srcHeight * ratio);
			}

			Image imgTarget = srcImg.getScaledInstance(destWidth, destHeight,
					Image.SCALE_SMOOTH);
			int pixels[] = new int[destWidth * destHeight];
			PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, destWidth,
					destHeight, pixels, 0, destWidth);
			try {
				pg.grabPixels();
			} catch (InterruptedException e) {
				throw new IOException(e.getMessage());
			}
			BufferedImage destImg = new BufferedImage(destWidth, destHeight,
					BufferedImage.TYPE_INT_RGB);
			destImg.setRGB(0, 0, destWidth, destHeight, pixels, 0, destWidth);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			
			ImageIO.write(destImg, sExtensionNm, byteArrayOutputStream);
			byte[] bytes = byteArrayOutputStream.toByteArray();
			
			bais = new ByteArrayInputStream(bytes);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return bais;
	}
}
