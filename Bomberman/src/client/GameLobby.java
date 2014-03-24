package client;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameLobby extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton startButton, joinButton, spectateButton;
	private boolean joined;
	private static JFrame f;
	
	public GameLobby(){
		
		setLayout(new FlowLayout());
		joined = false;
		joinButton = new JButton("Join");
		startButton = new JButton("Start");
		spectateButton = new JButton("Spectate");
		joinButton.addActionListener(new ButtonListener());
		startButton.addActionListener(new ButtonListener());
		spectateButton.addActionListener(new ButtonListener());
		add(joinButton);
		add(startButton);
		add(spectateButton);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		f = new JFrame("Bomberman");
		GameLobby d = new GameLobby();
		f.add(d);
		f.pack();
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Bomberman");
		f.setLocationRelativeTo(null);
		f.setAlwaysOnTop (true);
		f.setFocusable(true);
		f.setVisible(true);
	}
	public static void closeLobby() {
		f.setVisible(false);
		f.dispose(); //Destroy the JFrame object
	}
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == joinButton){
				if(joined){
					JOptionPane.showMessageDialog(null, String.format("You have already joined the game!"));
				}
				else{
					joined = true;
					Client.setCurrMove("join");
				}
			}
			else if(e.getSource() == startButton){
				Client.setCurrMove("start");
			}
			else if(e.getSource() == spectateButton){
				//Client.setCurrMove("spectate");
				System.out.println("You are a spectator for the next game.");
				JOptionPane.showMessageDialog(null, String.format("You are a spectator for the next game."));
			}
		}
		
	}
}

