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
	
	
	/*
	 * public static void movePlayer(Player player, String direction) {
		if (direction.equals("LEFT") || direction.equals("left")) {
			if (boardArray[player.x - 1][player.y] == 'f')
				player.x = player.x--;
		}
		if (direction.equals("UP") || direction.equals("up")) {
			if (boardArray[player.x][player.y + 1] == 'f')
				player.y = player.y++;
		}
		if (direction.equals("RIGHT") || direction.equals("right")) {
			if (boardArray[player.x + 1][player.y] == 'f')
				player.x = player.x++;
		}
		if (direction.equals("DOWN") || direction.equals("down")) {
			if (boardArray[player.x][player.y - 1] == 'f')
				player.y = player.y--;
		}
	}
	 */

}
