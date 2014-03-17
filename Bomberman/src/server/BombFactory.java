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
		int tempx = player.getXPosition(), tempy = player.getYPosition();
		synchronized (board) {board.placeBombAndPlayer(player);
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
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (board) {
			board.getBoardArray();
			board.placeFloor(tempx, tempy);
		}
		
		for (int row = 0; row < board.getBoardArray().length; row++) {
	        for (int column = 0; column < board.getBoardArray()[row].length; column++) {
	            System.out.print(board.getBoardArray()[row][column] + " ");
	        }
	        System.out.println();
	    }
	}
}
