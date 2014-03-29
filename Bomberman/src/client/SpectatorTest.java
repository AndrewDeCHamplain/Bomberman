package client;

public class SpectatorTest implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Client client;
		Thread clientThread = new Thread(client = new Client());
		clientThread.start();

		sleep(1000);
		client.getClientReceive().getGameLobby().pressSpectate();
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
