package rpg;

import systemDefault.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.Button;
import GUI.Checkbox;
import GUI.Label;
import GUI.PasswordField;
import GUI.TextField;
import GUI.Dialog;
import Server.Host;
import Server.HttpRequest;
import Server.Image;

@SuppressWarnings("serial")
public class LogPanel extends JPanel implements Screen {
	public Label title;
	public Label accountLabel;
	public TextField account;
	public Label errorAccount;
	public Label passwordLabel;
	public PasswordField password;
	public Label errorPassword;
	public Label passwordAgainLabel;
	public PasswordField passwordAgain;
	public Label errorPasswordAgain;
	public Checkbox check;
	public Button login;
	public Label quistion;

	private Dialog dialog;
	private GamePanel gamePanel;

	public LogPanel(GamePanel gamePanel) throws FontFormatException, IOException {
		dialog = new Dialog();
		this.gamePanel = gamePanel;
		this.setBounds((int) (width / 3), (int) (height / 4), (int) (width * 2 / 3), (int) (height * 3 / 4 - 48));
		setLayout(null);

		Image errorIcon = new Image("red_cross.png");

		title = new Label("登入");
		title.set(new Font("jf open 粉圓 1.1", Font.BOLD, Fonts.fontSize * 3));
		title.setHorizontal(JLabel.CENTER);
		title.set(0, 0, getWidth() / 2, Fonts.fontSize * 3);
		this.add(title);

		accountLabel = new Label("帳號");
		accountLabel.set(Fonts.normalFont);
		accountLabel.setVertical(JLabel.BOTTOM);
		accountLabel.set(0, Fonts.fontSize * 4, getWidth() / 2, Fonts.fontSize * 2);
		this.add(accountLabel);

		account = new TextField();
		account.set(0, Fonts.fontSize * 7, getWidth() / 2, Fonts.fontSize * 4);
		this.add(account);

		errorAccount = new Label("帳號不能為空", errorIcon.getBuffered());
		errorAccount.set(new Color(232, 106, 23));
		errorAccount.set(getWidth() / 2, Fonts.fontSize * 7, Fonts.fontSize * 9 + errorIcon.getWidth(),
				Fonts.fontSize * 4);
		errorAccount.setHorizontal(JLabel.CENTER);
		errorAccount.setVertical(JLabel.CENTER);
		errorAccount.set(false);
		this.add(errorAccount);

		passwordLabel = new Label("密碼");
		passwordLabel.set(Fonts.normalFont);
		passwordLabel.setVertical(JLabel.BOTTOM);
		passwordLabel.set(0, Fonts.fontSize * 12, getWidth() / 2, Fonts.fontSize * 2);
		this.add(passwordLabel);

		password = new PasswordField();
		password.set(0, Fonts.fontSize * 15, getWidth() / 2, Fonts.fontSize * 4);
		this.add(password);

		errorPassword = new Label("密碼不能為空", errorIcon.getBuffered());
		errorPassword.set(new Color(232, 106, 23));
		errorPassword.set(getWidth() / 2, Fonts.fontSize * 15, Fonts.fontSize * 9 + errorIcon.getWidth(),
				Fonts.fontSize * 4);
		errorPassword.setHorizontal(JLabel.CENTER);
		errorPassword.setVertical(JLabel.CENTER);
		errorPassword.set(false);
		this.add(errorPassword);

		passwordAgainLabel = new Label("密碼確認");
		passwordAgainLabel.set(0, Fonts.fontSize * 20, getWidth() / 2, Fonts.fontSize * 2);
		passwordAgainLabel.set(false);
		this.add(passwordAgainLabel);

		passwordAgain = new PasswordField();
		passwordAgain.set(0, Fonts.fontSize * 23, getWidth() / 2, Fonts.fontSize * 4);
		passwordAgain.set(false);
		this.add(passwordAgain);

		errorPasswordAgain = new Label("密碼不一致", errorIcon.getBuffered());
		errorPasswordAgain.set(new Color(232, 106, 23));
		errorPasswordAgain.set(getWidth() / 2, Fonts.fontSize * 23, Fonts.fontSize * 9 + errorIcon.getWidth(),
				Fonts.fontSize * 4);
		errorPasswordAgain.setHorizontal(JLabel.CENTER);
		errorPasswordAgain.setVertical(JLabel.CENTER);
		errorPasswordAgain.set(false);
		this.add(errorPasswordAgain);

		check = new Checkbox("記住帳戶資訊");
		check.setBounds(getWidth() / 4 - check.getWidth() / 2, Fonts.fontSize * 28, check.getWidth(),
				check.getHeight());
		this.add(check);

		login = new Button("確定");
		login.setFont(Fonts.normalFont);
		login.setBounds(getWidth() / 4 - login.getWidth() / 2, Fonts.fontSize * 29 + check.getHeight(),
				login.getWidth(), Math.max(login.getHeight(), Fonts.fontSize));
		login.addMouseListener(new LogEvent());
		this.add(login);

		quistion = new Label("沒有帳號?註冊");
		quistion.set(Fonts.normalFont);
		quistion.setCursor(Cursors.handCursor);
		quistion.setHorizontal(JLabel.CENTER);
		quistion.set(0, Fonts.fontSize * 30 + login.getHeight() + check.getHeight(), getWidth() / 2,
				Fonts.fontSize * 3);
		quistion.addMouseListener(new Switch());
		this.add(quistion);
		boolean is = Files.exists("account.txt");
		if (is) {
			FileReader temp = new FileReader("account.txt");
			int ch;
			String end = "";
			while ((ch = temp.read()) != -1) {
				end = end + (new StringBuilder().append("").append((char) ch).toString());
			}
			temp.close();
			String[] info = end.split(",");
			account.setText(info[0]);
			password.setText(info[1]);
			check.isChecked = true;
			check.reCheck();
			check.repaint();
		}
	}

