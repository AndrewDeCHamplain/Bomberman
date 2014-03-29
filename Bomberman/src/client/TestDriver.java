package client;

import java.io.IOException;
import java.util.Scanner;

public class TestDriver{
	
	public TestDriver(){
		Scanner reader = new Scanner(System.in);
		System.out.println("Which test would you like to conduct (test1, test2, test3, and test4)?");
		System.out.println("Type info for explination of each test, type 'quit' to end.");
		while(true){
			String s = reader.nextLine();
			if(s.equals("info")){
				System.out.println("dasda");
			}
			else if(s.equals("quit")){
				break;
			}
			else if(s.equals("test1")){
				try {
					test1();
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(s.equals("test2")){
				try {
					test2();
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(s.equals("test3")){
				
			}
			else{
				System.out.println("Unreconized command");
			}
		}
		reader.close();
	}

	// The text file containing the test inputs is stored in testFile
	
	public static void main(String[] args){
		new TestDriver();
	}
	private void test1() throws InterruptedException, IOException {
		Thread clientThread = new Thread(new ClientTest("p1t1"));
		clientThread.start();
		sleep(500);
		Thread spectatorThread = new Thread(new SpectatorTest());
		spectatorThread.start();
	}
	
	private void test2() throws InterruptedException, IOException {

		Thread clientThread1 = new Thread(new ClientTest("p1t1"));
		clientThread1.start();
		sleep(500);
		Thread clientThread2 = new Thread(new ClientTest("p2t2"));
		clientThread2.start();
	}

	private void sleep(int i) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			if (sentence.equals("test1")) {
				test1();
			}
			if (sentence.equals("test2")) {
				test2();
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
}
