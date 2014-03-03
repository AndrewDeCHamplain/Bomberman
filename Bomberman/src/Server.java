/**
 * 
 */

/**
 * @author Andrew
 *
 */

import java.io.*; 
import java.net.*;

public class Server {
	
	public static char[][] boardArray = 
		{{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
		{'w','1','x','f','f','f','f','f','f','f','f','f','f','f','x','x','w'},
		{'w','x','w','f','w','f','w','f','w','f','w','f','w','f','w','x','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','x','w','f','w','f','w','f','w','f','w','f','w','f','w','x','w'},
		{'w','x','x','f','f','f','f','f','f','f','f','f','f','f','x','x','w'},
		{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'}
		};
	public static String arraystring = null;

	/**
	 * @param args[0] -> port number
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			DatagramSocket serverSocket = null;
			DatagramPacket receivePacket = null;
			
			byte[] receiveData, sendData;
			int port, numPlayers = 0;
			
			try {
				serverSocket = new DatagramSocket(Integer.parseInt(args[0])); 
				//serverSocket = new DatagramSocket(5293);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 
			try {
				serverSocket.setReuseAddress(true);
			} catch (SocketException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			String startGame = null;
			
			while(startGame != "START_GAME" || numPlayers < 4){
				receiveData = new byte[1024];
				sendData = new byte[1024];
				
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				try {
					serverSocket.receive(receivePacket);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				startGame = new String(receivePacket.getData());
				
				// If trying to start game without any players
				if(startGame == "START_GAME" && numPlayers==0){
					InetAddress IPAddress = receivePacket.getAddress();
					port = receivePacket.getPort();
					String temp = "No players";
					sendData = temp.getBytes();
					//send player their number
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					try {
						serverSocket.send(sendPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(startGame == "JOIN_GAME"){
					numPlayers++;
					//get address from who sent packet
					InetAddress IPAddress = receivePacket.getAddress();
					port = receivePacket.getPort();
					String temp = String.valueOf(numPlayers);
					sendData = temp.getBytes();
					//send player their number
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					try {
						serverSocket.send(sendPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
				
			while(true){
				
				receiveData = new byte[1024];
				sendData = new byte[1024];
				
				// read a message from socket
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				try {
					serverSocket.receive(receivePacket);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String sentence = new String(receivePacket.getData());
				System.out.println("Received: " + sentence);
				
				//get address from who sent packet
				InetAddress IPAddress = receivePacket.getAddress();
				port = receivePacket.getPort();
				sendData = arrayToString(boardArray).getBytes();
				
				//send the message back
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				try {
					serverSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	}
	//this method converts our array to a CSV string format where each level of the array is delimited by "|"
	public static String arrayToString(char array[][]){
		arraystring = null;
		for (int i=0;i<array.length;i++){
				arraystring = arraystring + array[i][0];
			for (int j=1;j<array[i].length;j++){
				arraystring = arraystring + "," + array[i][j];
			}
			arraystring = arraystring + "|";
		}
		return arraystring;
	} 

}
