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
		MulticastSocket multicastSocket = null;
		DatagramSocket clientSocket = null;
		InetAddress IPAddress = null;
		char[] playerNum = null;
		int port1 = Integer.parseInt(args[0]);
		int port2 = port1+1;
		InetAddress group = null;
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];

		// TODO Auto-generated method stub
		try {
			clientSocket = new DatagramSocket();
			
			group = InetAddress.getByName("224.224.224.224");
			multicastSocket = new MulticastSocket(port2);
			multicastSocket.joinGroup(group);
			multicastSocket.setReuseAddress(true);
		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			IPAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		
		String player = "", starting = "", sentence = "", temp = "";

		
		while (!starting.equals("starting")) {
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
				DatagramPacket packet = null;
				String port1Addr = IPAddress.getHostAddress();
				System.out.println(port1Addr
						+ " trying to join game.");
				sendData = sentence.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length,
						IPAddress, port1);
				System.out.println("Joining game.");
				try {
					clientSocket.send(sendPacket);
					
					packet = new DatagramPacket(receiveData, receiveData.length, group, port2);
					multicastSocket.receive(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				player = new String(packet.getData());
				playerNum = player.toCharArray();
				System.out.println(port1Addr + " is player "
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
						IPAddress,port1);
				try {
					clientSocket.send(sendPacket);
					
					DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length, group, port2);
					multicastSocket.receive(packet);
					temp = new String(packet.getData());
					System.out.println(temp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (temp.trim().equals("starting")){
					System.out.println("You have started the game!");
					Thread gameThread = new Thread(new GameView(tileMap, playerNum));
					starting = "starting";
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
					IPAddress, port1);
			// DatagramPacket sendPacket = new DatagramPacket(sendData,
			// sendData.length, IPAddress, 5293);
			try {
				clientSocket.send(sendPacket);
				
				DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length, group, port2);
				multicastSocket.receive(packet);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String tileMapString = new String(receivePacket.getData());
			tileMap = stringToArray(tileMapString);
			System.out.println("From server: " + tileMapString);

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