package game.handlers;

import java.util.Scanner;

import game.net.ClientServer;
import game.net.GameClient;
import game.net.GameServer;

public class NetworkHandler {

	private static NetworkHandler instance = null;

	public static NetworkHandler getInstance() {
		if (instance == null)
			instance = new NetworkHandler();
		return instance;
	}

	private ClientServer network;

	public void init(boolean isServer, Scanner sc) {
		if (isServer) {
			network = new GameServer();
		} else {
			System.out.print("Server IP : ");
			String s = sc.next();
			sc.close();

			network = new GameClient(s);
		}
	}

	public ClientServer getNetwork() {
		return network;
	}

}
