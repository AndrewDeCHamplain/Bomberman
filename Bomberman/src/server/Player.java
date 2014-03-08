package server;
import java.net.InetAddress;


public class Player {
	int x, y, lives; 
	char playerNumber;
	InetAddress IP;

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
}
