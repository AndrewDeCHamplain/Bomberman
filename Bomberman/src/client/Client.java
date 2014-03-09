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

public class Client {

	public static char playerNum = 0;
	public static boolean startLobby = true;
	public static int keyInputPort;
	public static String currMove = "";

	/**
	 * @param args
	 *            [0] -> port number
	 */
	public static void main(String[] args) {

		DatagramPacket sendPacket = null;
		DatagramSocket clientSocket = null, inputSocket = null;
		InetAddress IPAddress = null;
		int sendPort = 3333;
		boolean joined = false;

		byte[] sendData = new byte[1024];

		Thread receiver = new Thread(new ClientReceive(sendPort));
		receiver.start();

		// TODO Auto-generated method stub
		try {
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName("localhost");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			clientSocket.close();
			e.printStackTrace();
		}

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));

		System.out.println("Join game.");
		String sentence = "";
		while (startLobby) {

			sendData = new byte[1024];

			
			try {
				sentence = inFromUser.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				clientSocket.close();
				e.printStackTrace();
			}

			if (sentence.equals("join")) {
				System.out.println("You are trying to join the game.");
				if (joined) {
					System.out.println("You have already joined");
					break;
				}
				joined = true;
				sendData = sentence.getBytes();
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

			}

			if (sentence.equals("start")) {
				System.out.println(IPAddress.getHostAddress()
						+ " starting game.");
				sendData = sentence.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length,
						IPAddress, sendPort);
				try {
					clientSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					clientSocket.close();
					e.printStackTrace();
				}
			}
		}
		clientSocket.close();
		try {
			inputSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName("localhost");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			inputSocket.close();
			e.printStackTrace();
		}

		while (true) {
			sendData = new byte[1024];

			/*
			 * try { sentence = inFromUser.readLine(); } catch (IOException e) {
			 * // TODO Auto-generated catch block inputSocket.close();
			 * e.printStackTrace(); }
			 */

			// slow down the loop (can register a key press up to every 20
			// milliseconds)
			try {
				Thread.sleep(20);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			synchronized (currMove) {
				if (currMove != "") {
					System.out.println(currMove);
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
				}
				currMove = "";
			}

		}
	}
}