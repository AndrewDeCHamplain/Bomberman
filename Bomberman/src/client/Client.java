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

public class Client implements Runnable{

	private char playerNum = 0;
	private int keyInputPort, winner;
	private String currMove = "";
	private DatagramPacket sendPacket = null;
	private DatagramSocket clientSocket = null, inputSocket = null;
	private InetAddress IPAddress = null;
	private int sendPort = 3333;
	private byte[] sendData = new byte[1024];
	private ClientReceive clientReceive;
	private Semaphore newReceived = new Semaphore(0);
	private boolean isWinner, inGame, isPlayer, joined, startLobby, isFull;
	private Thread receiver;
	/**
	 * @param args
	 *            [0] -> port number
	 */
	public Client() {
		isWinner = false;
		inGame = false;
		joined = false;
		startLobby = true;
		winner = 0;
		isFull = false;
		// TODO Auto-generated method stub
		try {
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName("127.0.0.1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			clientSocket.close();
			e.printStackTrace();
		}

		receiver = new Thread(clientReceive = new ClientReceive(sendPort, newReceived, this));
		receiver.start();
		System.out.println("Join game.");
	}
		
		private void game(){
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
				if(new String(receivePacket.getData()).equals("full")){
					isFull = true;
					System.out.println("Game Full");
				}else {
					playerNum = (new String(receivePacket.getData())).charAt(0);
					System.out.println("You are player " + playerNum);
				}

				if (playerNum == '1')
					keyInputPort = 3335;
				else if (playerNum == '2')
					keyInputPort = 3336;
				else if (playerNum == '3')
					keyInputPort = 3337;
				else if(playerNum == '4')
					keyInputPort = 3338;
				currMove = "";
				
			}
			if (currMove.equals("start")) {
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
		inGame = true;
		try {
			inputSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName("127.0.0.1");
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
			if(!inGame){
				String temp = "reset";
				sendData = temp.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length,
						IPAddress, keyInputPort);
				try {
					inputSocket.send(sendPacket);
					inputSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					inputSocket.close();
					e.printStackTrace();
				}
				break;
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
		Thread overThread = new Thread(new GameOver(winner, playerNum));
		overThread.start();
		try {
			overThread.join();
			inputSocket.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setWinner(int w){
		winner = w;
	}
	public void setIsFull(boolean b){
		isFull = b;
	}
	public boolean getIsFull(){
		return isFull;
	}
	public int getWinner(){
		return winner;
	}
	public char getPlayerNum(){
		return playerNum;
	}
	public void setPlayerNum(char playerNum){
		this.playerNum = playerNum;
	}
	public void setCurrMove(String s) {
		currMove = s;
	}
	public String setCurrMove() {
		return currMove;
	}
	public boolean getIsPlayer(){
		return isPlayer;
	}
	public boolean getStartLobby() {
		// TODO Auto-generated method stub
		return startLobby;
	}
	public void setStartLobby(boolean startLobby){
		this.startLobby = startLobby;
	}
	public void setKeyInputPort(int port){
		keyInputPort = port;
	}
	public void setIsWinner(boolean b) {
		// TODO Auto-generated method stub
		isWinner = b;
	}
	public boolean getIsWinner() {
		// TODO Auto-generated method stub
		return isWinner;
	}
	public boolean getInGame(){
		return inGame;
	}
	public void setInGame(boolean b){
		inGame = b;
	}
	public ClientReceive getClientReceive(){
		return clientReceive;
	}
	public static void main(String args[]) {
		while(true){
			Client client = new Client();
			client.game();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		game();
	}
}
