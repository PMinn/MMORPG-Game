package rpg;

import static systemDefault.Screen.width;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import systemDefault.Fonts;

@SuppressWarnings("serial")
public class TopBar extends JLabel {
	private GamePanel gamePanel;
//	private int HP = 10;
	private int maxHPBlock = 12;

	public TopBar(GamePanel gamePanel) {
		super(String.format("    %s      Lv%d", gamePanel.user.name, gamePanel.user.level));
		this.gamePanel = gamePanel;
		setFont(new Font("jf open ¯»¶ê 1.1", Font.PLAIN, Fonts.fontSize * 2));
		setForeground(Color.white);
		setBounds(0, 0, (int) width, 50);
	}

	protected void paintComponent(Graphics g) {
		String text = String.format("    %s      Lv%d", gamePanel.user.name, gamePanel.user.level);
		setText(text);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(0, 0, 0, 180));
		g2d.fillRect(0, 0, (int) width, getHeight());
		super.paintComponent(g);
		int bloodX = text.length() * Fonts.fontSize;
		int bloodY = getHeight() / 2 - GamePanel.bloods[0][0].getHeight() / 2;
		g2d.setColor(Fonts.color);
		g2d.setFont(Fonts.normalFont);
		g2d.drawString("HP", bloodX, bloodY + Fonts.fontSize);
		bloodX += Fonts.fontSize * 2;
		int HP = (int) ((1.0 * gamePanel.HP / gamePanel.maxHP) * maxHPBlock);
		for (int i = 0; i < maxHPBlock; i++) {
			if (i == 0) {
				g2d.drawImage(GamePanel.bloods[0][0].getBuffered(), bloodX, bloodY, null);// base
				if (HP > 0) {
					g2d.drawImage(GamePanel.bloods[1][0].getBuffered(), bloodX, bloodY, null);
				}
				bloodX += GamePanel.bloods[0][0].getWidth();
			} else if (i == maxHPBlock - 1) {
				g2d.drawImage(GamePanel.bloods[0][2].getBuffered(), bloodX, bloodY, null);// base
				if (i + 1 == HP) {
					g2d.drawImage(GamePanel.bloods[1][2].getBuffered(), bloodX, bloodY, null);
				}
			} else {
				g2d.drawImage(GamePanel.bloods[0][1].getBuffered(), bloodX, bloodY, null);// base
				if (i < HP) {
					g2d.drawImage(GamePanel.bloods[1][1].getBuffered(), bloodX, bloodY, null);
				} else if (i == HP) {
					g2d.drawImage(GamePanel.bloods[1][2].getBuffered(), bloodX, bloodY, null);
				}
				bloodX += GamePanel.bloods[0][1].getWidth();
			}
		}
	}
}
