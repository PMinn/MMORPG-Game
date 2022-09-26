package Map;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Timer;

import Server.Image;
import rpg.GamePanel;
import systemDefault.Fonts;
import systemDefault.Screen;

public class Monster extends Object implements Screen, MouseListener, ActionListener {
	private int index = 0;
	private int renderTime = 3;
	public boolean isFocus = false;
	public boolean hiting = false;
	public int hitIndex = 0;
	public int exp;
	public String id;
	public int attackNumber;
	private static Image imgs[][] = { { new Image("tile_0051-2.png"), new Image("tile_0052-2.png") },
			{ new Image("tile_0053-2.png"), new Image("tile_0054-2.png") },
			{ new Image("tile_0055-2.png"), new Image("tile_0056-2.png") } };
	private static String names[] = { "¤p¶À", "¤pÂÅ", "¤p¬õ" };
	private int type;
	private int level = 0;
	public int HP = 7;
	private static int maxHPBlock = 8;
	private GamePanel gamePanel;
	public Timer attack;
	private boolean attacking = false;

	public Monster(GamePanel gamePanel, int x, int y, String id, int type, int level, int HP, int attack, int exp) {
		this.id = id;
		this.level = level;
		this.HP = HP;
		this.HP = HP;
		attackNumber = attack;
		this.exp = exp;
		this.gamePanel = gamePanel;
		super.x = x;
		super.y = y;
		this.type = type;
		this.attack = new Timer(1500, this);
	}

	public void moveX(int i) {
		x += i;
	}

	public void moveY(int i) {
		y += i;
	}

	public void paint(Graphics g) {
		if (HP < 0)
			HP = 0;
//		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (isFocus) {
			g2d.setColor(theme[3]);
			g2d.setStroke(new BasicStroke(3));
			g2d.drawOval(x - blockSize / 2, y - blockSize + super.height * 3 / 4, super.width, super.height / 2);
		}
		g2d.setColor(theme[5]);
		g2d.setFont(Fonts.normalFont);
		String textLabel = String.format("%s Lv%d", names[type], level);
		g2d.drawString(textLabel, x - 2 * Fonts.fontSize,
				(int) (y - blockSize - GamePanel.bloods[0][0].getHeight() * 1.5));
		int bloodX = x - GamePanel.bloods[0][0].getWidth() - (maxHPBlock / 2 - 1) * GamePanel.bloods[0][1].getWidth();
		int bloodY = y - blockSize - GamePanel.bloods[0][0].getHeight();

		for (int i = 0; i < maxHPBlock; i++) {
			if (i == 0) {
				g2d.drawImage(GamePanel.bloods[0][0].getBuffered(), bloodX, bloodY, null);// base
				if (HP > 0) {
					g2d.drawImage(GamePanel.bloods[1][0].getBuffered(), bloodX, bloodY, null);
				}
				if (HP == 1) {
					g2d.drawImage(GamePanel.bloods[1][2].getBuffered(), bloodX + GamePanel.bloods[0][0].getWidth(),
							bloodY, null);
				}
				bloodX += GamePanel.bloods[0][0].getWidth();
			} else if (i == maxHPBlock - 1) {
				g2d.drawImage(GamePanel.bloods[0][2].getBuffered(), bloodX, bloodY, null);// base
			} else {
				g2d.drawImage(GamePanel.bloods[0][1].getBuffered(), bloodX, bloodY, null);// base
				if (i < HP) {
					g2d.drawImage(GamePanel.bloods[1][1].getBuffered(), bloodX, bloodY, null);
					if (i + 1 == HP) {
						g2d.drawImage(GamePanel.bloods[1][2].getBuffered(), bloodX + GamePanel.bloods[0][1].getWidth(),
								bloodY, null);
					}
				}
				bloodX += GamePanel.bloods[0][1].getWidth();
			}
		}

		g2d.drawImage(imgs[type][(index / renderTime) % imgs[type].length].getBuffered(), x - blockSize / 2,
				y - blockSize, blockSize, blockSize, null);
		if (hiting) {
			if (!attacking) {
				attack.start();
				attacking = true;
			}
			g2d.drawImage(GamePanel.hit[hitIndex], x - blockSize, y - blockSize, blockSize * 2, blockSize * 2, null);
			hitIndex++;
			if (hitIndex == 10) {
				hitIndex = 0;
				hiting = false;
			}
		}
		index++;
		if (index == renderTime * renderTime) {
			index = 0;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		isFocus = true;
	}

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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (GamePanel.isInRange(this, gamePanel.player)) {
			gamePanel.player.hiting = true;
			gamePanel.HP--;
		} else {
			attack.stop();
			attacking = false;
		}
	}

	public void remove() {
		for (int i = 0; i < gamePanel.objs.size(); i++) {
			if (gamePanel.objs.get(i) == this) {
				gamePanel.objs.remove(i);
			}
		}
	}
}
