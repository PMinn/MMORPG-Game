package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import systemDefault.Screen;
import java.awt.image.BufferedImage;

public class Floors extends Object implements Screen {
	private int avaliableWidth = 32;
	private int avaliableHeight = 17;
	private int flours[][] = new int[8][2];
	private BufferedImage[][] map;

	public Floors(BufferedImage[][] map) throws IOException {

		this.map = map;
		for (int i = 0; i < 8; i++) {
			flours[i][0] = (int) Math.floor(Math.random() * avaliableHeight);
			flours[i][1] = (int) Math.floor(Math.random() * avaliableWidth);
		}
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(141, 196, 53));
		g2d.fillRect(0, 0, (int) Screen.width, (int) Screen.height);
		for (int i = 0; i <= avaliableHeight; i++) {
			for (int j = 0; j <= avaliableWidth; j++) {
				g2d.drawImage(map[0][5], (int) (j * blockSize + x), (int) (i * blockSize + y), blockSize, blockSize,
						null);
				for (int k = 0; k < 8; k++) {
					if (flours[k][0] == i && flours[k][1] == j) {
						g2d.drawImage(map[9][28 + k / 4], (int) (j * blockSize + x), (int) (i * blockSize + y),
								blockSize, blockSize, null);
					}
				}
			}
		}
	}
}
