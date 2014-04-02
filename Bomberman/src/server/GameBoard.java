package server;

public class GameBoard {

	private char[][] boardArray = {
		{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
		{'w','x','x','f','f','d','f','d','d','f','f','f','d','f','x','x','w'},
		{'w','x','w','d','w','d','w','f','w','d','w','f','w','f','w','x','w'},
		{'w','d','d','d','f','d','d','f','f','d','d','d','f','d','d','d','w'},
		{'w','f','w','f','w','f','w','d','w','f','w','f','w','f','w','f','w'},
		{'w','d','d','d','f','d','f','d','f','d','d','d','f','d','d','f','w'},
		{'w','f','w','d','w','d','w','f','w','f','w','d','w','d','w','d','w'},
		{'w','d','d','f','f','d','f','f','f','d','f','f','d','d','f','f','w'},
		{'w','d','w','f','w','f','w','f','w','f','w','f','w','f','w','d','w'},
		{'w','f','d','d','d','d','f','d','f','d','f','d','f','d','f','d','w'},
		{'w','f','w','f','w','f','w','d','w','f','w','d','w','f','w','f','w'},
		{'w','d','d','d','f','d','d','f','f','d','f','d','f','d','f','d','w'},
		{'w','d','w','d','w','f','w','f','w','d','w','f','w','f','w','d','w'},
		{'w','f','d','f','f','d','d','d','f','f','d','f','d','d','d','f','w'},
		{'w','x','w','d','w','d','w','f','w','d','w','d','w','f','w','x','w'},
		{'w','x','x','d','f','d','f','f','f','d','f','d','f','d','x','x','w'},
		{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'} 
		};
	private boolean left, right, down, up;

	public GameBoard(int gameSize) {
		// use XML parse to make correct game board array
		this.left = false; right = false; this.up = false; this.down = false;
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
		if(player.getLives()>0){
			boardArray[player.getXPosition()][player.getYPosition()] = player.getPlayerNum();
		}else{
			boardArray[player.getXPosition()][player.getYPosition()] = 'f';
		}
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
		char temp;
		if(boardArray[x][y] == player.getPlayerNum())
			player.decLives();
		boardArray[x][y] = 'e';
		if ((temp = boardArray[x+1][y]) != 'w'){
			if(temp == player.getPlayerNum())
				player.decLives();
			if(temp == 'd'){
				down = true;
				System.out.println("down");
			}
			boardArray[x+1][y] = 'e';
		}
		if ((temp = boardArray[x-1][y]) != 'w'){
			if(temp == player.getPlayerNum())
				player.decLives();
			if(temp == 'd'){
				up = true;
				System.out.println("up");
			}
			boardArray[x-1][y] = 'e';
		}
		if ((temp = boardArray[x][y+1]) != 'w'){
			if(temp == player.getPlayerNum())
				player.decLives();
			if(temp == 'd'){
				right = true;
				System.out.println("right");
			}
			boardArray[x][y+1] = 'e';
		}
		if ((temp = boardArray[x][y-1]) != 'w'){
			if(temp == player.getPlayerNum())
				player.decLives();
			if(temp == 'd'){
				left = true;
				System.out.println("left");
			}
			boardArray[x][y-1] = 'e';
		}
	}
	public int tryPowerUp(){
		int temp = (int) (Math.random()*4);
		if(temp == 1){
			return 1;
		}else if(temp == 2){
			return 2;
		}else {
			return 0;
		}
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
		if (boardArray[x][y+1] == 'e'){
			if(right && tryPowerUp() == 1){
				placeElement(x, y+1, 'B');
			}else {
				boardArray[x][y+1] = 'f';
			}
		}
		if (boardArray[x][y-1] == 'e'){
			if(left && tryPowerUp() == 1){
				placeElement(x, y-1, 'B');
			}else {
				boardArray[x][y-1] = 'f';
			}
		}
		if (boardArray[x-1][y] == 'e'){
			if(up && tryPowerUp() == 1){
				placeElement(x-1, y, 'B');
			}else {
				boardArray[x-1][y] = 'f';
			}
		}
		if (boardArray[x+1][y] == 'e'){
			if(down && tryPowerUp() == 1){
				placeElement(x+1, y, 'B');
			}else {
				boardArray[x+1][y] = 'f';
			}
		}
		right = false; left = false; up = false; down = false;
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
