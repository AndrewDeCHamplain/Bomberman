package server;
/**
 * 
 */

/**
 * @author Andrew
 *
 */

import java.io.*;
import java.net.*;

import client.ClientReceive;

public class Server {

	public static char[][] boardArray = {
			{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
			{'w','1','x','f','f','f','f','f','f','f','f','f','f','f','x','2','w'},
			{'w','x','w','f','w','f','w','f','w','f','w','f','w','f','w','x','w'},
			{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
			{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
			{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
			{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
			{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
			{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
			{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
			{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
			{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
			{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
			{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
			{'w','x','w','f','w','f','w','f','w','f','w','f','w','f','w','x','w'},
			{'w','4','x','f','f','f','f','f','f','f','f','f','f','f','x','3','w'},
			{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'} 
			};
	
	public static String arraystring = null;

	/**
	 * @param args
	 *            [0] -> port number
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DatagramSocket serverSocket = null;
		MulticastSocket multicastSocket = null;
		DatagramPacket receivePacket = null;
		DatagramPacket sendPacket = null;
		int port1 = 3333;
		int port2 = 3334;
		byte[] receiveData, sendData;
		int numPlayers = 0;
		InetAddress group = null;
		InetAddress IPAddress;

		Player player1 = null;
		Player player2 = null;
		Player player3 = null;
		Player player4 = null;

		try {
			serverSocket = new DatagramSocket(port1);
			serverSocket.setReuseAddress(true);

			group = InetAddress.getByName("224.224.224.224");
			multicastSocket = new MulticastSocket(port2);
			multicastSocket.joinGroup(group);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		String startGame = "";

		while (!startGame.equals("start") && !(numPlayers > 4)) {
			System.out.println("Waiting for players, players: " + numPlayers);

			receiveData = new byte[1024];
			sendData = new byte[1024];

			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
				startGame = new String(receivePacket.getData(), 0,
						receivePacket.getLength(), "UTF-8");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				serverSocket.close();
				multicastSocket.close();
				e1.printStackTrace();
			}
			if (startGame.equals("join")) {
				numPlayers++;
				
				// get address from who sent packet
				IPAddress = receivePacket.getAddress();
				System.out.println(receivePacket.getAddress().getHostAddress()
						+ " joined the game.");
				port1 = receivePacket.getPort();
				
				// create new server thread to handle new clients inputs
				Thread inputThread = new Thread(new ClientReceive(getNextPort(numPlayers)));
				inputThread.start();
				
				String temp = String.valueOf(numPlayers);
				sendData = temp.getBytes();
				
				// send player their number and what server they will talk to
				sendPacket = new DatagramPacket(sendData, sendData.length,
						IPAddress, port1);
				try {
					serverSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					serverSocket.close();
					e.printStackTrace();
				}
				
				if (numPlayers == 1) {
					player1 = new Player(1, 1, '1', IPAddress);
					//placePlayer(player1);
				} else if (numPlayers == 2) {
					player2 = new Player(16, 1, '2', IPAddress);
					//placePlayer(player2);
				} else if (numPlayers == 3) {
					player3 = new Player(1, 16, '3', IPAddress);
					//placePlayer(player3);
				} else if (numPlayers == 4) {
					player4 = new Player(16, 16, '4', IPAddress);
					//placePlayer(player4);
				}

				

			}

			// If trying to start game without any players
			if (startGame.equals("start") && numPlayers == 0) {
				IPAddress = receivePacket.getAddress();
				// int port = receivePacket.getPort();
				String temp = "No players";
				sendData = temp.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length,
						group, port2);
				try {
					multicastSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					serverSocket.close();
					multicastSocket.close();
					e.printStackTrace();
				}
			}
		}
		// serverSocket.close();

		// Once starting, server makes new multicasting socket
		// int port = multicastSocket.getPort();
		// InetAddress address = multicastSocket.getInetAddress();
		sendData = "starting".getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, group, port2);
		try {
			multicastSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			serverSocket.close();
			multicastSocket.close();
			e.printStackTrace();
		}

		// send the board
		sendData = arrayToString(boardArray).getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, group, port2);
		try {
			multicastSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			serverSocket.close();
			multicastSocket.close();
			e.printStackTrace();
		}
		System.out.println("Game starting");

		while (true) {

			receiveData = new byte[1024];
			sendData = new byte[1024];

			// read a message from socket
			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				serverSocket.close();
				multicastSocket.close();
				e1.printStackTrace();
			}
			String sentence = new String(receivePacket.getData());
			IPAddress = receivePacket.getAddress();
			System.out.println("Received: " + sentence);

			//movePlayer(
			//		whichPlayer(IPAddress, player1, player2, player3, player4),
			//		sentence);

			// get address from who sent packet
			// InetAddress IPAddress = receivePacket.getAddress();
			// port = receivePacket.getPort();

			// send the board
			sendData = arrayToString(boardArray).getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, group,
					port2);
			try {
				multicastSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				serverSocket.close();
				multicastSocket.close();
				e.printStackTrace();
			}
		}
	}

	private static int getNextPort(int numPlayers) {
		if(numPlayers == 1)
			return 3335;
		if(numPlayers == 1)
			return 3336;
		if(numPlayers == 1)
			return 3337;
		else return 3338;
	}

	// this method converts our array to a CSV string format where each level of
	// the array is delimited by "|"
	public static String arrayToString(char array[][]) {
		arraystring = "";
		for (int i = 0; i < array.length; i++) {
			arraystring = arraystring + array[i][0];
			for (int j = 1; j < array[i].length; j++) {
				arraystring = arraystring + "," + array[i][j];
			}
			arraystring = arraystring + "|";
		}
		System.out.println(arraystring);
		return arraystring;
	}

/*
	public static Player whichPlayer(InetAddress ipaddress, Player p1,
			Player p2, Player p3, Player p4) {
		if (p1.getIPAddress().equals(ipaddress))
			return p1;
		else if (p2.getIPAddress().equals(ipaddress))
			return p2;
		else if (p3.getIPAddress().equals(ipaddress))
			return p3;
		else if (p4.getIPAddress().equals(ipaddress))
			return p4;
		else
			return null;
	}
	*/
}
