package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameLobby extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JButton readyButton;
	private static JButton joinButton;
	private static JButton spectateButton;
	private JButton test1Button;
	private JButton test2Button;
	private JButton test3Button;
	private boolean joined;
	private static JFrame f;
	private JPanel adminPanel;
	private JLabel label;
	private JTextField textField;
	private GameLobby d;
	private Client client;
	private GameLobby lobby;

	public GameLobby(Client client) {
		this.client = client;
		lobby = this;
		setLayout(new FlowLayout());
		joined = false;
		joinButton = new JButton("Join");
		readyButton = new JButton("Ready");
		spectateButton = new JButton("Spectate");
		joinButton.addActionListener(new ButtonListener());
		readyButton.addActionListener(new ButtonListener());
		spectateButton.addActionListener(new ButtonListener());
		add(joinButton);
		//add(startButton);
		add(spectateButton);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		f = new JFrame("Bomberman");
		d = this;
		f.add(d, BorderLayout.NORTH);

		adminPanel = new JPanel();
		textField = new JTextField(10);
		label = new JLabel("Enter password for access to testing");
		textField.addActionListener(new TextFieldListener());
		adminPanel.add(label);
		adminPanel.add(textField);
		f.add(adminPanel, BorderLayout.SOUTH);

		f.pack();
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Bomberman");
		f.setLocationRelativeTo(null);
		f.setFocusable(true);
		f.setVisible(true);
	}

	public static void closeLobby() {
		f.setVisible(false);
		f.dispose();
	}

	@Override
	public Dimension getPreferredSize() {
		return (new Dimension(605, 605));
	}

	private class TextFieldListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (textField.getText().equals("admin")) {
				System.out.println("Enter pressed");
				adminPanel.removeAll();
				test1Button = new JButton("Test 1");
				test2Button = new JButton("Test 2");
				test3Button = new JButton("Test 3");
				test1Button.addActionListener(new ButtonListener());
				test2Button.addActionListener(new ButtonListener());
				test3Button.addActionListener(new ButtonListener());
				adminPanel.add(test1Button);
				adminPanel.add(test2Button);
				adminPanel.add(test3Button);
				f.validate();
				f.repaint();
			}
			else{
				JOptionPane.showMessageDialog(null,
						String.format("Incorrect password"));
			}
		}
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

	private class ButtonListener implements ActionListener {
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
				JOptionPane.showMessageDialog(null, String.format("You are a spectator for the next game."));
			} else if (e.getSource() == test1Button) {
				f.setVisible(false);
				Thread tester = new Thread(new TestDriver("testcases", lobby));
				tester.start();
			} else if (e.getSource() == test2Button) {
				JOptionPane.showMessageDialog(null,
						String.format("Starting Test 2 (not really)"));
			} else if (e.getSource() == test3Button) {
				JOptionPane.showMessageDialog(null,
						String.format("Starting Test 3 (not really)"));
			}
		}
	}
}
