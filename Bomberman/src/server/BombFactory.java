package server;


public class BombFactory implements Runnable{
	
	private Player player;
	private GameBoard board;
	
	BombFactory(Player player, GameBoard board){
		this.player = player;
		this.board = board;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Bomb placed");
		int tempx, tempy;
		synchronized (board) {
			tempx = player.getXPosition();
			tempy = player.getYPosition();
			board.placeBombAndPlayer(player);
		}
		
		for (int row = 0; row < board.getBoardArray().length; row++) {
	        for (int column = 0; column < board.getBoardArray()[row].length; column++) {
	            System.out.print(board.getBoardArray()[row][column] + " ");
	        }
	        System.out.println();
	    }
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int row = 0; row < board.getBoardArray().length; row++) {
	        for (int column = 0; column < board.getBoardArray()[row].length; column++) {
	            System.out.print(board.getBoardArray()[row][column] + " ");
	        }
	        System.out.println();
	    }
		System.out.println("Bomb exploded");
		synchronized (board) {board.placeExplosion(tempx, tempy, player);}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (board) {
			board.removeExplosion(tempx, tempy, player);
		}
		
		for (int row = 0; row < board.getBoardArray().length; row++) {
	        for (int column = 0; column < board.getBoardArray()[row].length; column++) {
	            System.out.print(board.getBoardArray()[row][column] + " ");
	        }
	        System.out.println();
	    }
	}
}
