package client;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOver extends JPanel implements Runnable {

	/**
	 * 
	 */
	private JFrame f;
	private BufferedImage endBackground;
	private JLabel backgroundLabel, gameInfo;
	private GameOver gameOverPanel;
	private static final long serialVersionUID = 1L;

	public GameOver(boolean isWinner) {
		if (isWinner) {
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
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		f = new JFrame("Bomberman");
		f.setSize(300, 300);
		gameOverPanel = this;
		gameOverPanel.add(backgroundLabel);
		
		f.add(gameOverPanel);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Bomberman");
		f.setLocationRelativeTo(null);
		f.setAlwaysOnTop(true);
		f.setFocusable(true);
		f.setVisible(true);
	}

}
