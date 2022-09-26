package GUI;

import javax.swing.JLabel;

import Server.Image;
import systemDefault.Cursors;
import systemDefault.Fonts;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Button extends JLabel {
	public class event extends MouseAdapter {
		private Button btn;

		public event(Button btn) {
			this.btn = btn;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			btn.pressed();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			btn.released();
		}
	}

	public int radius = 30;
	public static final Image normalDefaultBG = new Image(
			"C:\\Users\\Alan\\Downloads\\uipack-rpg\\PNG\\buttonLong_beige.png");
	public static final Image normalPressedBG = new Image(
			"C:\\Users\\Alan\\Downloads\\uipack-rpg\\PNG\\buttonLong_beige_pressed.png");
	public Image defaultBG;
	public Image pressedBG;
	private Image nowBG;

	public Button(String text) {
		super(text);
		this.defaultBG = normalDefaultBG;
		this.pressedBG = normalPressedBG;
		super.setHorizontalAlignment(JLabel.CENTER);
		super.setVerticalAlignment(JLabel.CENTER);
//		super.setForeground(Color.white);
//        super.setCursor(new Cursor(Cursor.HAND_CURSOR));
		super.setCursor(Cursors.handCursor);
		this.addMouseListener(new event(this));
		setSize(defaultBG.getWidth(), defaultBG.getHeight());
		nowBG = pressedBG;
		setFont(Fonts.normalFont);
	}

	public void set(Image defaultBG, Image pressedBG) {
		this.defaultBG = defaultBG;
		this.pressedBG = pressedBG;
		nowBG = defaultBG;
		setSize(defaultBG.getWidth(), defaultBG.getHeight());
		repaint();
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(nowBG.getBuffered(), 0, 0, getWidth(), getHeight(), null);
		super.paintComponent(g);
	}

	public void pressed() {
		nowBG = pressedBG;
		this.repaint();
	}

	public void released() {
		nowBG = defaultBG;
		repaint();
	}
}
