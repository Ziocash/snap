package se.gustavkarlsson.snap.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class NetworkUtils {

	// TODO: Does one really need to add all these addresses?

	private static final Map<InetAddress, String> addressNameMap = new HashMap<InetAddress, String>();

	static {
		try {
			addressNameMap.put(InetAddress.getByName("0.0.0.0"), "Any (IPv4)");
			addressNameMap.put(InetAddress.getByName("::"), "Any (IPv6)");
			addressNameMap.put(InetAddress.getByName("127.0.0.1"), "Localhost (IPv4)");
			addressNameMap.put(InetAddress.getByName("::1"), "Localhost (IPv6)");
		} catch (UnknownHostException e) {
		}
	}

	public static List<InetAddress> listInetAddresses() {
		List<InetAddress> addresses = new ArrayList<InetAddress>();

		// Any IPv4
		try {
			addresses.add(InetAddress.getByName("0.0.0.0"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Any IPv6
		try {
			addresses.add(Inet6Address.getByName("::"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Loopback IPv4
		try {
			addresses.add(InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Loopback IPv6
		try {
			addresses.add(InetAddress.getByName("::1"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// The rest
		try {
			addresses
			.addAll(Arrays.asList(InetAddress.getAllByName(InetAddress.getLocalHost().getCanonicalHostName())));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return addresses;
	}

	public static String getInetAddressHumanFriendlyName(InetAddress address) {
		String humanFriendlyName = addressNameMap.get(address);
		if (humanFriendlyName == null) {
			humanFriendlyName = address.getHostAddress();
		}
		return humanFriendlyName;
	}

	public static int getMaximumValidPort() {
		return 65535;
	}

	public static int getMinimumValidPort() {
		return 1024;
	}
}
