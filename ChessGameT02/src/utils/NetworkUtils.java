package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;

import game.net.ClientServer;

public class NetworkUtils {

	public static class AddressData {
		private InetAddress ipAddress;
		private int port;

		public static AddressData generateNew() {
			AddressData ret = new AddressData();
			try {
				ret.setIpAddress(NetworkUtils.getLocalIP());
				ret.setPort(NetworkUtils.generatePort());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ret;
		}

		public static AddressData resolveAddress(String data) {
			String[] split = data.split(ClientServer.REGEX);
			AddressData ret = new AddressData();
			try {
				ret.setIpAddress(InetAddress.getByName(split[0]));
				ret.setPort(Integer.parseInt(split[1]));
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			return ret;
		}

		public InetAddress getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(InetAddress ipAddress) {
			this.ipAddress = ipAddress;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}
	}

	public static InetAddress getPublicIP() throws IOException {
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

		String ip = in.readLine(); // you get the IP as a String
		System.out.println("Public IP Address : " + ip);
		return InetAddress.getByName(ip);
	}
	
	public static InetAddress getLocalIP() throws UnknownHostException {
		return InetAddress.getLocalHost();
	}

	public static int generatePort() {
		int port = -1;
		try {
			ServerSocket ss = new ServerSocket(0);
			port = ss.getLocalPort();
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return port;
	}
}