package server;

public class GameEngine implements Runnable {
	private GameBoard board;
	private int numPlayers;
	private Player player1 = null, player2 = null, player3 = null,
			player4 = null;
	private static char[][] currentBoard;
	public static String command="0,0";

	public GameEngine(int numPlayers) {
		board = new GameBoard(1);
		this.numPlayers = numPlayers;
		currentBoard = board.getBoardArray();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < numPlayers; i++) {
			makePlayer(i);
		}

		while (true) {
			synchronized (command) {
				String[] parts = command.split(",");
				if (parts[1].equals("1")) {
					if (parts[0].trim().equals("LEFT") || parts[0].trim().equals("left")) {
						movePlayerLeft(player1);
					}
					if (parts[0].trim().equals("UP") || parts[0].trim().equals("up")) {
						movePlayerUp(player1);
					}
					if (parts[0].trim().equals("RIGHT") || parts[0].trim().equals("right")) {
						movePlayerRight(player1);
					}
					if (parts[0].trim().equals("DOWN") || parts[0].trim().equals("down")) {
						movePlayerDown(player1);
					}
				}
				if (parts[1].equals("2")) {
					if (parts[0].trim().equals("LEFT") || parts[0].trim().equals("left")) {
						movePlayerLeft(player2);
					}
					if (parts[0].trim().equals("UP") || parts[0].trim().equals("up")) {
						movePlayerUp(player2);
					}
					if (parts[0].trim().equals("RIGHT") || parts[0].trim().equals("right")) {
						movePlayerRight(player2);
					}
					if (parts[0].trim().equals("DOWN") || parts[0].trim().equals("down")) {
						movePlayerDown(player2);
					}
				}
				if (parts[1].equals("3")) {
					if (parts[0].trim().equals("LEFT") || parts[0].trim().equals("left")) {
						movePlayerLeft(player3);
					}
					if (parts[0].trim().equals("UP") || parts[0].trim().equals("up")) {
						movePlayerUp(player3);
					}
					if (parts[0].trim().equals("RIGHT") || parts[0].trim().equals("right")) {
						movePlayerRight(player3);
					}
					if (parts[0].trim().equals("DOWN") || parts[0].trim().equals("down")) {
						movePlayerDown(player3);
					}
				}
				if (parts[1].equals("4")) {
					if (parts[0].trim().equals("LEFT") || parts[0].trim().equals("left")) {
						movePlayerLeft(player4);
					}
					if (parts[0].trim().equals("UP") || parts[0].trim().equals("up")) {
						movePlayerUp(player4);
					}
					if (parts[0].trim().equals("RIGHT") || parts[0].trim().equals("right")) {
						movePlayerRight(player4);
					}
					if (parts[0].trim().equals("DOWN") || parts[0].trim().equals("down")) {
						movePlayerDown(player4);
					}
				}
			}
		}
	}

	private void makePlayer(int i) {
		if (i == 1) {
			player1 = new Player(1, 1, '1');
		} else if (i == 2) {
			player2 = new Player(currentBoard[0].length, 1, '2');
		} else if (i == 2) {
			player3 = new Player(1, currentBoard.length, '3');
		} else {
			player4 = new Player(currentBoard[0].length, currentBoard.length,
					'4');
		}
	}
	public void placePlayer(Player player, int x, int y) {
		currentBoard[x][y] = player.getPlayerNum();
		player.setX(x);
		player.setY(y);
		System.out.println(currentBoard[x][y]);
	}
	public static char[][] getCurrentBoard(){
		return currentBoard;
	}
	public void movePlayerRight(Player player) {
		if (currentBoard[player.getXPosition()+1][player.getYPosition()] == 'f'){
			placePlayer(player, player.getXPosition()+1, player.getYPosition());
			currentBoard[player.getXPosition()-1][player.getYPosition()] = 'f';
		}
	}

	public void movePlayerLeft(Player player) {
		if (currentBoard[player.getXPosition() - 1][player.getYPosition()] == 'f'){
			placePlayer(player, player.getXPosition() - 1, player.getYPosition()); 
			currentBoard[player.getXPosition() +1][player.getYPosition()] = 'f';
		}
	}

	public void movePlayerUp(Player player) {
		if (currentBoard[player.getXPosition()][player.getYPosition()+1] == 'f'){
			placePlayer(player, player.getXPosition(), player.getYPosition()+1);
			currentBoard[player.getXPosition()][player.getYPosition()-1] = 'f';
		}
	}

	public void movePlayerDown(Player player) {
		if (currentBoard[player.getXPosition()][player.getYPosition()-1] == 'f'){
			placePlayer(player, player.getXPosition(), player.getYPosition()-1);
			currentBoard[player.getXPosition()][player.getYPosition()+1] = 'f';
		}
	}
}
