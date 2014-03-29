package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientTest implements Runnable{

	private Client client;
	private FileInputStream in;
	private BufferedReader testFile;
	
	public ClientTest(String file){
		try {
			in = new FileInputStream("resources/"+file+".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testFile = new BufferedReader(new InputStreamReader(in));
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread clientThread = new Thread(client = new Client());
		clientThread.start();

		sleep(1000);
		client.getClientReceive().getGameLobby().pressJoin();
		sleep(2000);
		client.getClientReceive().getGameLobby().pressStart();
		
		String testInput = null;
		try {
			testInput = testFile.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (testInput != null) {
			if (testInput.equals("sleep")) {
				sleep(500);
			} else {
				client.setCurrMove(testInput);
			}
			try {
				sleep(500);
				testInput = testFile.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Client getClient(){
		return client;
	}
}