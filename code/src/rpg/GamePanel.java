package rpg;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import java.awt.FontFormatException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import GUI.Dialog;
import GUI.Label;
import Map.*;
import Map.Object;
import Server.Host;
import Server.HttpRequest;
import Server.Image;
import systemDefault.Screen;
import systemDefault.Cursors;
import systemDefault.Fonts;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements KeyListener, Screen, ActionListener, MouseListener {
	private JFrame frame;
	public Dialog dialog = new Dialog();
	public LogPanel logPanel = new LogPanel(this);
	public State state = new State();
	public UserInfo user = new UserInfo();
	public CharacterRunLabel characterRunLabel = new CharacterRunLabel();
	public Character player = new Character();
	public int playerX;
	public int playerY;
	public int movingX = 0;
	public int movingY = 0;
	private Timer timer;
	private Timer addBlood;
	private Floors floors;
	public int maxHP = 10;
	public int HP = 10;
	public static BufferedImage[][] map = new BufferedImage[31][57];
	public ArrayList<Object> objs = new ArrayList<Object>();
	public ArrayList<Monster> monsters = new ArrayList<Monster>();

	public static Image bloods[][] = {
			{ new Image("barBack_horizontalLeft.png"), new Image("barBack_horizontalMid.png"),
					new Image("barBack_horizontalRight.png") },
			{ new Image("barRed_horizontalLeft.png"), new Image("barRed_horizontalMid.png"),
					new Image("barRed_horizontalRight.png") } };
	public static BufferedImage hit[] = { null, null, null, null, null, null, null, null, null, null };

	private Timer fetchTimer = new Timer(2000, new FetchState());
	private HttpRequest fetch;

	public class FetchState implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				fetch = new HttpRequest(Host.url + "update", "POST");
				fetch.haveBody = true;
				fetch.body = String.format("account=%s&x=%d&y=%d", user.getAccount(), movingX, movingY);
				fetch.start();
			} catch (MalformedURLException e1) {
			}
		}
	}

	public class AddBlood implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			HP++;
		}
	}

	public GamePanel(JFrame frame) throws IOException, FontFormatException {
		this.frame = frame;
		super.setLayout(null);
		this.add(logPanel);
		Label esc = new Label("Escµ²§ô¹CÀ¸");
		esc.set(Fonts.normalFont);
		esc.set(8, (int) (height - Fonts.fontSize - 8), Fonts.fontSize * 7, Fonts.fontSize);
		this.add(esc);

		timer = new Timer(80, characterRunLabel);
		addBlood = new Timer(5000, new AddBlood());
		this.add(characterRunLabel);
		timer.start();

		frame.addKeyListener(this);

		BufferedImage maps = new Image("roguelikeSheet_transparent.png").getBuffered();
		for (int i = 0; i < 31; i++) {
			for (int j = 0; j < 57; j++) {
				map[i][j] = maps.getSubimage(17 * j, 17 * i, 16, 16);
			}
		}
		BufferedImage hits = new Image("hit10.png").getBuffered();
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 5; j++) {
				hit[i * 5 + j] = hits.getSubimage(j * 64, i * 64, 64, 64);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	private void checkMonsters() {
		try {
			String[] result = HttpRequest.async(Host.url + "monsters", "GET").split(",");
			if (result[0].equals("1")) {
				for (int i = 1; i < result.length; i += 8) {
					Monster m = new Monster(this, Integer.parseInt(result[i]), Integer.parseInt(result[i + 1]),
							result[i + 2], Integer.parseInt(result[i + 3]), Integer.parseInt(result[i + 4]),
							Integer.parseInt(result[i + 5]), Integer.parseInt(result[i + 6]),
							Integer.parseInt(result[i + 7]));
					objs.add(m);
					monsters.add(m);
				}
			}
		} catch (IOException e) {
		}
	}

	public void start() throws IOException {
		addMouseListener(this);
		frame.requestFocus();
		state.set(State.START);
		remove(logPanel);
		remove(characterRunLabel);
		player.positionX = (int) (width / 2 - blockSize / 2);
		player.positionY = (int) (height * 3 / 4);
		playerX = (int) (width / 2);
		playerY = player.positionY + blockSize;
		player.width = blockSize;// 48
		player.height = blockSize;
		player.isFocus = true;
		floors = new Floors(map);
		String[] result = HttpRequest.async(Host.url + "trees", "GET").split("@");
		for (int i = 0; i < result.length; i++) {
			Tree t = new Tree(map[10][13], map[11][13]);
			String position[] = result[i].split(",");
			t.y = Integer.parseInt(position[0]);
			t.x = Integer.parseInt(position[1]);
			objs.add(t);
		}

		TopBar topbar = new TopBar(this);
		this.add(topbar);

		checkMonsters();
		floors.moveX(movingX);
		floors.moveY(movingY);
		objs.forEach(obj -> {
			obj.moveX(movingX);
			obj.moveY(movingY);
		});

		timer.stop();
		timer = new Timer(33, this);
		timer.start();
		fetchTimer.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_UP
				|| code == KeyEvent.VK_DOWN) {
			player.state = Character.STAND;
			player.i = 0;
			repaint();
			addBlood.start();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (state.is(State.START)) {
			if (code == KeyEvent.VK_LEFT) {
				player.j = 0;
				player.state = Character.WALK_LEFT;
				addBlood.stop();
			} else if (code == KeyEvent.VK_RIGHT) {
				player.j = 3;
				player.state = Character.WALK_RIGHT;
				addBlood.stop();
			} else if (code == KeyEvent.VK_UP) {
				player.j = 2;
				player.state = Character.WALK_FRONT;
				addBlood.stop();
			} else if (code == KeyEvent.VK_DOWN) {
				player.j = 1;
				player.state = Character.WALK_BACK;
				addBlood.stop();
			}
		}
		if (code == KeyEvent.VK_ESCAPE) {
			System.exit(0);// log out
		}
	}

	public int movingDir = 3;

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(theme[0]);
		g2d.fillRect(0, 0, (int) width, (int) height);
		if (state.is(State.START)) {
			if (player.state == Character.WALK_LEFT) {
				movingX += movingDir;
				player.i = (player.i + 2) % 3;
				floors.moveX(movingDir);
				objs.forEach(obj -> {
					obj.moveX(movingDir);
				});
			} else if (player.state == Character.WALK_RIGHT) {
				movingX -= movingDir;
				player.i = (player.i + 2) % 3;
				floors.moveX(-movingDir);
				objs.forEach(obj -> {
					obj.moveX(-movingDir);
				});
			} else if (player.state == Character.WALK_FRONT) {
				movingY += movingDir;
				player.i = (player.i + 2) % 3;
				floors.moveY(movingDir);
				objs.forEach(obj -> {
					obj.moveY(movingDir);
				});
			} else if (player.state == Character.WALK_BACK) {
				movingY -= movingDir;
				player.i = (player.i + 2) % 3;
				floors.moveY(-movingDir);
				objs.forEach(obj -> {
					obj.moveY(-movingDir);
				});
			}

			floors.paint(g);

			objs.forEach(obj -> {
				if (obj.y <= playerY)
					obj.paint(g);
			});
			player.paint(g);
			objs.forEach(obj -> {
				if (obj.y > playerY)
					obj.paint(g);
			});
		}
	}

	public static boolean isInRange(Monster monster, Character player) {
		return (Math.sqrt(Math.pow(monster.x - (player.positionX + player.width / 2), 2)
				+ Math.pow(monster.y - (player.positionY + player.height), 2)) < ((Object) monster).width / 2
						+ player.width / 2);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = e.getLocationOnScreen();
		boolean inFight = false;
		for (int i = 0; i < monsters.size(); i++) {
			Monster monster = monsters.get(i);
			if (p.y < monster.y && p.x > monster.x - ((Object) monster).width / 2
					&& p.x < monster.x + ((Object) monster).width / 2 && p.y > monster.y - ((Object) monster).height) {
				if (monster.isFocus) {
					if (isInRange(monster, player)) {
						addBlood.stop();
						frame.setCursor(Cursors.attackCursor);
						if (!monster.hiting) {
							monster.hiting = true;
							monster.HP--;
							if (monster.HP == 0) {
								monster.attack.stop();
								monster.remove();
								try {
									String[] result = HttpRequest.async(Host.url + "delete?id=" + monster.id, "GET")
											.split(",");
									if (result[0].equals("1")) {
										Monster m = new Monster(this, Integer.parseInt(result[1]),
												Integer.parseInt(result[2]), result[3], Integer.parseInt(result[4]),
												Integer.parseInt(result[5]), Integer.parseInt(result[6]),
												Integer.parseInt(result[7]), Integer.parseInt(result[8]));
										objs.add(m);
										monsters.add(m);
									}
								} catch (IOException e1) {
								}
								monsters.remove(i);

							}
						}
						inFight = true;
					}
				} else {
					monster.mouseClicked(e);
				}
			} else {
				monster.isFocus = false;
			}
		}
		if (!inFight) {
			frame.setCursor(Cursors.defaultCursor);
		}
		if (monsters.size() == 0) {
			frame.setCursor(Cursors.defaultCursor);
		}
		frame.requestFocus();
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
	}// C:\Users\Alan\Downloads\pixel-line-platformer\Tiles
}