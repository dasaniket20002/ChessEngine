package game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import utils.NetworkUtils;

public class GameClient extends ClientServer {

	public GameClient(String serverAddress) {
		NetworkUtils.AddressData data = NetworkUtils.AddressData.resolveAddress(serverAddress);
		this.ipAddress = data.getIpAddress();
		this.port = data.getPort();

		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void send(String data) {
		byte[] byte_array = data.getBytes();
		DatagramPacket packet = new DatagramPacket(byte_array, byte_array.length, ipAddress, port);
		try {
			socket.send(packet);
			System.out.println("CS: " + data);
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
			System.out.println("CR: " + new String(byte_array).trim());
		} catch (IOException e) {
			e.printStackTrace();
		}

		recievedString = new String(packet.getData());
		
		return packet;
	}

	private void requestLoginToServer() {
		send(constructPacket("CLIENT", PacketType.LOGIN));
		String recv = new String(recieve().getData()).trim();

		String[] data = recv.split(REGEX);
		PacketType type = PacketType.decode(data[0]);

		if (type == PacketType.LOGIN)
			isConnected = true;
	}

	@Override
	public void tryConnection() {
		if (isConnected)
			return;
		requestLoginToServer();
	}
}
