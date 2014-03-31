package server;

public class PlayerSleep implements Runnable{

	private Player player;
	
	/**
	 * Assigns parameter to field variable
	 * @param player
	 */
	public PlayerSleep(Player player){
		this.player = player;
	}
	@Override
	/**
	 * Current player status, alive/dead
	 */
	public void run() {
		player.setPlayerStatus(false);
		// TODO Auto-generated method stub
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.setPlayerStatus(true);
	}

}
