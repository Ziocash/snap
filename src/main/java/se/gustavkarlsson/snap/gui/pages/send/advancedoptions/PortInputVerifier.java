package se.gustavkarlsson.snap.gui.pages.send.advancedoptions;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import se.gustavkarlsson.snap.util.NetworkUtils;

class PortInputVerifier extends InputVerifier {

	@Override
	public boolean verify(JComponent input) {

		boolean valid = false;

		if (input instanceof JTextComponent) {
			String text = ((JTextComponent) input).getText();
			if (text != null) {
				int port;
				try {
					port = Integer.parseInt(text);

					if ((port >= NetworkUtils.getMinimumValidPort()) && (port <= NetworkUtils.getMaximumValidPort())) {
						valid = true;
					}
				} catch (NumberFormatException e) {
				}
			}
		}

		return valid;
	}
}
