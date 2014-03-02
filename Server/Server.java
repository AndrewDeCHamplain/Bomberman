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

	/**
	 * @param args[0] -> port number
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			DatagramSocket serverSocket = null;
			byte[] receiveData, sendData;
			try {
				serverSocket = new DatagramSocket(Integer.parseInt(args[0])); 
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
			
			
			while(true){
				
				receiveData = new byte[1024];
				sendData = new byte[1024];
				
				// read a message from socket
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				try {
					serverSocket.receive(receivePacket);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String sentence = new String( receivePacket.getData());
				System.out.println("Received: " + sentence);
				
				//get address from who sent packet
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				String capitalizedSentence = sentence.toUpperCase();
				sendData = capitalizedSentence.getBytes();
				
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

}
