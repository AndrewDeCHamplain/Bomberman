package client;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ClientReceive implements Runnable {

	int receivePort;
	public static ArrayList<ArrayList<Character>> tileMap;

	public ClientReceive(int port) {
		receivePort = port + 1;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = null;
		MulticastSocket multicastSocket = null;
		InetAddress group = null;


		// Create multicasting socket for receiving gameview updates
		try {
			group = InetAddress.getByName("224.224.224.224");
			multicastSocket = new MulticastSocket(receivePort);
			multicastSocket.joinGroup(group);
			multicastSocket.setReuseAddress(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			multicastSocket.close();
			e.printStackTrace();
		}
		System.out.println("Socket made");
		
		// Start menu, waiting for a client to connect
		while (Client.startLobby) {
			receiveData = new byte[1024];
			System.out.println("In game lobby");
			try {
				receivePacket = new DatagramPacket(receiveData,
						receiveData.length, group, receivePort);
				multicastSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				multicastSocket.close();
				e.printStackTrace();
			}
			String received = new String(receivePacket.getData()).trim();
			startLobbyLogic(received);

		}
		
		
		//Create first instance of game board
		receiveData = new byte[1024];
		try {
			multicastSocket.receive(receivePacket);
			String tileMapString = new String(receivePacket.getData());
			
			// update the tileMap 
			tileMap = stringToArray(tileMapString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			multicastSocket.close();
			e.printStackTrace();	
		}
		Thread gameThread = new Thread(new GameView(tileMap, Client.playerNum));
		gameThread.start();
		
		
		
		// continuously receives and prints game board
		while(true){
			receiveData = new byte[1024];
			
			try {
				multicastSocket.receive(receivePacket);
				String tileMapString = new String(receivePacket.getData());
				
				// update the tileMap 
				tileMap = stringToArray(tileMapString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				multicastSocket.close();
				e.printStackTrace();	
			}
		}
	}
	
	
	public void startLobbyLogic(String received){
		System.out.println("Received "+received);
		
		if (received.equals("1") || received.equals("2") || received.equals("3")) {
			//System.out.println("You are player " + Client.playerNum);
			Client.playerNum = received.toCharArray()[0];
			Client.keyInputPort = Integer.valueOf(received)+3333;
		}
		else if (received.equals("4")) {
			System.out.println("You are player 4, game is now starting");
			Client.keyInputPort = 3338;
		}
		else if(received.equals("no players")){
			System.out.println("Not enough players to start");
		}
		else if(received.equals("starting")){
			Client.startLobby = false;
			System.out.println("Game now starting");
		}
		else {
			//Thread gameThread = new Thread(new GameView(tileMap, Client.playerNum));
		}
	}
	
	// this method converts our array to a CSV string format where each level of
	// the array is delimited by "|"
	public ArrayList<ArrayList<Character>> stringToArray(String s) {
		ArrayList<ArrayList<Character>> tileMapInternal = new ArrayList<ArrayList<Character>>();
		ArrayList<String> tempMap = new ArrayList<String>();
		
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
