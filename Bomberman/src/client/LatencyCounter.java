package client;

import java.util.concurrent.Semaphore;

public class LatencyCounter implements Runnable {

	private long time;
	private Semaphore semMoved;;
	
	public LatencyCounter(Semaphore semMoved, long time){
		this.time = time;
		this.semMoved = semMoved;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			semMoved.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - time;
	    System.out.println("Latency: "+elapsedTime);
	}
}
