package client;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;

public class ClientReceive implements Runnable {

	private int receivePort;
	Semaphore semStarting;
	public static ArrayList<ArrayList<Character>> tileMap;

	public ClientReceive(int port, Semaphore semaphore) {
		receivePort = port + 1;
		semStarting = semaphore;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = null;
		MulticastSocket multicastSocket = null;
		InetAddress group = null;
		Semaphore semaphore = new Semaphore(0);

		startLobbyLogic(String.valueOf(Client.playerNum).trim());

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
		Thread gameLobbyThread = new Thread(new GameLobby());
		gameLobbyThread.start();
		// Start menu, waiting for a client to connect
		while (Client.startLobby) {
			receiveData = new byte[1024];
			
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
		Thread gameThread = new Thread(new GameView(tileMap, Client.playerNum, semaphore, Client.getIsPlayer()));
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
			semaphore.release();
		}
	}
	
	
	public void startLobbyLogic(String received){
		if (received.equals("1") || received.equals("2") || received.equals("3")) {
			//System.out.println("You are player " + Client.playerNum);
			Client.playerNum = received.toCharArray()[0];
			//System.out.println("You are player );
			Client.keyInputPort = Integer.valueOf(received)+3333;
		}
		else if (received.equals("4")) {
			System.out.println("You are player 4, game is now starting");
			Client.keyInputPort = 3338;
		}
		else if(received.equals("no players")){
			semStarting.release();
			System.out.println("Not enough players to start");
		}
		else if(received.equals("starting")){
			semStarting.release();
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
		return tileMapInternal;
	}
}
