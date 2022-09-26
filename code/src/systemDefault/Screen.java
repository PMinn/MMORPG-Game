package systemDefault;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public interface Screen {
	final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final static double width = screenSize.getWidth();
	public final static double height = screenSize.getHeight();
	public final static Color theme[] = { new Color(45, 45, 47), new Color(211, 214, 217), new Color(157, 34, 250),
			new Color(255, 140, 0), new Color(27, 217, 119), new Color(255, 0, 56) };
	public final static int blockSize = 64;
}
