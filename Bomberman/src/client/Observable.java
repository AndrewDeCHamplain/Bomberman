package client;
import java.awt.event.KeyEvent;

public interface Observable {
	public void NotifyObservers(KeyEvent keyEvent);
}
