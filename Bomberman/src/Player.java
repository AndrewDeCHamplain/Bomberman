import java.net.InetAddress;


public class Player {
	int x;
	int y;
	int HP;
	InetAddress IP;
	int playerNumber;
	Player(int X,int Y,int Num,InetAddress ip){
		x = X;
		y = Y;
		playerNumber = Num;
		IP = ip;
	}
}
