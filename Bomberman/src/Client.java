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

	public static char tileMap[][] = {{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
		{'w','x','f','f','f','f','f','f','f','f','f','f','f','f','x','x','w'},
		{'w','x','w','f','w','f','w','f','w','f','w','f','w','f','w','x','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','1','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','x','w','f','w','f','w','f','w','f','w','f','w','f','w','x','w'},
		{'w','x','x','f','f','f','f','f','f','f','f','f','f','f','x','x','w'},
		{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'}
		};

	/**
	 * @param args
	 *            [0] -> port number
	 */
	public static void main(String[] args) {

		Thread gameThread = new Thread(new GameView(tileMap));
		
		// TODO Auto-generated method stub
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket();
		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		InetAddress IPAddress = null;
		try {
			IPAddress = InetAddress.getByName("localhost");
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] receiveData, sendData;
		String sentence = null;

		while (true) {

			receiveData = new byte[1024];
			sendData = new byte[1024];

			try {
				sentence = inFromUser.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,
			sendData.length, IPAddress, Integer.parseInt(args[0]));
			//DatagramPacket sendPacket = new DatagramPacket(sendData,
			//		sendData.length, IPAddress, 5293);
			try {
				clientSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			try {
				clientSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String tileMapString = new String(receivePacket.getData());
			tileMap = stringToArray(tileMapString);
			System.out.println("From server: " + tileMapString);
			
			;
			// clientSocket.close();
		}
	}

	// this method converts our array to a CSV string format where each level of
	// the array is delimited by "|"
	public static char[][] stringToArray(String s) {
		char[][] tileMapInternal = null;
		String tempMap[] = null;
		int i = 0;
		int j = 0;
		String[] tokensRow = s.split("|");
		//String[] tokensColumn = s.split(",");
		
		for (String t : tokensRow){
			tempMap[i]= t;		
			String[] tokensColumn = tempMap[j].split(",");
			for(String y : tokensColumn){
				tileMapInternal[i][j]=y.toCharArray()[0];
				j++;
			}
			i++;
		}


		return tileMapInternal;
	}

}
