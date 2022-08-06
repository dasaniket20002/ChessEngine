package game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import utils.NetworkUtils;

public class GameServer extends ClientServer {

	public GameServer() {
		NetworkUtils.AddressData data = NetworkUtils.AddressData.generateNew();
		data.setPort(49000);
		int serverPort = data.getPort();
		try {
			this.socket = new DatagramSocket(serverPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		System.out.println("Server IP : " + data.getIpAddress().getHostAddress() + ":" + data.getPort());
	}

	@Override
	public void send(String data) {
		byte[] byte_array = data.getBytes();
		DatagramPacket packet = new DatagramPacket(byte_array, byte_array.length, ipAddress, port);
		try {
			socket.send(packet);
			System.out.println("SS: " + data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public DatagramPacket recieve() {
		byte[] byte_array = new byte[MAX_PACKET_RECIEVE_SIZE];
		DatagramPacket packet = new DatagramPacket(byte_array, byte_array.length);

		try {
			socket.receive(packet);
			System.out.println("SR: " + new String(byte_array).trim());
		} catch (IOException e) {
			e.printStackTrace();
		}

		recievedString = new String(packet.getData());

		return packet;
	}

	private void searchForLoginRequest() {
		DatagramPacket packet = recieve();
		String recv = new String(packet.getData()).trim();

		String[] data = recv.split(REGEX);
		PacketType recvPacket = PacketType.decode(data[0]);

		if (recvPacket == PacketType.LOGIN) {
			ipAddress = packet.getAddress();
			port = packet.getPort();
			isConnected = true;

			send(constructPacket("SERVER", PacketType.LOGIN));
		}
	}

	@Override
	public void tryConnection() {
		if (isConnected)
			return;
		searchForLoginRequest();
	}
}
