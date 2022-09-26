package Map;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import systemDefault.Screen;

public class Tree extends Object implements Screen {
	private BufferedImage top, bottom;

	public Tree(BufferedImage top, BufferedImage bottom) {
		this.top = top;
		this.bottom = bottom;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(top, x - blockSize / 2, y - blockSize * 2, blockSize, blockSize, null);
		g2d.drawImage(bottom, x - blockSize / 2, y - blockSize, blockSize, blockSize, null);
	}
}
