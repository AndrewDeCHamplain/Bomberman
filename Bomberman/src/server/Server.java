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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {
	
	private static String arrayString = null;
	private static InetAddress group = null;
	private static MulticastSocket multicastSocket = null;

	/**
	 * @param args
	 *            [0] -> port number
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DatagramSocket serverSocket = null;
		
		DatagramPacket receivePacket = null;
		DatagramPacket sendPacket = null;
		int port1 = 3333;
		final int port2 = 3334;
		byte[] receiveData, sendData;
		int numPlayers = 0;
		
		InetAddress IPAddress;

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
				int port = receivePacket.getPort();
				
				// create new server thread to handle new clients inputs
				Thread inputThread = new Thread(new ServerInputThread(getNextPort(numPlayers), numPlayers));
				inputThread.start();
				
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
		serverSocket.close(); // server no longer receives any data
 
		/* Once starting, server makes the GameEngine which makes the GameBoard.
		*  Then "starting" is send on the multicasted socket.
		*  Then the server loops, forever sending the boardarray to the clients
		*  at a fixed FPS.
		*/
		
		Thread engineThread = new Thread(new GameEngine(numPlayers));
		engineThread.start();
		
		System.out.println("Game starting");

		sendData = "starting".getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, group, port2);
		try {
			multicastSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			multicastSocket.close();
			e.printStackTrace();
		}

		// send the board
		sendData = arrayToString(GameEngine.getGameBoard()).getBytes();
		sendPacket = new DatagramPacket(sendData, sendData.length, group, port2);
		try {
			multicastSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			multicastSocket.close();
			e.printStackTrace();
		}
		
			ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
			ses.scheduleAtFixedRate(new Runnable() {
			    @Override
			    public void run() {
			    	byte[] sendData = new byte[1024];

					// send the board
					sendData = arrayToString(GameEngine.getGameBoard()).getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, group,
							port2);
					try {
						multicastSocket.send(sendPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						multicastSocket.close();
						e.printStackTrace();
					}
			    }
			}, 0, 1, TimeUnit.SECONDS); // sends gameboard every 1 second
			//ses.shutdown();
			
			while(true);
	}

	private static int getNextPort(int numPlayers) {
		if(numPlayers == 1)
			return 3335;
		if(numPlayers == 2)
			return 3336;
		if(numPlayers == 3)
			return 3337;
		else return 3338;
	}

	// this method converts our array to a CSV string format where each level of
	// the array is delimited by "|"
	private static String arrayToString(char array[][]) {
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
}
