package server;


public class Player {
	private int x, y, lives, bombs; 
	private char playerNumber;
	private String prevMove;
	private boolean playerStatus;

	Player(int x,int y,char num){
		this.x = x;
		this.y = y;
		playerNumber = num;
		lives = 2;
		bombs = 1;
		prevMove = "";
		playerStatus = true;
	}
	/**
	 * @return
	 */
	public char getPlayerNum(){
		return playerNumber;
	}
	/**
	 * @return
	 */
	public int getYPosition(){
		return y;
	}
	/**
	 * @return
	 */
	public int getXPosition(){
		return x;
	}
	/**
	 * @return
	 */
	public int getHP(){
		return lives;
	}
	/**
	 * @return
	 */
	public String getPrevMove(){
		return prevMove;
	}
	/**
	 * @return
	 */
	public int getBombs(){
		return bombs;
	}
	/**
	 * @return
	 */
	public int getLives(){
		return lives;
	}
	/**
	 * @return
	 */
	public boolean getPlayerStatus() {
		return playerStatus;
	}
	/**
	 * Sets the x value
	 * @param x
	 */
	public void setX(int x){
		this.x=x;
	}
	/**
	 * Sets the y value
	 * @param y
	 */
	public void setY(int y){
		this.y=y;
	}
	/**
	 * Sets the amount of lives the player has
	 * @param lives
	 */
	public void setLives(int lives){
		this.lives = lives;
	}
	/**
	 * Assigns a number to the player
	 * @param playerNum
	 */
	public void setPlayerNumber(char playerNum){
		playerNumber = playerNum;
	}
	/**
	 * Sets a move to the assigned key stroke used by the player
	 * @param prevMove
	 */
	public void setPrevMove(String prevMove){
		this.prevMove = prevMove;
	}
	/**
	 * Sets the bomb in the specified place
	 * @param bombs
	 */
	public void setBombs(int bombs){
		this.bombs = bombs;
	}
	/**
	 * Increments number of bombs by 1
	 * @param bombs
	 */
	public void incBombs(){
		this.bombs++;
	}
	/**
	 * Decrements number of bombs by 1
	 * @param bombs
	 */
	public void decBombs(){
		this.bombs--;
	}
	/**
	 * Sets the status of the player to 'alive' or dead'
	 * @param status
	 */
	public void setPlayerStatus(boolean status) {
		playerStatus = status;
	}
}
