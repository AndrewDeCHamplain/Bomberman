package server;

public class GameBoard {

	private char[][] boardArray = {
		{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
		{'w','x','x','f','m','f','d','f','f','f','f','f','d','f','x','x','w'},
		{'w','x','w','f','w','d','w','f','w','d','w','f','w','f','w','x','w'},
		{'w','f','d','d','f','f','f','f','f','d','f','f','d','d','f','d','w'},
		{'w','d','w','f','w','f','w','f','w','f','w','f','w','d','w','f','w'},
		{'w','f','d','f','f','f','f','d','f','f','d','f','f','f','f','f','w'},
		{'w','f','w','d','w','f','w','f','w','f','w','f','w','d','w','d','w'},
		{'w','d','f','f','f','f','f','d','f','d','f','f','f','d','f','f','w'},
		{'w','f','w','f','w','f','w','d','w','f','w','f','w','f','w','f','w'},
		{'w','f','f','f','d','f','d','f','f','d','f','f','d','d','f','f','w'},
		{'w','d','w','f','w','d','w','f','w','f','w','f','w','f','w','f','w'},
		{'w','f','d','d','f','d','f','f','f','d','f','f','f','d','f','d','w'},
		{'w','f','w','f','w','f','w','f','w','d','w','f','w','f','w','d','w'},
		{'w','f','d','f','f','f','f','d','f','f','f','f','d','f','f','f','w'},
		{'w','x','w','d','w','d','w','f','w','f','w','d','w','d','w','x','w'},
		{'w','x','x','d','f','f','d','f','f','f','f','d','f','f','x','x','w'},
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
	public synchronized void setExplosion(int x, int y){
		boardArray[x][y] = 'e';
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
	public void placeElement(int x, int y, char i) {
		boardArray[x][y] = i;
	}
}
