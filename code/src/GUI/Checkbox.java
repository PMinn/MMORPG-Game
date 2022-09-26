package GUI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import Server.Image;
import systemDefault.Cursors;
import systemDefault.Fonts;

@SuppressWarnings("serial")
public class Checkbox extends JLabel implements MouseListener {
	public static final Image uncheckedImage = new Image("yellow_boxCross.png");
	public static final Image checkedImage = new Image("yellow_boxCheckmark.png");
	public boolean isChecked;
	private Image nowImage;

	public Checkbox(String text) {
		super(text);
		setSize(uncheckedImage.getWidth() + text.length() * Fonts.fontSize + 10, uncheckedImage.getHeight());
		setFont(Fonts.normalFont);
		setForeground(Fonts.color);
		setVerticalAlignment(JLabel.CENTER);
		setHorizontalAlignment(JLabel.RIGHT);
		setCursor(Cursors.handCursor);
		isChecked = false;
		nowImage = uncheckedImage;
		addMouseListener(this);
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(nowImage.getBuffered(), 0, 0, getHeight(), getHeight(), null);
		super.paintComponent(g);
	}

	public void reCheck() {
		if (isChecked) {
			nowImage = checkedImage;
		} else {
			nowImage = uncheckedImage;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isChecked) {
			isChecked = false;
		} else {
			isChecked = true;
		}
		reCheck();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
