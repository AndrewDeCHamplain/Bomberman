package server;

public class GameBoard {

	private static char[][] boardArray = {
		{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
		{'w','x','x','f','f','f','f','f','f','f','f','f','f','f','x','x','w'},
		{'w','x','w','f','w','f','w','f','w','f','w','f','w','f','w','x','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
		{'w','x','w','f','w','f','w','f','w','f','w','f','w','f','w','x','w'},
		{'w','x','x','f','f','f','f','f','f','f','f','f','f','f','x','x','w'},
		{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'} 
		};

	public GameBoard(int gameSize) {
		// use XML parse to make correct game board array
	}

	public static void placePlayer(Player player, int x, int y) {
		boardArray[x][y] = player.getPlayerNum();
		player.setX(x);
		player.setY(y);
		System.out.println(boardArray[x][y]);
	}
	public char[][] getBoardArray(){
		return boardArray;
	}
}
