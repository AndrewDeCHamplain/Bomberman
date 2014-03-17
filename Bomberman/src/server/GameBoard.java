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
		boardArray[player.getXPosition()][player.getYPosition()] = player.getPlayerNum();
	}
	public void placeFloor(int x, int y){
		boardArray[x][y] = 'f';
	}
	public void placeBomb(int x, int y){
		boardArray[x][y] = 'b';
	}
	public void placeBombAndPlayer(Player player){
		boardArray[player.getXPosition()][player.getYPosition()] = 'c';
	}
	public synchronized void placeExplosion(int x, int y, Player player){
		if(player.getBombs()>0){
			player.setBombs(player.getBombs()-1);
			boardArray[x][y] = 'e';
			if (boardArray[x+1][y] != 'w')
				boardArray[x+1][y] = 'e';
			if (boardArray[x-1][y] != 'w')
				boardArray[x-1][y] = 'e';
			if (boardArray[x][y+1] != 'w')
				boardArray[x][y+1] = 'e';
			if (boardArray[x][y-1] != 'w')
				boardArray[x][y-1] = 'e';
		}
	}
	public synchronized void removeExplosion(int x, int y, Player player){
		boardArray[x][y] = 'f';
		if (boardArray[x+1][y] == 'e')
			boardArray[x+1][y] = 'f';
		if (boardArray[x-1][y] == 'e')
			boardArray[x-1][y] = 'f';
		if (boardArray[x][y+1] == 'e')
			boardArray[x][y+1] = 'f';
		if (boardArray[x][y-1] == 'e')
			boardArray[x][y-1] = 'f';
	}
	public char getBoardArrayElement(int x, int y){
		return boardArray[x][y];
	}
	public int getBoardY(){
		return boardArray.length;
	}
	public int getBoardX(){
		return boardArray[0].length;
	}
}
