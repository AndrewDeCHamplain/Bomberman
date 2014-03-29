package client;

public class ClientTest implements Runnable{

	private static Client client;
	public ClientTest(){
		client = new Client(true);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	public Client getClient(){
		return client;
	}

}
