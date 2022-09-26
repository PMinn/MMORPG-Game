package systemDefault;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import Server.Image;

public class Cursors {
	public static Cursor defaultCursor;
	public static Cursor handCursor;
	public static Cursor attackCursor;

	public Cursors() {
		defaultCursor = loadCursor("cursor_pointerFlat2.png");
		handCursor = loadCursor("cursor_hand2.png");
		attackCursor = loadCursor("cursorSword_silver2.png");
	}

	private Cursor loadCursor(String img) {
		BufferedImage cursorImage = new Image(img).getBuffered();
		return Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), null);
	}
}
