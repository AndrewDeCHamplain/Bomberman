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
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Server {

	private String arrayString = null;
	// private static InetAddress group = null;
	// private static MulticastSocket multicastSocket = null;
	private boolean inGame, full;
	private Thread inputThread1, inputThread2, inputThread3, inputThread4;
	private GameEngine engine;
	private ArrayList<InetAddress> connections;
	private DatagramSocket serverSocket;

	/**
	 * @param args
	 *            [0] -> port number
	 */
	public Server() {
		// TODO Auto-generated method stub
		connections = new ArrayList<InetAddress>();
		serverSocket = null;
		inGame = true;
		full = false;
		engine = null;
		DatagramPacket receivePacket = null;
		DatagramPacket sendPacket = null;
		int port1 = 3333;
		final int port2 = 3334;
		byte[] receiveData, sendData;
		int numPlayers = 0;
		Semaphore semNewMessage = new Semaphore(0);

		InetAddress IPAddress;

		try {
			serverSocket = new DatagramSocket(port1);
			serverSocket.setReuseAddress(true);

			// group = InetAddress.getByName("224.224.224.224");
			// multicastSocket = new MulticastSocket(port2);
			// multicastSocket.joinGroup(group);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Boolean starting = false;
		int numReadyPlayers = 0;
		String startGame = "";

		while (!starting) {
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
				e1.printStackTrace();
			}
			IPAddress = receivePacket.getAddress();
			if (startGame.equals("spectate")) {
				connections.add(IPAddress);
			}
			if (startGame.equals("join") && numPlayers < 4) {
				numPlayers++;
				if (numPlayers == 4)
					full = true;
				// get address from who sent packet

				System.out.println(receivePacket.getAddress().getHostAddress()
						+ " joined the game.");
				int port = receivePacket.getPort();

				// create new server thread to handle new clients inputs
				if (numPlayers == 1) {
					inputThread1 = new Thread(new ServerInputThread(
							getNextPort(numPlayers), numPlayers, semNewMessage, this, engine));
					inputThread1.start();
				} else if (numPlayers == 2) {
					inputThread2 = new Thread(new ServerInputThread(
							getNextPort(numPlayers), numPlayers, semNewMessage, this, engine));
					inputThread2.start();
				} else if (numPlayers == 3) {
					inputThread3 = new Thread(new ServerInputThread(
							getNextPort(numPlayers), numPlayers, semNewMessage, this, engine));
					inputThread3.start();
				} else if (numPlayers == 4) {
					inputThread4 = new Thread(new ServerInputThread(
							getNextPort(numPlayers), numPlayers, semNewMessage, this, engine));
					inputThread4.start();
				}
				connections.add(IPAddress);
				String temp = String.valueOf(numPlayers);
				sendData = temp.getBytes();

				// send player their number and what server they will talk to
				sendPacket = new DatagramPacket(sendData, sendData.length,
						IPAddress, port);
				try {
					serverSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					serverSocket.close();
					e.printStackTrace();
				}
			}
			if (startGame.equals("join") && full) {

				String temp = "full";
				sendData = temp.getBytes();
				int port = receivePacket.getPort();
				sendPacket = new DatagramPacket(sendData, sendData.length,
						IPAddress, port);
				try {
					serverSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					serverSocket.close();
					e.printStackTrace();
				}
				System.out.println("Game full, join rejected");
			}
			if (startGame.equals("start") && numPlayers > 0) {
				numReadyPlayers++;
				System.out.println(numReadyPlayers + " Players ready");
				if (numReadyPlayers == numPlayers) {
					starting = true;
					inGame = true;
				}
			}
		}

		/*
		 * Once starting, server makes the GameEngine which makes the GameBoard.
		 * Then "starting" is send on the multicasted socket. Then the server
		 * loops, forever sending the boardarray to the clients at a fixed FPS.
		 */
		final Semaphore semGameDone = new Semaphore(0);
		engine = new GameEngine(numPlayers, semNewMessage, this);
		Thread engineThread = new Thread(engine);
		engineThread.start();

		System.out.println("Game starting");

		sendData = "starting".getBytes();
		for (InetAddress x : connections) {
			sendPacket = new DatagramPacket(sendData, sendData.length, x, port2);
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				serverSocket.close();
				e.printStackTrace();
			}
		}

		// send the board
		sendData = new byte[1024];
		sendData = arrayToString(engine.getGameBoard()).getBytes();

		for (InetAddress x : connections) {
			sendPacket = new DatagramPacket(sendData, sendData.length, x, port2);
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				serverSocket.close();
				e.printStackTrace();
			}
		}

		ScheduledExecutorService ses = Executors
				.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				byte[] sendData = new byte[1024];
				// send the board
				synchronized (engine.getGameBoard()) {
					char[][] temp = engine.getGameBoard();
					if (temp[0][0] == '0' || temp[0][0] == '1'
							|| temp[0][0] == '2' || temp[0][0] == '3'
							|| temp[0][0] == '4') {
						sendData = arrayToString(temp).getBytes();
						for (InetAddress x : connections) {
							DatagramPacket sendPacket = new DatagramPacket(
									sendData, sendData.length, x, port2);
							try {
								serverSocket.send(sendPacket);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								serverSocket.close();
								e.printStackTrace();
							}
						}
						semGameDone.release();
					} else {
						sendData = arrayToString(temp).getBytes();
						for (InetAddress x : connections) {
							DatagramPacket sendPacket = new DatagramPacket(
									sendData, sendData.length, x, port2);
							try {
								serverSocket.send(sendPacket);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								serverSocket.close();
								e.printStackTrace();
							}
						}
					}
				}
			}
		}, 0, 32, TimeUnit.MILLISECONDS); // 32 millisec sends gameboard at 30
											// FPS
		try {
			semGameDone.acquire();

			inGame = false;
			sendData = new byte[1024];
			// send the board
			synchronized (engine.getGameBoard()) {
				sendData = arrayToString(engine.getGameBoard()).getBytes();
				for (InetAddress x : connections) {
					sendPacket = new DatagramPacket(sendData, sendData.length,
							x, port2);
					serverSocket.send(sendPacket);

				}
			}

			
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			serverSocket.close();
		}
		ses.shutdown();
		sendData = new byte[1024];
		sendData = arrayToString(engine.getGameBoard()).getBytes();
		for (InetAddress x : connections) {
			sendPacket = new DatagramPacket(sendData, sendData.length, x, port2);
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				serverSocket.close();
				e.printStackTrace();
			}
		}
		serverSocket.close();
		try {
			engineThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (numPlayers == 1) {
			try {
				inputThread1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (numPlayers == 2) {
			try {
				inputThread1.join();
				inputThread2.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (numPlayers == 3) {
			try {
				inputThread1.join();
				inputThread2.join();
				inputThread3.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (numPlayers == 4) {
			try {
				inputThread1.join();
				inputThread2.join();
				inputThread3.join();
				inputThread4.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private int getNextPort(int numPlayers) {
		if (numPlayers == 1)
			return 3335;
		if (numPlayers == 2)
			return 3336;
		if (numPlayers == 3)
			return 3337;
		else
			return 3338;
	}

	// this method converts our array to a CSV string format where each level of
	// the array is delimited by "|"
	private String arrayToString(char array[][]) {
		arrayString = "";
		for (int i = 0; i < array.length; i++) {
			arrayString = arrayString + array[i][0];
			for (int j = 1; j < array[i].length; j++) {
				arrayString = arrayString + "," + array[i][j];
			}
			arrayString = arrayString + "|";
		}
		return arrayString;
	}

	/**
	 * If player is currently in game
	 * 
	 * @return
	 */
	public boolean getInGame() {
		return inGame;
	}

	/**
	 * set the current status of the game
	 * 
	 * @param b
	 */
	public void setInGame(boolean b) {
		inGame = b;
	}

	public static void main(String[] args) {
		while (true) {
			new Server();
		}
	}
	public GameEngine getGameEngine(){
		return engine;
	}
}
