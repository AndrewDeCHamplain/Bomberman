package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerInputThread implements Runnable {

	public int player;
	int receivePort;

	public ServerInputThread(int port, int player) {
		receivePort = port;
		this.player = player;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DatagramSocket serverSocket = null;
		DatagramPacket receivePacket = null;
		byte[] receivedData = new byte[1024];

		try {
			serverSocket = new DatagramSocket(receivePort);
			serverSocket.setReuseAddress(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			serverSocket.close();
			e.printStackTrace();
		}
		while (true) {
			receivedData = new byte[1024];
			receivePacket = new DatagramPacket(receivedData,
					receivedData.length);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(player +" Pressed: " +new String(receivePacket.getData()));
			synchronized (GameEngine.command){
				GameEngine.command = new String(receivePacket.getData()).trim()+","+player;
			}
		}
	}
}
