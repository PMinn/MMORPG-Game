package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import systemDefault.Fonts;
import systemDefault.Screen;

@SuppressWarnings("serial")
public class Label extends JLabel implements Screen {
	BufferedImage icon = null;

	public Label(String text, int align) {
		super(text, align);
		setFont(Fonts.normalFont);
		setVerticalAlignment(JLabel.BOTTOM);
		set(Fonts.color);
	}

	public Label(String text) {
		super(text);
		setFont(Fonts.normalFont);
		setVerticalAlignment(JLabel.BOTTOM);
		set(Fonts.color);
	}

	public Label(String text, BufferedImage icon) {
		super(text);
		setFont(Fonts.normalFont);
		setVerticalAlignment(JLabel.BOTTOM);
		set(Fonts.color);
		setIcon(icon);
	}

	public void setIcon(BufferedImage icon) {
		this.icon = icon;
		repaint();
	}

	public void set(int x, int y, int w, int h) {
		setBounds(x, y, w, h);
	}

	public void set(Color c) {
		setForeground(c);
	}

	public void set(boolean v) {
		setVisible(v);
	}

	public void set(Font f) {
		setFont(f);
	}

	public void setHorizontal(int a) {
		setHorizontalAlignment(a);
	}

	public void setVertical(int a) {
		setVerticalAlignment(a);// JLabel.CENTER
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (icon != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(icon, 0, getHeight() / 2 - icon.getHeight() / 2, null);
		}
	}
}
