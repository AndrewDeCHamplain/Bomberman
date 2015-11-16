package client;

import java.io.IOException;
import java.util.Scanner;

public class TestDriver{
	
	public TestDriver(){
		Scanner reader = new Scanner(System.in);
		System.out.println("Which test would you like to conduct (test1, test2, test3, and test4)?");
		System.out.println("Type 'info' for explination of each test, type 'quit' to end.");
		while(true){
			String s = reader.nextLine();
			if(s.equals("info")){
				System.out.println("Test 1: 1 player performing simple movements which includes the use of bombs.");
				System.out.println("	A spectator is also present.");
				System.out.println("Test 2: 2 players are moving and using bombs and end the game by touching.");
				System.out.println("Test 3: Latency test 1, the server is under low load. The game runs with 1");
				System.out.println("	player and 1 spectator and measures the latency.");
				System.out.println("Test 4: Latency test 2, the server is under medium load. The game runs with 2");
				System.out.println("	players and 2 spectators and measures the latency.");
				System.out.println("Test 5: Latency test 3, the server is under high load. The game runs with 4");
				System.out.println("	players and 4 spectators and measures the latency.");
			}else if(s.equals("quit")){
				break;
			}else if(s.equals("test1")){
				try {
					test1();
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(s.equals("test2")){
				try {
					test2();
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(s.equals("test3")){
				try {
					test3();
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(s.equals("test4")){
				try {
					test4();
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(s.equals("test5")){
				try {
					test5();
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		Thread clientThread = new Thread(new ClientTest("p1t3", false));
		clientThread.start();
		sleep(500);
		Thread spectatorThread = new Thread(new SpectatorTest());
		spectatorThread.start();
		clientThread.join();
	}
	
	private void test2() throws InterruptedException, IOException {

		Thread clientThread1 = new Thread(new ClientTest("p1t1", false));
		clientThread1.start();
		sleep(500);
		Thread clientThread2 = new Thread(new ClientTest("p2t2", false));
		clientThread2.start();
	}
	
	private void test3() throws InterruptedException, IOException {

		Thread clientThread1 = new Thread(new ClientTest("p1t3", false));
		clientThread1.start();
		sleep(500);
		Thread clientThread2 = new Thread(new ClientTest("p2t3", false));
		clientThread2.start();
		sleep(500);
		clientThread1.join();
		clientThread2.join();
		//Thread spectatorThread = new Thread(new SpectatorTest());
		//spectatorThread.start();
	}
	private void test4() throws InterruptedException, IOException {

		Thread clientThread1 = new Thread(new ClientTest("p1t3", false));
		clientThread1.start();
		sleep(200);
		Thread clientThread2 = new Thread(new ClientTest("p2t3", false));
		clientThread2.start();
		sleep(200);
		Thread clientThread3 = new Thread(new ClientTest("p2t3", false));
		clientThread3.start();
		sleep(200);
		Thread clientThread4 = new Thread(new ClientTest("p2t3", false));
		clientThread4.start();
		sleep(200);
		clientThread1.join();
		clientThread2.join();
		clientThread3.join();
		clientThread4.join();
	}
	private void test5() throws InterruptedException, IOException {

		Thread clientThread1 = new Thread(new ClientTest("p1t3", true));
		clientThread1.start();
		sleep(500);
		Thread clientThread2 = new Thread(new ClientTest("p2t3", true));
		clientThread2.start();
		sleep(500);
		Thread clientThread3 = new Thread(new ClientTest("p2t3", true));
		clientThread3.start();
		sleep(500);
		Thread clientThread4 = new Thread(new ClientTest("p2t3", true));
		clientThread4.start();
		sleep(500);
		clientThread1.join();
		clientThread2.join();
		clientThread3.join();
		clientThread4.join();
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
}
