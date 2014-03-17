package server;


public class BombFactory implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Bomb placed");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Bomb exploded");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
