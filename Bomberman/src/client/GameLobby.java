package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameLobby extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton readyButton;
	private JButton joinButton;
	private JButton spectateButton;
	private boolean joined;
	private JFrame f;
	private JPanel graphicPanel;
	private GameLobby d;
	private Client client;
	private BufferedImage lobbyBackground;
	private JLabel backgroundLabel;

	public GameLobby(Client client) {
		this.client = client;
		try {
			lobbyBackground = ImageIO.read(new File(
					"resources/lobbyBackground.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backgroundLabel = new JLabel(new ImageIcon(lobbyBackground));
		setLayout(new FlowLayout());
		joined = false;
		joinButton = new JButton("Join");
		readyButton = new JButton("Ready");
		spectateButton = new JButton("Spectate");
		joinButton.addActionListener(new ButtonListener());
		readyButton.addActionListener(new ButtonListener());
		spectateButton.addActionListener(new ButtonListener());
		add(joinButton);
		add(spectateButton);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		f = new JFrame("Bomberman");
		//f.setSize(800, 800);
		d = this;
		f.add(d, BorderLayout.NORTH);
		graphicPanel = new JPanel();
		graphicPanel.add(backgroundLabel);
		f.add(graphicPanel, BorderLayout.SOUTH);
		f.pack();
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Bomberman");
		f.setLocationRelativeTo(null);
		f.setAlwaysOnTop(true);
		f.setFocusable(true);
		f.setVisible(true);
	}

	public void closeLobby() {
		f.setVisible(false);
		f.dispose();
	}

	public void pressJoin() {
		joinButton.doClick();
	}

	public void pressStart() {
		readyButton.doClick();
	}

	public void pressSpectate() {
		spectateButton.doClick();
	}

	public void makeMessageDialog(String s) {
		JOptionPane.showMessageDialog(null,s);
		removeAll();
		add(joinButton);
		add(spectateButton);
		joinButton.setEnabled(false);
		invalidate();
		validate();
	}

	protected class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == joinButton) {
				if (joined) {
					JOptionPane.showMessageDialog(null,
							String.format("You have already joined the game!"));
				} else {
					joined = true;
					client.setCurrMove("join");
				}
				removeAll();
				add(readyButton);
				add(spectateButton);
				spectateButton.setEnabled(false);
				invalidate();
				validate();

			} else if (e.getSource() == readyButton) {
				client.setCurrMove("start");
			} else if (e.getSource() == spectateButton) {
				joinButton.setEnabled(false);
				readyButton.setEnabled(false);
				spectateButton.setEnabled(false);
				// JOptionPane.showMessageDialog(null,
				// String.format("You are a spectator for the next game."));
			}
		}
	}
}
