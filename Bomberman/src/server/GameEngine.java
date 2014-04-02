package server;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class GameEngine implements Runnable {
	private GameBoard board;
	private Server server;
	private int numPlayers;
	private Player player1 = null, player2 = null, player3 = null,
			player4 = null;
	private int[] alive = { 0, 0, 0, 0 };
	private boolean inGame;
	// private static char[][] currentBoard;
	public String command = "0,0";
	private Semaphore semNewMessage;

	/**
	 * Initializes the Game Engine with the 2-d array board Initializes
	 * placement of players
	 * 
	 * @param numPlayers
	 * @param semNewMessage
	 * @param server
	 */
	public GameEngine(int numPlayers, Semaphore semNewMessage, Server server) {
		this.server = server;
		board = new GameBoard(1);
		inGame = true;
		this.numPlayers = numPlayers;
		this.semNewMessage = semNewMessage;
		// currentBoard = board.getBoardArray();
	}

	@Override
	/**
	 * Runs the game engine with the specifications of everything being initialized 
	 * on the board (sprite, elements, wall, floor, etc). Also allows the player to
	 * specify their commands using the keystrokes for each of the players(1-4)
	 * If the player dies the Game is Over.
	 */
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < numPlayers; i++) {
			makePlayer(i);
			alive[i] = 1;
		}
		int[] p1Win = { 1, 0, 0, 0 };
		int[] p2Win = { 0, 1, 0, 0 };
		int[] p3Win = { 0, 0, 1, 0 };
		int[] p4Win = { 0, 0, 0, 1 };
		int[] allDead = { 0, 0, 0, 0 };

		while (inGame) {
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
				if (Arrays.equals(alive, allDead)) {
					inGame = false;
					server.setInGame(false);
				}
				if (Arrays.equals(alive, p1Win) || Arrays.equals(alive, p2Win)
						|| Arrays.equals(alive, p3Win)
						|| Arrays.equals(alive, p4Win)) {
					board.placeElement(7, 7, 'P');
				}
				command = "0,0";
			}
		}
		System.out.println("Game over");
		if (Arrays.equals(alive, p1Win)) {
			board.placeElement(0, 0, '1');
		} else if (Arrays.equals(alive, p2Win)) {
			board.placeElement(0, 0, '2');
		} else if (Arrays.equals(alive, p3Win)) {
			board.placeElement(0, 0, '3');
		} else if (Arrays.equals(alive, p4Win)) {
			board.placeElement(0, 0, '4');
		} else
			board.placeElement(0, 0, '0');
	}

	/**
	 * @return the board 2-d
	 */
	public char[][] getGameBoard() {
		return board.getBoardArray();
	}

	/**
	 * Initializes players 1-4
	 * 
	 * @param i
	 */
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

	/**
	 * places the bomb at the specific position using the start command
	 * 
	 * @param player
	 */
	private void placeBomb(Player player) {
		// if(player.getBombs()>0){
		Thread bomb = new Thread(new BombFactory(player, board));
		bomb.start();
		// player.setBombs(player.getBombs() -1);
		// }
	}

	/**
	 * Allows the player to move up if there is no obstruction
	 * 
	 * @param player
	 */
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
					player.getYPosition()) == 'P') {
				inGame = false;
				server.setInGame(false);
			}
			if (board.getBoardArrayElement(player.getXPosition() + 1,
					player.getYPosition()) == 'e') {
				Thread playerSleepThread = new Thread(new PlayerSleep(player));
				playerSleepThread.start();
				player.setLives(player.getLives() - 1);
				if (player.getLives() > 0)
					player.setX(player.getXPosition() + 1);
				else {
					alive[(int) (player.getPlayerNum() - '0') - 1] = 0;
					player.setX(player.getXPosition() + 1);
					board.placeFloor(player.getXPosition(),
							player.getYPosition());
				}

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

	/**
	 * Moves the player up if there is no obstruction
	 * 
	 * @param player
	 */
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
					player.getYPosition()) == 'P') {
				inGame = false;
				server.setInGame(false);
			}
			if (board.getBoardArrayElement(player.getXPosition() - 1,
					player.getYPosition()) == 'e') {
				Thread playerSleepThread = new Thread(new PlayerSleep(player));
				playerSleepThread.start();
				player.setLives(player.getLives() - 1);
				if (player.getLives() > 0)
					player.setX(player.getXPosition() - 1);
				else {
					alive[(int) (player.getPlayerNum() - '0') - 1] = 0;
					player.setX(player.getXPosition() - 1);
					board.placeFloor(player.getXPosition(),
							player.getYPosition());
				}
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

	/**
	 * Allows the player to move right if there is no obstruction
	 * 
	 * @param player
	 */
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
					player.getYPosition() + 1) == 'P') {
				inGame = false;
				server.setInGame(false);
			}
			if (board.getBoardArrayElement(player.getXPosition(),
					player.getYPosition() + 1) == 'e') {
				Thread playerSleepThread = new Thread(new PlayerSleep(player));
				playerSleepThread.start();
				player.setLives(player.getLives() - 1);
				if (player.getLives() > 0)
					player.setY(player.getYPosition() + 1);
				else {
					alive[(int) (player.getPlayerNum() - '0') - 1] = 0;
					player.setY(player.getYPosition() + 1);
					board.placeFloor(player.getXPosition(),
							player.getYPosition());
				}
				
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

	/**
	 * Allows the player to move left if there is no obstruction
	 * 
	 * @param player
	 */
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
					player.getYPosition() - 1) == 'P') {
				inGame = false;
				server.setInGame(false);
			}
			if (board.getBoardArrayElement(player.getXPosition(),
					player.getYPosition() - 1) == 'e') {
				Thread playerSleepThread = new Thread(new PlayerSleep(player));
				playerSleepThread.start();
				player.setLives(player.getLives() - 1);
				if (player.getLives() > 0)
					player.setY(player.getYPosition() - 1);
				else {
					alive[(int) (player.getPlayerNum() - '0') - 1] = 0;
					player.setY(player.getYPosition() - 1);
					board.placeFloor(player.getXPosition(),
							player.getYPosition());
				}
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
