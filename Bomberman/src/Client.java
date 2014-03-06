/**
 * 
 */

/**
 * @author Andrew
 *
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Client {

	
	public static ArrayList<ArrayList<Character>> tileMap = null;
			/*{
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
*/
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
		InetAddress group = null;
		char playerNum = 0;
		int port1 = Integer.parseInt(args[0]);
		int port2 = port1+1;
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		
		// TODO Auto-generated method stub
		try {
			clientSocket = new DatagramSocket();
			
			group = InetAddress.getByName("224.224.224.224");
			multicastSocket = new MulticastSocket(port2);
			multicastSocket.joinGroup(group);
			multicastSocket.setReuseAddress(true);
			
			IPAddress = InetAddress.getByName("localhost");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				playerNum = player.toCharArray()[0];
				System.out.println(port1Addr + " is player "
						+ player);
			}
			if (player.equals("4")){
				System.out.println("4 Players, Game Started!");
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
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (temp.trim().equals("starting")){
					System.out.println("You have started the game!");
					starting = "starting";
				}
				else{
					System.out.println("No players to start the game!");
				}
			}
		}
		
		try {
			receivePacket = new DatagramPacket(receiveData, receiveData.length, group, port2);
			multicastSocket.receive(receivePacket);
			tileMap = stringToArray(new String(receivePacket.getData()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		Thread gameThread = new Thread(new GameView(tileMap, playerNum));
		
		
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
			try {
				clientSocket.send(sendPacket);
				
				DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length, group, port2);
				multicastSocket.receive(packet);
				String tileMapString = new String(receivePacket.getData());
				tileMap = stringToArray(tileMapString);
				System.out.println("From server: " + tileMapString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// clientSocket.close();
		}
	}

	// this method converts our array to a CSV string format where each level of
	// the array is delimited by "|"
	public static ArrayList<ArrayList<Character>> stringToArray(String s) {
		ArrayList<ArrayList<Character>> tileMapInternal = new ArrayList<ArrayList<Character>>();
		ArrayList<String> tempMap = new ArrayList<String>();
		int j=0;	
		
		StringTokenizer st1 = new StringTokenizer(s, "|");
		while (st1.hasMoreElements()) {
			tempMap.add((String) st1.nextElement());
		}
		for(int i=0; i<tempMap.size(); i++){
			ArrayList<Character> row = new ArrayList<Character>();
			StringTokenizer st2 = new StringTokenizer((String) tempMap.get(i), ",");
			
			while (st2.hasMoreElements()){
				row.add(((String) st2.nextElement()).charAt(0));
			}
			tileMapInternal.add(row);
		}
		System.out.println(tileMapInternal);
		return tileMapInternal;
	}
}