package client;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Keyer implements KeyListener, Observable {

	private ArrayList<Observer> obsList;

	public Keyer(){
		obsList = new ArrayList<Observer>();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		NotifyObservers(e);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void NotifyObservers(KeyEvent keyEvent) {
		for (Observer obs : obsList) {
			obs.update(keyEvent);
		}
	}

	public void addObserver(Observer obs) {
		if (obs != null)
			obsList.add(obs);
	}

	public void delObserver(Observer obs) {
		if (obs != null)
			obsList.remove(obs);
	}

}