	/*---------- Events ----------*/
	public class Switch extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (gamePanel.state.is(State.LOGIN)) {
				passwordAgainLabel.setVisible(true);
				passwordAgain.setVisible(true);
				title.setText("註冊");
				quistion.setText("有帳號?登入");
				gamePanel.state.set(State.LOGUP);
			} else {
				passwordAgainLabel.setVisible(false);
				passwordAgain.setVisible(false);
				title.setText("登入");
				quistion.setText("沒有帳號?註冊");
				gamePanel.state.set(State.LOGIN);
				errorPasswordAgain.set(false);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			quistion.setForeground(theme[1]);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			quistion.setForeground(Color.white);
		}
	}

	public class LogEvent extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			String accountText = account.getText();
			boolean inError = false;
			if (accountText.equals("")) {
				errorAccount.set(true);
				inError = true;
			} else {
				errorAccount.set(false);
			}
			String passwordText = new String(password.getPassword());
			if (passwordText.equals("")) {
				errorPassword.set(true);
				inError = true;
			} else {
				errorPassword.set(false);
			}
			if (gamePanel.state.is(State.LOGIN)) {
				if (!inError) {
					try {
						String result[] = HttpRequest.async(Host.url + "login", "POST",
								String.format("account=%s&password=%s", accountText, passwordText)).split(",");
						if (result[0].equals("1")) {
							gamePanel.user.setAccount(accountText);
							gamePanel.user.setPassword(passwordText);
							dialog.show("登入成功", "歡迎!!!");
							if (result[1].equals("1")) {
								if (check.isChecked) {
									FileWriter temp = new FileWriter("account.txt");
									temp.write(String.format("%s,%s", accountText, passwordText));
									temp.close();
								}
								gamePanel.user.name = result[2];
								gamePanel.user.level = Integer.valueOf(result[3]);
								gamePanel.user.exp = Integer.valueOf(result[4]);
								gamePanel.user.attack = Integer.valueOf(result[5]);
								gamePanel.movingX = Integer.valueOf(result[6]);
								gamePanel.movingY = Integer.valueOf(result[7]);
								gamePanel.start();
							} else {
								signCharacter();
							}
						} else if (result[0].equals("0")) {
							dialog.show("登入失敗", result[1]);
						}
					} catch (IOException e1) {
						dialog.show("錯誤", e1.getMessage());
						// e1.printStackTrace();
					}
				}
			} else if (gamePanel.state.is(State.LOGUP)) {
				String passwordAgainText = new String(passwordAgain.getPassword());
				if (passwordAgainText.equals("")) {
					errorPasswordAgain.setText("欄位不能為空");
					errorPasswordAgain.set(true);
					inError = true;
				} else {
					errorPasswordAgain.set(false);
				}
				if (!inError) {
					if (passwordText.equals(passwordAgainText)) {
						try {
							String result[] = HttpRequest
									.async(Host.url + "signUp", "POST",
											String.format("account=%s&password=%s", accountText, passwordText))
									.split(",");
							if (result[0].equals("1")) {
								dialog.show("註冊成功", "歡迎加入");
							} else if (result[0].equals("0")) {
								dialog.show("註冊失敗", result[1]);
							}
						} catch (IOException e1) {
							dialog.show("錯誤", e1.getMessage());
						}
					} else {
						errorPasswordAgain.setText("密碼不一致");
						errorPasswordAgain.set(true);
					}
				}
			} else if (gamePanel.state.is(State.SIGN_CHARACTER)) {
				String name = account.getText();
				if (name.equals("")) {
					dialog.show("錯誤", "名稱不能為空");
					return;
				}
				try {
					String result[] = HttpRequest.async(Host.url + "signCharacter", "POST",
							String.format("account=%s&name=%s", gamePanel.user.getAccount(), name)).split(",");
					if (result[0].equals("1")) {
						dialog.show("創建成功", "歡迎 " + name);
						gamePanel.user.name = name;
						gamePanel.start();
					} else if (result[0].equals("0")) {
						dialog.show("錯誤", result[1]);
					}
				} catch (IOException e1) {
					dialog.show("錯誤", e1.getMessage());
				}
			}
		}
	}

	public class SelectCharacter extends JLabel {
		Character p;

		public SelectCharacter() {
			p = new Character();
			p.state = Character.STAND;
			p.positionX = 0;
			p.positionY = 0;
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			p.paint(g);
		}
	}

	public Character character;

	public class changeCharacterEvent extends MouseAdapter {
		private int move;

		public changeCharacterEvent(int move) {
			this.move = move;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			character.k = (character.k + move) % 6;
			repaint();
		}
	}

	private void signCharacter() {
		gamePanel.state.set(State.SIGN_CHARACTER);
		accountLabel.setText("名稱");
		account.setText("");

		Image preImage = new Image("arrowBeige_left.png");
		int Y = (Fonts.fontSize * 18 + check.getHeight()) / 2 + Fonts.fontSize * 11;

		Button pre = new Button("");
		pre.setBounds(0, Y - preImage.getHeight() / 2, preImage.getWidth(), preImage.getHeight());
		pre.set(preImage, preImage);

		Image nextImage = new Image("arrowBeige_right.png");
		Button next = new Button("");
		next.setBounds((int) (getWidth() / 2 - nextImage.getWidth()), Y - nextImage.getHeight() / 2,
				nextImage.getWidth(), nextImage.getHeight());
		next.set(nextImage, nextImage);

		character = new Character(0, 0, 1);
		character.positionX = getWidth() / 4 - 40;
		character.positionY = Y - 40;
		character.width = 80;
		character.height = 80;
		pre.addMouseListener(new changeCharacterEvent(5));
		next.addMouseListener(new changeCharacterEvent(1));
		this.add(pre);
		this.add(next);

		title.setText("創建角色");
		errorAccount.set(false);
		passwordLabel.set(false);
		password.set(false);
		errorPassword.set(false);
		passwordAgainLabel.set(false);
		passwordAgain.set(false);
		errorPasswordAgain.set(false);
		check.setVisible(false);
		quistion.set(false);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(theme[0]);
		g2d.fillRect(0, 0, (int) width, (int) height);
		if (gamePanel.state.is(State.SIGN_CHARACTER))
			character.paint(g);
	}
}
