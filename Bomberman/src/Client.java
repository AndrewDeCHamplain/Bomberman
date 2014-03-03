/**
 * 
 */

/**
 * @author Andrew
 *
 */

import java.io.*;
import java.net.*;

public class Client {

	public static char tileMap[][] = {
			{ 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w',
					'w', 'w', 'w', 'w' },
			{ 'w', 'x', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'x', 'x', 'w' },
			{ 'w', 'x', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'x', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'f', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'f', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'f', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', '1', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'f', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'f', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'x', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'x', 'w' },
			{ 'w', 'x', 'x', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'x', 'x', 'w' },
			{ 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w',
					'w', 'w', 'w', 'w' } };

	/**
	 * @param args
	 *            [0] -> port number
	 */
	public static void main(String[] args) {

		// Thread gameThread = new Thread(new GameView(tileMap, playerNum));
		DatagramPacket sendPacket = null, receivePacket = null;
		char[] playerNum = null;

		// TODO Auto-generated method stub
		
		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		InetAddress IPAddress = null;
		try {
			IPAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		String sentence = "", temp = "";
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		
		String player = "";
		while (true) {
			receiveData = new byte[1024];
			sendData = new byte[1024];
			
			System.out.println("Join game.");
			try {
				sentence = inFromUser.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//String temp = sentence;
			
			if (sentence.equals("join")) {
				System.out.println(IPAddress.getHostAddress()
						+ " trying to join game.");
				sendData = sentence.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length,
						IPAddress, Integer.parseInt(args[0]));
				System.out.println("Joining game.");
				try {
					clientSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				try {
					clientSocket.receive(receivePacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				player = new String(receivePacket.getData());
				playerNum = player.toCharArray();
				System.out.println(IPAddress.getHostAddress() + " is player "
						+ player);
			}
			if (player.equals("4")){
				System.out.println("4 Players, Game Started!");
				Thread gameThread = new Thread(new GameView(tileMap, playerNum));
				break;
			}
			if (sentence.equals("start")) {
				System.out.println(IPAddress.getHostAddress()
						+ " starting game.");
				sendData = sentence.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length,
						IPAddress, Integer.parseInt(args[0]));
				try {
					clientSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				try {
					clientSocket.receive(receivePacket);
					temp = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (!temp.equals("No players")){
					System.out.println("You have started the game!");
					Thread gameThread = new Thread(new GameView(tileMap, playerNum));
					break;
				}
				else{
					System.out.println("No players to start the game!");
				}
			}

		}
		while (true) {

			receiveData = new byte[1024];
			sendData = new byte[1024];

			try {
				sentence = inFromUser.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendData = sentence.getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length,
					IPAddress, Integer.parseInt(args[0]));
			// DatagramPacket sendPacket = new DatagramPacket(sendData,
			// sendData.length, IPAddress, 5293);
			try {
				clientSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				clientSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String tileMapString = new String(receivePacket.getData());
			tileMap = stringToArray(tileMapString);
			System.out.println("From server: " + tileMapString);

			;
			// clientSocket.close();
		}
	}

	// this method converts our array to a CSV string format where each level of
	// the array is delimited by "|"
	public static char[][] stringToArray(String s) {
		char[][] tileMapInternal = null;
		String tempMap[] = null;
		int i = 0;
		int j = 0;
		String[] tokensRow = s.split("|");
		// String[] tokensColumn = s.split(",");

		for (String t : tokensRow) {
			tempMap[i] = t;
			String[] tokensColumn = tempMap[j].split(",");
			for (String y : tokensColumn) {
				tileMapInternal[i][j] = y.toCharArray()[0];
				j++;
			}
			i++;
		}
		return tileMapInternal;
	}

}
