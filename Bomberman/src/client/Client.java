package client;

/**
 * 
 */

/**
 * @author Andrew
 *
 */

import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

public class Client{

	static char playerNum = 0;
	public static boolean startLobby = true;
	static int keyInputPort;
	static String currMove = "";
	private static boolean isPlayer;

	/**
	 * @param args
	 *            [0] -> port number
	 */
	public Client() {

		DatagramPacket sendPacket = null;
		DatagramSocket clientSocket = null, inputSocket = null;
		InetAddress IPAddress = null;
		int sendPort = 3333;
		boolean joined = false;
		byte[] sendData = new byte[1024];
		Semaphore newReceived = new Semaphore(0);
		
		// TODO Auto-generated method stub
		try {
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName("localhost");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			clientSocket.close();
			e.printStackTrace();
		}

		Thread receiver = new Thread(new ClientReceive(sendPort, newReceived));
		receiver.start();
		System.out.println("Join game.");

		while (startLobby) {
			sendData = new byte[1024];

			try {
				Thread.sleep(100);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			if (currMove.equals("join")) {

				if (joined) { // check if already in game
					System.out.println("You have already joined");
					break;
				}
				joined = true;
				isPlayer = true;
				// make thread to handle board updates from server

				sendData = currMove.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length,
						IPAddress, sendPort);
				System.out.println("Joining game.");
				try {
					clientSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					clientSocket.close();
					e.printStackTrace();
				}

				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				try {
					clientSocket.receive(receivePacket);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					clientSocket.close();
					e1.printStackTrace();
				}
				playerNum = (new String(receivePacket.getData())).charAt(0);

				if (playerNum == '1')
					keyInputPort = 3335;
				else if (playerNum == '2')
					keyInputPort = 3336;
				else if (playerNum == '3')
					keyInputPort = 3337;
				else
					keyInputPort = 3338;
				currMove = "";
				System.out.println("You are player " + playerNum);
			}
			if (currMove.equals("start")) {
				System.out.println(IPAddress.getHostAddress() + " starting game.");
				sendData = currMove.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length,
						IPAddress, sendPort);
				try {
					clientSocket.send(sendPacket);
					newReceived.acquire();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					clientSocket.close();
					e.printStackTrace();
				}
				currMove = "";
			}

			 if (currMove.equals("spectate")){
				 isPlayer = false;
			 }
		}
		
		// No longer in startLobby
		clientSocket.close();
		try {
			inputSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName("localhost");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			inputSocket.close();
			e.printStackTrace();
		}

		System.out.println("Client: KeyInputPort: " + keyInputPort);
		System.out.println("Waiting for key presses");
		while (true) {
			sendData = new byte[1024];

			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (currMove != "") {
				sendData = currMove.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length,
						IPAddress, keyInputPort);
				try {
					inputSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					inputSocket.close();
					e.printStackTrace();
				}
				currMove = "";
			}
		}
	}

	public static void setCurrMove(String s) {
		currMove = s;
	}
	public static boolean getIsPlayer(){
		return isPlayer;
	}
	public static void main(String args[]) {
		new Client();
	}
}
