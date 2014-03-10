package server;
import java.net.InetAddress;


public class Player {
	private int x, y, lives; 
	private char playerNumber;
	private InetAddress IP;

	Player(int x,int y,char num,InetAddress IP){
		this.x = x;
		this.y = y;
		playerNumber = num;
		this.IP = IP;
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
	public InetAddress getIPAddress(){
		return IP;
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
}
