package server;

public class GameBoard {

	private char[][] boardArray = {
		{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
		{'w','x','x','f','f','f','f','f','f','f','f','f','f','f','x','x','w'},
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
		{'w','x','x','f','f','f','f','f','f','f','f','f','f','f','x','x','w'},
		{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'} 
		};

	public GameBoard(int gameSize) {
		// use XML parse to make correct game board array
	}
	/**
	 * Returns the double array representing the game board.
	 */
	public char[][] getBoardArray(){
		return boardArray;
	}
	/**
	 * Places a player at a specific position given the specific
	 * x and y coordinates
	 * @param player Player
	 */
	public void placePlayer(Player player){
		boardArray[player.getXPosition()][player.getYPosition()] = player.getPlayerNum();
	}
	/**
	 * Assigns the 'floor' where the player can walk
	 * @param x int
	 * @param y int
	 */
	public void placeFloor(int x, int y){
		boardArray[x][y] = 'f';
	}
	/**
	 * Places the bomb at the specifically assigned x and y coordinate
	 * @param x int
	 * @param y int
	 */
	public void placeBomb(int x, int y){
		boardArray[x][y] = 'b';
	}
	/**
	 * Places the bomb and player together at the position
	 * where the player is positioned
	 * @param player Player
	 */
	public void placeBombAndPlayer(Player player){
		boardArray[player.getXPosition()][player.getYPosition()] = 'c';
	}
	/**
	 * Where necessarily the explosion of the bomb can initiate
	 * Also extends the explosion a few coordinates around the 
	 * area of the bomb placed.
	 * @param player Player
	 * @param x int
	 * @param y int
	 */
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
	/**
	 * Sets the explosion at the specific coordinates
	 * @param x int
	 * @param y int
	 */
	public synchronized void setExplosion(int x, int y){
		boardArray[x][y] = 'e';
	}
	/**
	 * After a delay removes the explosion from the players placed
	 * bomb position
	 * @param player Player
	 * @param x int
	 * @param y int
	 */
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
	/**
	 * returns the size of the board in terms of a 2-d array
	 * @param x int
	 * @param y int
	 */
	public char getBoardArrayElement(int x, int y){
		return boardArray[x][y];
	}
	/**
	 * @return length of board 2-d 
	 */
	public int getBoardY(){
		return boardArray.length;
	}
	/**
	 * @return length of board 2-d
	 */
	public int getBoardX(){
		return boardArray[0].length;
	}
	/**
	 * place a specific element at a given position with
	 * a corresponding respective character
	 * @param x int
	 * @param y int
	 * @param i char
	 */
	public void placeElement(int x, int y, char i) {
		boardArray[x][y] = i;
	}
}
