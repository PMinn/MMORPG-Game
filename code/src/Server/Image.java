package Server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import GUI.Dialog;

public class Image {
	private BufferedImage img;

	public Image(String fileName) {
		load(fileName);
	}

	public BufferedImage getBuffered() {
		return this.img;
	}

	public int getWidth() {
		return img.getWidth();
	}

	public int getHeight() {
		return img.getHeight();
	}

	public static void download(String fileName) {
		try (InputStream in = new URL(Host.url + "static/" + fileName).openStream()) {
			System.out.println("download file: " + fileName);
			Files.copy(in, Paths.get(fileName));
		} catch (MalformedURLException e) {
			Dialog dialog = new Dialog();
			dialog.show("錯誤-下載圖片失敗", "檔案:" + fileName);
		} catch (IOException e) {
			Dialog dialog = new Dialog();
			dialog.show("錯誤-下載圖片失敗", "檔案:" + fileName);
		}
	}

	private void load(String fileName) {
		try {
			this.img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			download(fileName);
			load(fileName);
		}
	}
}