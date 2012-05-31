package se.gustavkarlsson.snap.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class NetworkUtils {

	public static List<InetAddress> listInetAddresses() {
		List<InetAddress> addresses = new ArrayList<InetAddress>();
		try {
			// Listen on all addresses
			addresses.add(InetAddress.getByAddress(new byte[] { 0, 0, 0, 0 }));

			// Loopback address
			addresses.add(InetAddress.getByName(null));

			InetAddress localhost = InetAddress.getLocalHost();
			addresses.addAll(Arrays.asList(InetAddress.getAllByName(localhost
					.getCanonicalHostName())));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addresses;
	}
}
