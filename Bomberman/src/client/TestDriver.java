package client;

import java.io.IOException;

public class TestDriver implements Runnable {

	// The text file containing the test inputs is stored in testFile
	private String sentence;

	public TestDriver(String sentence) {
		this.sentence = sentence;
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
}
