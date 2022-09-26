package Map;

import java.awt.Graphics;
import systemDefault.Screen;

public abstract class Object implements Screen {
	public int x = 0;
	public int y = 0;
	public int width = blockSize;
	public int height = blockSize;

	public void moveX(int i) {
		x += i;
	}

	public void moveY(int i) {
		y += i;
	}

	public abstract void paint(Graphics g);
}
