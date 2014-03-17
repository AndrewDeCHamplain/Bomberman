package server;

public class GameEngine implements Runnable {
	private static GameBoard board;
	private int numPlayers;
	private Player player1 = null, player2 = null, player3 = null,
			player4 = null;
	// private static char[][] currentBoard;
	public static String command = "0,0";

	public GameEngine(int numPlayers) {
		board = new GameBoard(1);
		this.numPlayers = numPlayers;
		// currentBoard = board.getBoardArray();
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
					if (parts[0].trim().equals("LEFT")
							|| parts[0].trim().equals("left")) {
						movePlayerLeft(player1);
					}
					if (parts[0].trim().equals("UP")
							|| parts[0].trim().equals("up")) {
						movePlayerUp(player1);
					}
					if (parts[0].trim().equals("RIGHT")
							|| parts[0].trim().equals("right")) {
						movePlayerRight(player1);
					}
					if (parts[0].trim().equals("DOWN")
							|| parts[0].trim().equals("down")) {
						movePlayerDown(player1);
					}
				}
				if (parts[1].equals("2")) {
					if (parts[0].trim().equals("LEFT")
							|| parts[0].trim().equals("left")) {
						movePlayerLeft(player2);
					}
					if (parts[0].trim().equals("UP")
							|| parts[0].trim().equals("up")) {
						movePlayerUp(player2);
					}
					if (parts[0].trim().equals("RIGHT")
							|| parts[0].trim().equals("right")) {
						movePlayerRight(player2);
					}
					if (parts[0].trim().equals("DOWN")
							|| parts[0].trim().equals("down")) {
						movePlayerDown(player2);
					}
				}
				if (parts[1].equals("3")) {
					if (parts[0].trim().equals("LEFT")
							|| parts[0].trim().equals("left")) {
						movePlayerLeft(player3);
					}
					if (parts[0].trim().equals("UP")
							|| parts[0].trim().equals("up")) {
						movePlayerUp(player3);
					}
					if (parts[0].trim().equals("RIGHT")
							|| parts[0].trim().equals("right")) {
						movePlayerRight(player3);
					}
					if (parts[0].trim().equals("DOWN")
							|| parts[0].trim().equals("down")) {
						movePlayerDown(player3);
					}
				}
				if (parts[1].equals("4")) {
					if (parts[0].trim().equals("LEFT")
							|| parts[0].trim().equals("left")) {
						movePlayerLeft(player4);
					}
					if (parts[0].trim().equals("UP")
							|| parts[0].trim().equals("up")) {
						movePlayerUp(player4);
					}
					if (parts[0].trim().equals("RIGHT")
							|| parts[0].trim().equals("right")) {
						movePlayerRight(player4);
					}
					if (parts[0].trim().equals("DOWN")
							|| parts[0].trim().equals("down")) {
						movePlayerDown(player4);
					}
				}
				command = "0,0";
			}
		}
	}

	public static char[][] getGameBoard() {
		return board.getBoardArray();
	}

	private void makePlayer(int i) {
		if (i == 0) {
			player1 = new Player(1, 1, '1');
			board.placePlayer(player1);
		} else if (i == 1) {
			player2 = new Player(board.getBoardX() - 2, 1, '2');
			board.placePlayer(player2);
		} else if (i == 2) {
			player3 = new Player(1, board.getBoardY() - 2, '3');
			board.placePlayer(player3);
		} else {
			player4 = new Player(board.getBoardX() - 2,
					board.getBoardY() - 2, '4');
			board.placePlayer(player4);
		}
	}

	public void movePlayerDown(Player player) {
		if (board.getBoardArrayElement(player.getXPosition() + 1,
				player.getYPosition()) == 'f'
				|| board.getBoardArrayElement(player.getXPosition() + 1,
						player.getYPosition()) == 'x') {
			player.setX(player.getXPosition() + 1);
			board.placePlayer(player);
			board.placeFloor(player.getXPosition() - 1, player.getYPosition());
		}
	}

	public void movePlayerUp(Player player) {
		if (board.getBoardArrayElement(player.getXPosition() - 1,
				player.getYPosition()) == 'f'
				|| board.getBoardArrayElement(player.getXPosition() - 1,
						player.getYPosition()) == 'x') {
			player.setX(player.getXPosition() - 1);
			board.placePlayer(player);
			board.placeFloor(player.getXPosition() + 1, player.getYPosition());
		}
	}

	public void movePlayerRight(Player player) {
		if (board.getBoardArrayElement(player.getXPosition(),
				player.getYPosition() + 1) == 'f'
				|| board.getBoardArrayElement(player.getXPosition(),
						player.getYPosition() + 1) == 'x') {
			player.setY(player.getYPosition() + 1);
			board.placePlayer(player);
			board.placeFloor(player.getXPosition(), player.getYPosition() - 1);
		}
	}

	public void movePlayerLeft(Player player) {
		if (board.getBoardArrayElement(player.getXPosition(),
				player.getYPosition() - 1) == 'f'
				|| board.getBoardArrayElement(player.getXPosition(),
						player.getYPosition() - 1) == 'x') {
			player.setY(player.getYPosition() - 1);
			board.placePlayer(player);
			board.placeFloor(player.getXPosition(), player.getYPosition() + 1);
		}
	}
}
