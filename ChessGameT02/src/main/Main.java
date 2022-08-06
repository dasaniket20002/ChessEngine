package main;

import java.util.Scanner;

import game.handlers.GameHandler;
import game.handlers.NetworkHandler;

public class Main {

	public static void main(String args[]) {

		Scanner sc = new Scanner(System.in);
		System.out.println("1. Server");
		System.out.println("2. Client");
		int ch = sc.nextInt();

		NetworkHandler.getInstance().init(ch == 1, sc);

		System.out.println("Waiting for connection...");
		while (!NetworkHandler.getInstance().getNetwork().isConnected())
			NetworkHandler.getInstance().getNetwork().tryConnection();
		System.out.println("Connected");
		
		GameHandler.getInstance().init(ch == 1);
		GameHandler.getInstance().start();
		
		sc.close();
	}

}
