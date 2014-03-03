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
		{'w','1','x','f','f','f','f','f','f','f','f','f','f','f','x','2','w'},
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
		{'w','3','x','f','f','f','f','f','f','f','f','f','f','f','x','4','w'},
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
			
			Player player1 = null;
			Player player2 = null;
			Player player3 = null;
			Player player4 = null;
			
			byte[] receiveData, sendData;
			int numPlayers = 0;
			
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
			
			String startGame = "";
			
			while(!startGame.equals("start") && !(numPlayers > 4 )){
				System.out.println("Waiting for players, players: " + numPlayers);
				
				
				receiveData = new byte[1024];
				sendData = new byte[1024];
				
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				try {
					serverSocket.receive(receivePacket);
					startGame = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println(startGame);
				
				if(startGame.equals("join")){
					
					numPlayers++;

					//get address from who sent packet
					InetAddress IPAddress = receivePacket.getAddress();
					System.out.println(receivePacket.getAddress().getHostAddress() + " joined the game.");
					int port = receivePacket.getPort();
					String temp = String.valueOf(numPlayers);
					sendData = temp.getBytes();
					
					if(numPlayers == 1){
						player1 = new Player(1, 1, 1, IPAddress);
						placePlayer(player1);
					}else if(numPlayers == 2){
						player2 = new Player(16, 1, 2, IPAddress);
						placePlayer(player2);
					}else if(numPlayers == 3){
						player3 = new Player(1, 16, 3, IPAddress);
						placePlayer(player3);
					}else if(numPlayers == 4){
						player4 = new Player(16, 16, 4, IPAddress);
						placePlayer(player4);
					}
					
					//send player their number
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					try {
						serverSocket.send(sendPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				// If trying to start game without any players
				if(startGame.equals("start") && numPlayers==0){
					InetAddress IPAddress = receivePacket.getAddress();
					int port = receivePacket.getPort();
					String temp = "No players";
					sendData = temp.getBytes();
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
					try {
						serverSocket.send(sendPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}								
				
			}
			try {
				serverSocket.setBroadcast(true);
			} catch (SocketException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
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
				int port = receivePacket.getPort();
				
				movePlayer(whichPlayer(IPAddress,player1,player2,player3,player4),sentence);
				
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
	
	public static void placePlayer(Player player){
				boardArray[player.x][player.y]=(char) player.playerNumber;
	}
	public static void movePlayer(Player player, String direction){
				if (direction.equals("LEFT")||direction.equals("left")){
					if (boardArray[player.x-1][player.y]=='f')
						player.x=player.x--;
				}
				if (direction.equals("UP")||direction.equals("up")){
					if (boardArray[player.x][player.y+1]=='f')
						player.y=player.y++;
				}
				if (direction.equals("RIGHT")||direction.equals("right")){
					if (boardArray[player.x+1][player.y]=='f')
						player.x=player.x++;
				}
				if (direction.equals("DOWN")||direction.equals("down")){
					if (boardArray[player.x][player.y-1]=='f')
						player.y=player.y--;
				}
	}
	public static Player whichPlayer(InetAddress ipaddress,Player p1,Player p2, Player p3, Player p4){
		if (p1.IP.equals(ipaddress))
			return p1;
		else if (p2.IP.equals(ipaddress))
			return p2;
		else if (p3.IP.equals(ipaddress))
			return p3;
		else if (p4.equals(ipaddress))
			return p4;
		else 
			return null;
	}

}
