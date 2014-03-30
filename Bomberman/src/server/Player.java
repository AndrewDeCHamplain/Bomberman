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
	public char getPlayerNum(){
		return playerNumber;
	}
	public int getYPosition(){
		return y;
	}
	public int getXPosition(){
		return x;
	}
	public int getHP(){
		return lives;
	}
	public String getPrevMove(){
		return prevMove;
	}
	public int getBombs(){
		return bombs;
	}
	public int getLives(){
		return lives;
	}
	public boolean getPlayerStatus() {
		return playerStatus;
	}
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
	public void setLives(int lives){
		this.lives = lives;
	}
	public void setPlayerNumber(char playerNum){
		playerNumber = playerNum;
	}
	public void setPrevMove(String prevMove){
		this.prevMove = prevMove;
	}
	public void setBombs(int bombs){
		this.bombs = bombs;
	}
	public void setPlayerStatus(boolean status) {
		playerStatus = status;
	}
}
