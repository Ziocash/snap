package se.gustavkarlsson.snap.gui.pages.send.advancedoptions.old;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import org.eclipse.jface.viewers.LabelProvider;

import se.gustavkarlsson.snap.resources.Strings;

public class Inet4AddressLabelProvider extends LabelProvider {
	
	private static final int MAX_NETWORK_INTERFACE_NAME_LENGTH = 40;

	@Override
	public String getText(Object element) {
		if (element instanceof Inet4Address) {
			InetAddress address = (InetAddress) element;
			String label = address.getHostAddress();

			try {
				NetworkInterface networkInterface = NetworkInterface
						.getByInetAddress(address);
				String networkInterfaceName = networkInterface == null ? "All interfaces"
						: networkInterface.getDisplayName();

				if (networkInterfaceName.length() > MAX_NETWORK_INTERFACE_NAME_LENGTH) {
					networkInterfaceName = networkInterfaceName.substring(0, MAX_NETWORK_INTERFACE_NAME_LENGTH - 3) + "...";
				}
				
				label += " (" + networkInterfaceName + ")";
			} catch (SocketException e) {
				// TODO Log warn
				e.printStackTrace();
			}
			return label;
		}
		throw new IllegalArgumentException(Strings.ILLEGAL_ARGUMENT_TYPE + ": "
				+ element.getClass().getCanonicalName());
	}
}
