package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;

public class TestDriver implements Runnable{

	//The text file containing the test inputs is stored in testFile
	private static BufferedReader testFile;
	private String sentence;
	private Client client;
	private ClientTest clientTest;
	private GameLobby gameLobby;
	
	public TestDriver(String sentence, GameLobby gameLobby){
		this.sentence = sentence;
		this.gameLobby = gameLobby;
	}
	
	public void test(String testCase) throws IOException, InterruptedException {
		FileInputStream in = new FileInputStream("resources/" + testCase + ".txt");
		
		testFile = new BufferedReader(new InputStreamReader(in));

		Thread clientThread = new Thread(clientTest = new ClientTest());
		clientThread.start();
		client = clientTest.getClient();
		Thread.sleep(1000);
		gameLobby.pressJoin();
		Thread.sleep(1000);
		gameLobby.pressStart();
		
		String testInput = testFile.readLine();
		
		client = clientTest.client;
		
		while (testInput != null) {
			if(testInput.equals("sleep")) {
				Thread.sleep(500);
			}
			else {
				client.setCurrMove(testInput);
			}
			Thread.sleep(500);
			testInput = testFile.readLine();
		}
		in.close();
		Thread.sleep(1000);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			test(sentence);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
