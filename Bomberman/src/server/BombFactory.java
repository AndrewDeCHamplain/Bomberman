package server;


public class BombFactory implements Runnable{
	
	private Player player;
	private GameBoard board;
	
	/**
	 * Assigns paramaters to the variables intialized in the field.
	 * @param player, Player
	 * @param board, GameBoard
	 */
	BombFactory(Player player, GameBoard board){
		this.player = player;
		this.board = board;
	}

	@Override
	/**
	 * Places bomb at specific x and y position on the gameboard
	 * wherever the players position is current. Shows the gif for the bomb
	 * exploding and then removes the gif after a certain amount
	 * of time having elapsed.
	 */
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Bomb placed");
		int tempx, tempy;
		//int constx, consty;
		synchronized (board) {
			tempx = player.getXPosition();
			tempy = player.getYPosition();
			board.placeBombAndPlayer(player);
		}
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Bomb exploded");
		synchronized (board) {board.placeExplosion(tempx, tempy, player);}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		while(1){
			constx = player.getXposition();
			consty = player.getYposition();
			if (constx == tempx && consty == tempy)
			{
				player.setLives(getLives() - 1);
				if(player.getLives() == 0)
				{
					System.out.println("The player has lost all lives. Game will terminate");
					//Begin termination sequence code.
				}
			}
		}
		*/
		synchronized (board) {
			board.removeExplosion(tempx, tempy, player);
			//player.setBombs(player.getBombs()+1);
		}
		//System.out.println(player.getBombs());
	}
}
