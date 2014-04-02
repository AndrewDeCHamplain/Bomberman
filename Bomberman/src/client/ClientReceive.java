package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;

public class ClientReceive implements Runnable {

	private int receivePort;
	private Semaphore semStarting;
	private ArrayList<ArrayList<Character>> tileMap;
	private Client client;
	private GameLobby gameLobby;
	private GameView gameView;
	private InetAddress serverAddress;

	public ClientReceive(int port, Semaphore semaphore, Client client, InetAddress serverAddress) {
		this.serverAddress = serverAddress;
		receivePort = port + 1;
		semStarting = semaphore;
		this.client = client;
		tileMap = null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = null;
		DatagramSocket receiveSocket = null;
		//MulticastSocket multicastSocket = null;
		//InetAddress group = null;
		Semaphore newReceive = new Semaphore(0);

		// Create multicasting socket for receiving gameview updates
		try {
			receiveSocket = new DatagramSocket(receivePort);
			receiveSocket.setReuseAddress(true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			receiveSocket.close();
			e.printStackTrace();
		}

		Thread gameLobbyThread = new Thread(gameLobby = new GameLobby(client));
		gameLobbyThread.start();

		// Start menu, waiting for a client to connect
		while (client.getStartLobby()) {
			receiveData = new byte[1024];
			try {
				receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				receiveSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				receiveSocket.close();
				e.printStackTrace();
			}
			String received = new String(receivePacket.getData()).trim();
			startLobbyLogic(received);
		}

		// Create first instance of game board
		receiveData = new byte[1024];
		try {
			receiveSocket.receive(receivePacket);
			String tileMapString = new String(receivePacket.getData());
			// update the tileMap
			tileMap = stringToArray(tileMapString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			receiveSocket.close();
			e.printStackTrace();
		}
		gameView = new GameView(tileMap, client.getPlayerNum(), newReceive,
				client.getIsPlayer(), client, gameLobby, this);
		Thread gameThread = new Thread(gameView);
		gameThread.start();

		// continuously receives and prints game board
		while (client.getInGame()) {
			receiveData = new byte[1024];
			synchronized (gameThread) {
				try {
					receiveSocket.receive(receivePacket);
					String tileMapString = new String(receivePacket.getData());

					// update the tileMap
					tileMap = stringToArray(tileMapString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					receiveSocket.close();
					e.printStackTrace();
				}

				newReceive.release();
				/*
				if (client.getIsPlayer()) {
					if (gameView.getBoardElement(client.getPosition()[1],
							client.getPosition()[0]) == client.getPrev()) {
						client.setPrev('.');
						long stopTime = System.currentTimeMillis();
						long elapsedTime = stopTime - client.getTimer();
						System.out.println("Player " + client.getPlayerNum()
								+ ": " + "Latency: " + elapsedTime);
					}
				}
				*/
			}
		}
		receiveSocket.close();
	}

	public void startLobbyLogic(String received) {
		if (received.equals("1") || received.equals("2")
				|| received.equals("3") || received.equals("4")) {
			client.setPlayerNum(received.toCharArray()[0]);
			client.setKeyInputPort(Integer.valueOf(received) + 3333);
		} else if (received.equals("full")) {
			client.setIsFull(true);
			gameLobby.makeMessageDialog(String
					.format("Game is full, you may still spectate though"));
		} else if (received.equals("starting")) {
			semStarting.release();
			client.setStartLobby(false);
			System.out.println("Game now starting");
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
		for (int i = 0; i < tempMap.size(); i++) {
			ArrayList<Character> row = new ArrayList<Character>();
			StringTokenizer st2 = new StringTokenizer((String) tempMap.get(i),
					",");

			while (st2.hasMoreElements()) {
				row.add(((String) st2.nextElement()).charAt(0));
			}
			tileMapInternal.add(row);
		}
		return tileMapInternal;
	}

	public GameLobby getGameLobby() {
		return gameLobby;
	}

	public GameView getGameView() {
		return gameView;
	}

	public ArrayList<ArrayList<Character>> getTileMap() {
		// TODO Auto-generated method stub
		return tileMap;
	}
}
