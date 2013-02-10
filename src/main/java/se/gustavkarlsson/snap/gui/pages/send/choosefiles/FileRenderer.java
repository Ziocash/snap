package se.gustavkarlsson.snap.gui.pages.send.choosefiles;

import java.awt.Component;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.filechooser.FileSystemView;

@SuppressWarnings("serial")
public class FileRenderer extends DefaultListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		File file = (File) value;
		label.setText(file.getName());
		label.setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));

		return label;
	}
}