package GUI;

import javax.swing.JPanel;

import systemDefault.Fonts;
import systemDefault.Screen;

import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Dialog extends JDialog implements Screen {
	private Label title;
	private Label inner;
	private Button btn;

	public class event implements MouseListener {
		private Dialog dialog;

		public event(Dialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			this.dialog.dispose();
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

	private int fontSize;

	public Dialog() {
		super.setModalityType(ModalityType.APPLICATION_MODAL);
		super.setSize(300, 200);
		super.setLocationRelativeTo(null);
		super.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));

		this.fontSize = new JLabel().getFont().getSize();
		JPanel center = new JPanel();
		center.setLayout(new GridBagLayout());
		center.setBackground(theme[0]);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = fontSize * 2;
		c.weightx = 0.2;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 5;
		this.title = new Label("", JLabel.CENTER);
		this.title.setVertical(JLabel.TOP);
		this.title.set(new Font("jf open ¯»¶ê 1.1", Font.BOLD, (int) (fontSize * 1.3)));
		center.add(this.title, c);

		c.gridy = 1;
		this.inner = new Label("", JLabel.CENTER);
		this.inner.setVertical(JLabel.TOP);
		this.inner.set(Fonts.normalFont);
		center.add(this.inner, c);

		c.gridwidth = 1;
		c.gridy = 2;
		center.add(new JLabel(), c);

		c.gridx = 1;
		c.gridwidth = 3;
		btn = new Button("½T©w");
		center.add(btn, c);

		c.gridwidth = 1;
		c.gridx = 4;
		center.add(new JLabel(), c);

		super.add(center);
		this.btn.addMouseListener(new event(this));
	}

	public void show(String title, String innerText) {
		this.title.setText(title);
		this.inner.setText(innerText);
		int width = this.fontSize * innerText.length();
		if (width > 300) {
			super.setSize(width, 200);
		} else {
			super.setSize(300, 200);
		}
		super.setVisible(true);
	}
}
