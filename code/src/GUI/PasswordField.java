package GUI;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;

import systemDefault.Cursors;
import systemDefault.Fonts;
import systemDefault.Screen;

@SuppressWarnings("serial")
public class PasswordField extends JPasswordField implements Screen, FocusListener {
	public PasswordField() {
		super();
		setBackground(theme[0]);
		setForeground(Fonts.color);
		setCaretColor(Fonts.color);
		setFont(Fonts.normalFont);
		setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(100, 100, 100)));
		addFocusListener(this);
		setCursor(Cursors.defaultCursor);
	}

	public void set(int x, int y, int w, int h) {
		setBounds(x, y, w, h);
	}

	public void set(boolean v) {
		setVisible(v);
	}

	@Override
	public void focusGained(FocusEvent e) {
		setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, theme[1]));
	}

	@Override
	public void focusLost(FocusEvent e) {
		setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(100, 100, 100)));
	}
}
