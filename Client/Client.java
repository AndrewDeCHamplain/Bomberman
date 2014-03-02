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

	/**
	 * @param args[0] -> port number
	 * @throws UnknownHostException 
	 * @throws SocketException 
	 */
	public static void main(String[] args) throws UnknownHostException, SocketException {
		
		// TODO Auto-generated method stub
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] receiveData, sendData;
		String sentence = null;
		
		while(true){
			
			receiveData = new byte[1024];
			sendData = new byte[1024];
			
			try {
				sentence = inFromUser.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, Integer.parseInt(args[0]));
			try {
				clientSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				clientSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String modifiedSentence = new String(receivePacket.getData());
			System.out.println("From server: " + modifiedSentence);;
			//clientSocket.close();
		}
	}

}
