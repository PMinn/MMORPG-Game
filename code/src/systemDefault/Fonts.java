package systemDefault;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

import Server.Image;

public class Fonts {
	public final static int fontSize = (int) (new JLabel().getFont().getSize() * 1.3);
	public final static Color color = Color.white;
	public static Font normalFont;
	private Font nf;

	public Fonts() {
		try {
			loadFont();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(nf);
		normalFont = new Font("jf open ¯»¶ê 1.1", Font.PLAIN, Fonts.fontSize);
	}

	private void loadFont() throws FontFormatException, IOException {
		if (!Files.exists("jf-openhuninn-1.1.ttf")) {
			Image.download("jf-openhuninn-1.1.ttf");
		}
		nf = Font.createFont(Font.TRUETYPE_FONT, new File("jf-openhuninn-1.1.ttf"));
	}
}
