package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.Semaphore;

public class ServerInputThread implements Runnable {

	private int player;
	private int receivePort;
	private Semaphore semNewMessage;
	private Server server;
	private GameEngine gameEngine;

	/**
	 * assigns specified paramters to field variables
	 * @param port
	 * @param player
	 * @param semNewMessage
	 */
	public ServerInputThread(int port, int player, Semaphore semNewMessage, Server server, GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		this.server = server;
		receivePort = port;
		this.player = player;
		this.semNewMessage = semNewMessage;
	}

	@Override
	/**
	 * Runs the Servers Input Thread by initializing sockets and packets alike.
	 * Checks if the players are able to get in game. If yes, then the communication
	 * link is established and packets are sent through.
	 */
	public void run() {
		// TODO Auto-generated method stub
		DatagramSocket serverSocket = null;
		DatagramPacket receivePacket = null;
		byte[] receivedData = new byte[1024];

		try {
			serverSocket = new DatagramSocket(receivePort);
			serverSocket.setReuseAddress(true);
			receivePacket = new DatagramPacket(receivedData,
					receivedData.length);
			serverSocket.receive(receivePacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			serverSocket.close();
			e.printStackTrace();
		}
		gameEngine = server.getGameEngine();
		while (server.getInGame()) {
			receivedData = new byte[1024];
			
			semNewMessage.release();
			synchronized (gameEngine.command){
				gameEngine.command = new String(receivePacket.getData()).trim()+","+player;
			}
			try {
				receivePacket = new DatagramPacket(receivedData,
						receivedData.length);
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		serverSocket.close();
	}
}
