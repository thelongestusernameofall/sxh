package com.sxh.common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Service;

@Service
public class ThumbnailAWTService2 {
	public static final int WIDTH = 100;
	public static final int HEIGHT = 100;

	public String thumbnail(FileItem file,String newfilename, String uploadPath, String realUploadPath) {
		OutputStream os = null;
		InputStream is = null;
		try {
			// is= new FileInputStream(file);
			String des = realUploadPath+"\\"+ newfilename;
			System.out.println("缩略图地址="+des);
			System.out.println("file.size="+file.getSize());
			is = file.getInputStream();
			os = new FileOutputStream(des);
			// os = new FileOutputStream(des);
			//
			// byte[] buffer = new byte[1024];
			// int len = 0;
			// while ((len = is.read(buffer)) > 0) {
			//
			// os.write(buffer);
			// }
			System.out.println("file.getName()" + newfilename);
			// os = new FileOutputStream(des);
			/// System.out.print("=================是否存在文件"+file.exists());
			Image image = ImageIO.read(is);
			

			// Image image = ImageIO.read(file);
			int width = image.getWidth(null);// 原图宽度
			int height = image.getHeight(null);// 原图高度
			System.out.println("width="+image.getWidth(null));
			System.out.println("height="+image.getHeight(null));

			// 宽度缩略比例
			int rate1 = width / WIDTH;
			System.out.println("rate1="+rate1);
			// 高度缩略比例
			int rate2 = height / HEIGHT;
			System.out.println("rate2="+rate2);

			int rate = 0;
			if (rate1 > rate2) {// 宽度缩略比例大于高度缩略比例，使用宽度缩略比例
				rate = rate1;
			} else {
				rate = rate2;
			}
			// 计算缩略图最终的宽度和高度
			int newWidth = width / rate;
			int newHeight = height / rate;

			BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

			bufferedImage.getGraphics().drawImage(image.getScaledInstance(newWidth, newHeight, image.SCALE_SMOOTH), 0,
					0, null);

			// image/jpeg
			String imageType = file.getContentType().substring(file.getContentType().indexOf("/") + 1);
			ImageIO.write(bufferedImage, imageType, os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return uploadPath + "/" + file.getName();
	}

}
