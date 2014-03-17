package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		String sentence = "";
		
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
	}
	
	private static void test1() throws InterruptedException{
		
		Thread client1 = new Thread(new ClientTest());
		client1.start();
		
		Thread.sleep(1000);
		Client.setCurrMove("join");
		Thread.sleep(500);
		Client.setCurrMove("start");
		Thread.sleep(2000);
		Client.setCurrMove("down");
		Thread.sleep(500);
		Client.setCurrMove("down");
		Thread.sleep(500);
		Client.setCurrMove("right");
		Thread.sleep(500);
		Client.setCurrMove("right");

	}

}
