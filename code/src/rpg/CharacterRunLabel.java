package rpg;

import static systemDefault.Screen.height;
import static systemDefault.Screen.width;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CharacterRunLabel extends JLabel implements ActionListener {
	private int i = 0;
	private int j = 3;
	private int positionX = -48 * 6;
	private Character[] character = { null, null, null, null, null, null };

	public CharacterRunLabel() {
		setBounds(0, (int) (height - 48), (int) width, 48);
		for (int k = 0; k < 6; k++) {
			character[k] = new Character(k,i,j);
			character[k].width = 48;
			character[k].height = 48;
			character[k].positionY = 0;
			character[k].positionX = positionX + 48 * k;
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int k = 0; k < 6; k++) {
			character[k].paint(g);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		i = (i + 2) % 3;
		if (positionX > width)
			j = 0;
		else if (positionX < -48 * 6)
			j = 3;
		if (j == 3)
			positionX += 2;
		else
			positionX -= 2;
		for (int k = 0; k < 6; k++) {
			character[k].i = i;
			character[k].j = j;
			character[k].positionX = positionX + 48 * k;
		}
		repaint();
	}
}
