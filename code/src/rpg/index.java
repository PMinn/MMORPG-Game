package rpg;

import javax.swing.JFrame;

import GUI.Dialog;
import Server.Host;
import systemDefault.*;

import java.awt.FontFormatException;
import java.io.IOException;

public class index implements Screen {
	JFrame frame;

	public index() throws FontFormatException {
		new Host();
		new Fonts();
		new Cursors();
		Character.load();
		frame = new JFrame();
		frame.setLayout(null);
		frame.setCursor(Cursors.defaultCursor);
		GamePanel game;
		try {
			game = new GamePanel(frame);

			game.setBounds(0, 0, (int) width, (int) height);
			frame.add(game);
			frame.addMouseListener(game);
		} catch (IOException e) {
			e.printStackTrace();
			Dialog dialog = new Dialog();
			dialog.show("錯誤", "圖片無法載入");
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}

	public static void main(String[] args) throws FontFormatException {
		new index();
	}
}
