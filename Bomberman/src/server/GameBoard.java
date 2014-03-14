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
	public char[][] getBoardArray(){
		return boardArray;
	}
	public void placePlayer(Player player){
		System.out.println("Player x: "+player.getXPosition()+" y: "+player.getYPosition()+" Num: "+player.getPlayerNum());
		boardArray[player.getXPosition()][player.getYPosition()] = player.getPlayerNum();
		System.out.println(boardArray);
	}
	public void placeFloor(int x, int y){
		boardArray[x][y] = 'f';
	}
	public char getBoardArrayElement(int x, int y){
		return boardArray[x][y];
	}
	public int getBoardRows(){
		return boardArray.length;
	}
	public int getBoardCols(){
		return boardArray[0].length;
	}
}
