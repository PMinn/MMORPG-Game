package rpg;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Server.Image;
import systemDefault.Screen;

public class Character implements Screen {
	public static BufferedImage characters[][][] = new BufferedImage[6][3][4];
	public static final int STAND = 0;
	public static final int WALK_RIGHT = 1;
	public static final int WALK_LEFT = 2;
	public static final int WALK_FRONT = 3;
	public static final int WALK_BACK = 4;

	public int state;
	public int k;
	public int i;
	public int j;
	public int positionX;
	public int positionY;
	public int width;
	public int height;
	public boolean isFocus = false;
	public boolean hiting = false;
	public int hitIndex = 0;

	public Character() {
		k = 0;
		i = 0;
		j = 0;
		positionX = 0;
		positionY = 0;
		width = characters[0][0][0].getWidth();
		height = characters[0][0][0].getHeight();
	}

	public Character(int k, int i, int j) {
		this.k = k;
		this.i = i;
		this.j = j;
		positionX = 0;
		positionY = 0;
		width = characters[0][0][0].getWidth();
		height = characters[0][0][0].getHeight();
	}

	public static void load() {
		for (int k = 0; k < 6; k++) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					characters[k][i][j] = new Image(String.format("tile_%04d.png", 23 + 81 * k + 27 * i + j))
							.getBuffered();
				}
			}
		}
	}

	protected void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (isFocus) {
			g2d.setColor(theme[2]);
			g2d.setStroke(new BasicStroke(3));
			g2d.drawOval(positionX, positionY + height * 3 / 4, width, height / 2);
		}
		g2d.drawImage(characters[k][i][j], positionX, positionY, width, height, null);
		if (hiting) {
			g2d.drawImage(GamePanel.hit[hitIndex], positionX + width / 2 - blockSize, positionY, blockSize * 2,
					blockSize * 2, null);
			hitIndex++;
			if (hitIndex == 10) {
				hitIndex = 0;
				hiting = false;
			}
		}
	}

}
