package client;

public class ClientTest implements Runnable{

	public Client client;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread clientThread = new Thread(client = new Client());
		clientThread.start();
	}
	public Client getClient(){
		return client;
	}
}