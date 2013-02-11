package se.gustavkarlsson.snap.gui.pages.send.advancedoptions;

import java.awt.Component;
import java.net.InetAddress;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import se.gustavkarlsson.snap.util.NetworkUtils;

@SuppressWarnings("serial")
class NetworkAddressComboBoxRenderer extends BasicComboBoxRenderer{

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setFont(list.getFont());

		if (value instanceof InetAddress) {
			InetAddress address = (InetAddress) value;
			setText(NetworkUtils.getInetAddressHumanFriendlyName(address));
		}

		return this;
	}

}
