package server;

public class GameBoard {

	public static char[][] boardArray;

	public GameBoard(int gameSize) {
		// use XML parse to make correct game board array
	}

	public static void placePlayer(Player player) {
		boardArray[player.getXPosition()][player.getYPosition()] = player
				.getPlayerNum();
		System.out.println(boardArray[player.getXPosition()][player
				.getYPosition()]);
	}


}
