package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;

public class TestDriver{

	//The text file containing the test inputs is stored in testFile
	private static BufferedReader testFile;
	
	public TestDriver(String sentence){
		try {
			test(sentence);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void test(String testCase) throws IOException, InterruptedException {
		FileInputStream in = new FileInputStream("resources/" + testCase + ".txt");
		ClientTest client;
		testFile = new BufferedReader(new InputStreamReader(in));
		
		String testInput = testFile.readLine();
		
		Thread clientThread = new Thread(client = new ClientTest());
		clientThread.start();
		
		System.out.println("1");
		GameLobby.pressJoin();
		Thread.sleep(500);
		GameLobby.pressStart();

		while (testInput != null) {
			if(testInput.equals("sleep")) {
				Thread.sleep(500);
			}
			else {
				client.getClient().setCurrMove(testInput);
			}
			Thread.sleep(500);
			testInput = testFile.readLine();
		}
		
		in.close();
	}
	public static void main(String[] args){
		new TestDriver("testcases");
	}
}
