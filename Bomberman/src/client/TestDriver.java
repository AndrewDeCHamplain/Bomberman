package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;

public class TestDriver {

	
	//The text file containing the test inputs is stored in testFile
	private static BufferedReader testFile;
	
	static BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
			System.in));
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		String sentence = "";
		
		while(true){
			System.out.println("enter the name of a testing file (do not type the extension): ");
			try {
				sentence = inFromUser.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(sentence.equals("quit"))
				break;
			else {
				test(sentence);
			}
		}
		
		/*
		System.out.println("Choose test to be performed. (1,2, or 3) Type 'quit' to end test");
		while(true){
			try {
				sentence = inFromUser.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(sentence.equals("1")){
				try {
					test1();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Choose test to be performed. (1,2, or 3) Type 'quit' to end test");
			}
			if(sentence.equals("quit"))
				break;
		}
		*/
	}
	
	private static void test(String testCase) throws IOException, InterruptedException {
		FileInputStream in = new FileInputStream("resources/" + testCase + ".txt");
		
		testFile = new BufferedReader(new InputStreamReader(in));
		
		String testInput = testFile.readLine();
		
		Thread client1 = new Thread(new ClientTest());
		client1.start();
		
		while (testInput != null) {
			if(testInput.equals("sleep")) {
				Thread.sleep(500);
			}
			else {
				Client.setCurrMove(testInput);
			}
			Thread.sleep(500);
			testInput = testFile.readLine();
		}
		in.close();
		//New stuff
	}
	/*
	private static void test1() throws InterruptedException{
		
		Thread client1 = new Thread(new ClientTest());
		client1.start();
		
		
		Thread.sleep(2000);
		Client.setCurrMove("join");
		System.out.println("Joining");
		Thread.sleep(1000);
		Client.setCurrMove("start");
		Thread.sleep(2000);
		Client.setCurrMove("down");
		Thread.sleep(500);
		Client.setCurrMove("down");
		Thread.sleep(500);
		Client.setCurrMove("bomb");
		Thread.sleep(500);
		Client.setCurrMove("up");
		Thread.sleep(500);
		Client.setCurrMove("up");

	}
	*/
}
