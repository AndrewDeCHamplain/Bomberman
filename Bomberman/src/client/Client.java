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

public class Client implements Runnable {

	private char playerNum, prev;
	private int keyInputPort, winner;
	private String currMove;
	private DatagramPacket sendPacket;
	private DatagramSocket clientSocket, inputSocket;
	private InetAddress IPAddress;
	private int sendPort = 3333;
	private byte[] sendData;
	private ClientReceive clientReceive;
	private Semaphore newReceived;
	private boolean isWinner, inGame, isPlayer, joined, startLobby, isFull;
	private Thread receiver;
	private GameOver gameOver;
	/**
	 * @param args
	 *            [0] -> port number
	 */
	public Client() {
		sendPacket = null;
		clientSocket = null;
		inputSocket = null;
		IPAddress = null;
		newReceived = new Semaphore(0);
		sendData = new byte[1024];
		currMove = "";
		playerNum = 0;
		isPlayer = false;
		isWinner = false;
		inGame = false;
		joined = false;
		startLobby = true;
		winner = 0;
		isFull = false;
		keyInputPort = 0;
		gameOver = null;
		// TODO Auto-generated method stub
		try {
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName("192.168.0.13"); // 192.168.43.21 my laptop
		} catch (IOException e) {
			// TODO Auto-generated catch block
			clientSocket.close();
			e.printStackTrace();
		}

		receiver = new Thread(clientReceive = new ClientReceive(sendPort,
				newReceived, this, IPAddress));
		receiver.start();
	}

	private void game() {
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
				if (new String(receivePacket.getData()).equals("full")) {
					isFull = true;
					System.out.println("Game Full");
				} else {
					playerNum = (new String(receivePacket.getData())).charAt(0);
					System.out.println("You are player " + playerNum);
				}

				if (playerNum == '1'){
					keyInputPort = 3335;
				}else if (playerNum == '2'){
					keyInputPort = 3336;
				}else if (playerNum == '3'){
					keyInputPort = 3337;
				}else if (playerNum == '4'){
					keyInputPort = 3338;
				}
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

			if (currMove.equals("spectate")) {
				isPlayer = false;
			}
		}
		// No longer in startLobby
		clientSocket.close();
		inGame = true;
		try {
			inputSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName("192.168.0.13");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			inputSocket.close();
			e.printStackTrace();
		}
		while (true) {
			sendData = new byte[1024];

			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!inGame) {
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
		try {
			receiver.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		gameOver = new GameOver(winner, playerNum, isWinner);
		Thread overThread = new Thread(gameOver);
		overThread.start();
		try {
			overThread.join();
			inputSocket.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public GameOver getGameOver(){
		return gameOver;
	}
	public void setWinner(int w) {
		winner = w;
	}

	public void setIsFull(boolean b) {
		isFull = b;
	}

	public boolean getIsFull() {
		return isFull;
	}

	public int getWinner() {
		return winner;
	}

	public char getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(char playerNum) {
		this.playerNum = playerNum;
	}

	public void setCurrMove(String s) {
		currMove = s;
	}

	public String setCurrMove() {
		return currMove;
	}

	public boolean getIsPlayer() {
		return isPlayer;
	}

	public boolean getStartLobby() {
		// TODO Auto-generated method stub
		return startLobby;
	}

	public void setStartLobby(boolean startLobby) {
		this.startLobby = startLobby;
	}

	public void setKeyInputPort(int port) {
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

	public boolean getInGame() {
		return inGame;
	}

	public void setInGame(boolean b) {
		inGame = b;
	}
	public ClientReceive getClientReceive() {
		return clientReceive;
	}

	public static void main(String args[]) {
		while (true) {
			Client client = new Client();
			client.game();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		game();
	}

	public void setPrev(char i) {
		prev = i;
	}
	public char getPrev(){
		return prev;
	}
}
