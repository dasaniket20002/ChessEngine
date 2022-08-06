package game.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import game.board.Board;

public abstract class ClientServer {

	public static final int MAX_PACKET_RECIEVE_SIZE = 1024;
	public static final String REGEX = ":";

	protected InetAddress ipAddress;
	protected int port;

	protected boolean isConnected = false;

	protected DatagramSocket socket;
	
	protected String recievedString;

	public abstract void send(String data);

	public abstract DatagramPacket recieve();

	public abstract void tryConnection();

	public String constructPacket(String data, PacketType type) {
		String ret = type.getValue() + REGEX + data;
		return ret;
	}

	public void sendBoard(Board board) {
		String boardInfo = board.encodeBoard();
		send(constructPacket(boardInfo, PacketType.BOARD));
	}

	public void recieveBoard(Board board) {
		String packet = new String(recieve().getData()).trim();
		String[] data = packet.split(REGEX);
		if (PacketType.decode(data[0]) == PacketType.BOARD) {
			board.decodeBoard(data[1]);
		}
	}

	public void sendGameOver() {
		String message = constructPacket("CHECKMATE", PacketType.CHECKMATE);
		send(message);
	}
	
	public String getRecievedString()
	{
		return recievedString;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public int getPort() {
		return port;
	}

	public boolean isConnected() {
		return isConnected;
	}
}
