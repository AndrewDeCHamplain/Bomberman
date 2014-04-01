package client;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOver extends JPanel implements Runnable, KeyListener{

	/**
	 * 
	 */
	private JFrame f;
	private BufferedImage endBackground;
	private JLabel backgroundLabel, gameInfoLabel;
	private GameOver backgroundPanel;
	private JPanel gameInfoPanel;
	private static final long serialVersionUID = 1L;
	private boolean waiting;

	public GameOver(int winner, int playerNum) {
		waiting = true;
		if(winner == playerNum)
			gameInfoLabel = new JLabel("YOU WON!");
		else if(winner == 1)
			gameInfoLabel = new JLabel("PLAYER 1 WON!");
		else if(winner == 2)
			gameInfoLabel = new JLabel("PLAYER 2 WON!");
		else if(winner == 3)
			gameInfoLabel = new JLabel("PLAYER 3 WON!");
		else if(winner == 4)
			gameInfoLabel = new JLabel("PLAYER 4 WON!");
		else 
			gameInfoLabel = new JLabel("NO WINNER");
		this.setBackground(new Color(210,210,210));
		if (winner == playerNum) {
			try {
				endBackground = ImageIO.read(new File(
						"resources/winnerBackground.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				endBackground = ImageIO.read(new File("resources/loserBackground.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		backgroundLabel = new JLabel(new ImageIcon(endBackground));
		
		gameInfoPanel = new JPanel();
		gameInfoPanel.setBackground(new Color(210,210,210));
		gameInfoPanel.add(gameInfoLabel);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		f = new JFrame("Bomberman");
		backgroundPanel = this;
		backgroundPanel.add(backgroundLabel);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(gameInfoPanel);
		panel.add(backgroundPanel);
		this.addKeyListener(this);
		f.add(panel);
		f.pack();
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Bomberman");
		f.setLocationRelativeTo(null);
		f.setAlwaysOnTop(true);
		setFocusable(true);
		f.setVisible(true);
		while(waiting){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		f.setVisible(false);
		f.dispose();
	}
	public void pressEnter() {
		try {
			requestFocus(true);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
	        robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Dimension getPreferredSize() {
		return (new Dimension(500, 470));
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			waiting = false;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
