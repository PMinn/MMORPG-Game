package systemDefault;

import java.io.File;

public class Files {
	public static boolean exists(String fileName) {
		File f = new File(fileName);
		if (!f.exists() || f.isDirectory()) {
			return false;
		}
		return true;
	}
}
