package io;

import java.awt.FileDialog;
import java.awt.Frame;

// prikaze file dialog i vrati izabranu putanju/null
public class FileChooser {

	public String choose(Frame owner, String title, int mode) {
		FileDialog fd = new FileDialog(owner, title, mode);
		fd.setVisible(true);
		if (fd.getFile() == null)
			return null;
		return fd.getDirectory() + fd.getFile();
	}

}
