package server;

import java.util.concurrent.Semaphore;

public class GameEngine implements Runnable {
	private static GameBoard board;
	private int numPlayers;
	private Player player1 = null, player2 = null, player3 = null,
			player4 = null;
	private int[] alive = { 1, 1, 1, 1 };
	// private static char[][] currentBoard;
	public static String command = "0,0";
	Semaphore semNewMessage;

	public GameEngine(int numPlayers, Semaphore semaphore) {
		board = new GameBoard(1);
		this.numPlayers = numPlayers;
		semNewMessage = semaphore;
		// currentBoard = board.getBoardArray();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < numPlayers; i++) {
			makePlayer(i);
		}

		while (true) {
			try {
				semNewMessage.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (command) {
				String[] parts = command.split(",");
				if (parts[1].equals("1")) {
					if (player1.getLives() > 0) {
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
						if (parts[0].trim().equals("BOMB")
								|| parts[0].trim().equals("bomb")) {
							placeBomb(player1);
						}
						board.placePlayer(player1);
					} else
						alive[0] = 0;
				}
				if (parts[1].equals("2")) {
					if (player2.getLives() > 0) {
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
						if (parts[0].trim().equals("BOMB")
								|| parts[0].trim().equals("bomb")) {
							placeBomb(player2);
						}
						board.placePlayer(player1);
					} else
						alive[1] = 0;
				}
				if (parts[1].equals("3")) {
					if (player3.getLives() > 0) {
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
						if (parts[0].trim().equals("BOMB")
								|| parts[0].trim().equals("bomb")) {
							placeBomb(player3);
						}
						board.placePlayer(player1);
					} else
						alive[2] = 0;
				}
				if (parts[1].equals("4")) {
					if (player4.getLives() > 0) {
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
						if (parts[0].trim().equals("BOMB")
								|| parts[0].trim().equals("bomb")) {
							placeBomb(player4);
						}
						board.placePlayer(player1);
					} else
						alive[3] = 0;
				}
				if (alive[0] == 0 && alive[1] == 0 && alive[2] == 0
						&& alive[3] == 0)
					break;
				command = "0,0";
			}
		}
		System.out.println("Game over");
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
			player4 = new Player(board.getBoardX() - 2, board.getBoardY() - 2,
					'4');
			board.placePlayer(player4);
		}
	}

	private void placeBomb(Player player) {
		// if(player.getBombs()>0){
		Thread bomb = new Thread(new BombFactory(player, board));
		bomb.start();
		// player.setBombs(player.getBombs() -1);
		// }
	}

	private void movePlayerDown(Player player) {
		if (player.getPlayerStatus()) {
			if (board.getBoardArrayElement(player.getXPosition() + 1,
					player.getYPosition()) == 'f'
					|| board.getBoardArrayElement(player.getXPosition() + 1,
							player.getYPosition()) == 'x') {
				player.setX(player.getXPosition() + 1);
				board.placePlayer(player);

				if (board.getBoardArrayElement(player.getXPosition() - 1,
						player.getYPosition()) == 'c'
						|| board.getBoardArrayElement(
								player.getXPosition() - 1,
								player.getYPosition()) == 'b')
					board.placeBomb(player.getXPosition() - 1,
							player.getYPosition());
				else {
					board.placeFloor(player.getXPosition() - 1,
							player.getYPosition());
				}
			}
			if (board.getBoardArrayElement(player.getXPosition() + 1,
					player.getYPosition()) == 'e') {
				player.setLives(player.getLives() - 1);
				if(player.getLives() > 0)
					player.setX(player.getXPosition() + 1);
				else board.placeFloor(player.getXPosition(),
						player.getYPosition());
				board.setExplosion(player.getXPosition(), player.getYPosition());
				
				if (board.getBoardArrayElement(player.getXPosition() - 1,
						player.getYPosition()) == 'b'
						|| board.getBoardArrayElement(
								player.getXPosition() - 1,
								player.getYPosition()) == 'c')
					board.placeBomb(player.getXPosition() - 1,
							player.getYPosition());
				else {
					board.placeFloor(player.getXPosition() - 1,
							player.getYPosition());
				}
			}
		}
	}

	private void movePlayerUp(Player player) {
		if (player.getPlayerStatus()) {
			if (board.getBoardArrayElement(player.getXPosition() - 1,
					player.getYPosition()) == 'f'
					|| board.getBoardArrayElement(player.getXPosition() - 1,
							player.getYPosition()) == 'x') {
				player.setX(player.getXPosition() - 1);
				board.placePlayer(player);
				if (board.getBoardArrayElement(player.getXPosition() + 1,
						player.getYPosition()) == 'c'
						|| board.getBoardArrayElement(
								player.getXPosition() + 1,
								player.getYPosition()) == 'b')
					board.placeBomb(player.getXPosition() + 1,
							player.getYPosition());
				else {
					board.placeFloor(player.getXPosition() + 1,
							player.getYPosition());
				}
			}
			if (board.getBoardArrayElement(player.getXPosition() - 1,
					player.getYPosition()) == 'e') {
				player.setLives(player.getLives() - 1);
				if(player.getLives() > 0)
					player.setX(player.getXPosition() - 1);
				else board.placeFloor(player.getXPosition(),
						player.getYPosition());
				board.setExplosion(player.getXPosition(), player.getYPosition());
				if (board.getBoardArrayElement(player.getXPosition() + 1,
						player.getYPosition()) == 'b'
						|| board.getBoardArrayElement(
								player.getXPosition() + 1,
								player.getYPosition()) == 'c')
					board.placeBomb(player.getXPosition() + 1,
							player.getYPosition());
				else {
					board.placeFloor(player.getXPosition() + 1,
							player.getYPosition());
				}
			}
		}
	}

	private void movePlayerRight(Player player) {
		if (player.getPlayerStatus()) {
			if (board.getBoardArrayElement(player.getXPosition(),
					player.getYPosition() + 1) == 'f'
					|| board.getBoardArrayElement(player.getXPosition(),
							player.getYPosition() + 1) == 'x') {
				player.setY(player.getYPosition() + 1);
				board.placePlayer(player);
				if (board.getBoardArrayElement(player.getXPosition() - 1,
						player.getYPosition()) == 'b'
						|| board.getBoardArrayElement(player.getXPosition(),
								player.getYPosition() - 1) == 'c')
					board.placeBomb(player.getXPosition(),
							player.getYPosition() - 1);
				else {
					board.placeFloor(player.getXPosition(),
							player.getYPosition() - 1);
				}
			}
			if (board.getBoardArrayElement(player.getXPosition(),
					player.getYPosition() + 1) == 'e') {
				Thread playerSleepThread = new Thread(new PlayerSleep(player));
				playerSleepThread.start();
				player.setLives(player.getLives() - 1);
				if(player.getLives() > 0)
					player.setY(player.getYPosition() + 1);
				else board.placeFloor(player.getXPosition(),
						player.getYPosition());
				board.setExplosion(player.getXPosition(), player.getYPosition());
				if (board.getBoardArrayElement(player.getXPosition() - 1,
						player.getYPosition()) == 'b'
						|| board.getBoardArrayElement(player.getXPosition(),
								player.getYPosition() - 1) == 'c')
					board.placeBomb(player.getXPosition(),
							player.getYPosition() - 1);
				else {
					board.placeFloor(player.getXPosition(),
							player.getYPosition() - 1);
				}
			}
		}
	}

	private void movePlayerLeft(Player player) {
		if (player.getPlayerStatus()) {
			if (board.getBoardArrayElement(player.getXPosition(),
					player.getYPosition() - 1) == 'f'
					|| board.getBoardArrayElement(player.getXPosition(),
							player.getYPosition() - 1) == 'x') {
				player.setY(player.getYPosition() - 1);
				board.placePlayer(player);
				if (board.getBoardArrayElement(player.getXPosition() + 1,
						player.getYPosition()) == 'b'
						|| board.getBoardArrayElement(player.getXPosition(),
								player.getYPosition() + 1) == 'c')
					board.placeBomb(player.getXPosition(),
							player.getYPosition() + 1);
				else {
					board.placeFloor(player.getXPosition(),
							player.getYPosition() + 1);
				}
			}
			if (board.getBoardArrayElement(player.getXPosition(),
					player.getYPosition() - 1) == 'e') {
				player.setLives(player.getLives() - 1);
				if(player.getLives() > 0)
					player.setY(player.getYPosition() - 1);
				else board.placeFloor(player.getXPosition(),
						player.getYPosition());
				board.setExplosion(player.getXPosition(), player.getYPosition());
				if (board.getBoardArrayElement(player.getXPosition() + 1,
						player.getYPosition()) == 'b'
						|| board.getBoardArrayElement(player.getXPosition(),
								player.getYPosition() + 1) == 'c')
					board.placeBomb(player.getXPosition(),
							player.getYPosition() + 1);
				else {
					board.placeFloor(player.getXPosition(),
							player.getYPosition() + 1);
				}
			}
		}
	}
}
