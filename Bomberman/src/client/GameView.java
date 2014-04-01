package client;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class GameView extends JPanel implements Runnable, Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ArrayList<Character>> boardArray = null;
	private char playerNum;
	private int columnCount;
	private int rowCount;
	private JFrame f;
	private BufferedImage spriteDown = null, spriteBomb = null,
			spriteExplosionYellow = null, spriteDestructible = null,
			spriteMonster = null, portal = null;
	private Semaphore newReceived;
	private Client client;
	private GameLobby lobby;

	public GameView(ArrayList<ArrayList<Character>> args, char playerNum,
			Semaphore newReceived, boolean isPlayer, Client client,
			GameLobby lobby) {
		boardArray = args;
		this.playerNum = playerNum;
		
		this.lobby = lobby;
		columnCount = boardArray.get(0).size();
		rowCount = boardArray.size() - 1;
		this.newReceived = newReceived;
		this.client = client;
		if (isPlayer) {
			Keyer keyListener = new Keyer();
			keyListener.addObserver(this);
			setFocusable(true);
			requestFocusInWindow();
			addKeyListener(keyListener);
		}
		try {
			spriteDown = ImageIO.read(new File("resources/bmanDown.png"));
			spriteBomb = ImageIO.read(new File("resources/bmanBomb.png"));
			spriteExplosionYellow = ImageIO.read(new File(
					"resources/bmanExplosionYellow.png"));
			spriteDestructible = ImageIO.read(new File(
					"resources/bmanDestructible.png"));
			spriteMonster = ImageIO.read(new File("resources/bmanMonster.png"));
			portal = ImageIO.read(new File("resources/portal2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		lobby.closeLobby();
		f = new JFrame("Bomberman");
		f.add(this);
		f.pack();
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Bomberman");
		f.setLocationRelativeTo(null);
		f.setAlwaysOnTop(true);
		f.setFocusable(true);
		f.setVisible(true);
		synchronized (this) {
			try {
				newReceived.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (client.getInGame()) {
				try {
					boardArray = ClientReceive.getTileMap();
					char temp = boardArray.get(0).get(0);
					if (temp == playerNum) {
						client.setIsWinner(true);
						client.setInGame(false);
						closeGameView();
					}
					else if (temp == '0' || temp == '1' || temp == '2' || temp == '3'
							|| temp == '4') {
						client.setWinner(Integer.valueOf(temp));
						client.setInGame(false);
						closeGameView();
					}
					f.validate();
					f.repaint();
					newReceived.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void closeGameView() {
		f.setVisible(false);
		f.dispose(); // Destroy the JFrame object
	}

	@Override
	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

	public char getBoardElement(int x, int y){
		return boardArray.get(x).get(y);
	}
	public void update(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			/*
			client.setPrev(boardArray.get(client.getPosition()[1]).get(client.getPosition()[0]));
			client.setPosition(0, client.getPosition()[0]+1);
			System.out.println(client.getPosition()[0]+" "+client.getPosition()[1]);
			client.startTimer();
			*/
			client.setCurrMove("right");
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			/*
			client.setPrev(boardArray.get(client.getPosition()[1]).get(client.getPosition()[0]));
			client.setPosition(0, client.getPosition()[0]-1);
			System.out.println(client.getPosition()[0]+" "+client.getPosition()[1]);
			client.startTimer();
			*/
			client.setCurrMove("left");
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			client.setCurrMove("up");
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			client.setCurrMove("down");
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			client.setCurrMove("bomb");
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return (new Dimension(605, 605));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// this.setBackground(new Color(30, 150, 30));
		Graphics2D g2d = (Graphics2D) g.create();
		List<Rectangle> cells = new ArrayList<>(columnCount * rowCount);
		;

		int width = getWidth();
		int height = getHeight();

		int cellWidth = width / columnCount;
		int cellHeight = height / rowCount;

		int xOffset = (width - (columnCount * cellWidth)) / 2;
		int yOffset = (height - (rowCount * cellHeight)) / 2;

		if (cells.isEmpty()) {
			for (int row = 0; row < rowCount; row++) {
				for (int col = 0; col < columnCount; col++) {
					Rectangle cell = new Rectangle(xOffset + (col * cellWidth),
							yOffset + (row * cellHeight), cellWidth, cellHeight);
					cells.add(cell);
				}
			}
		}

		// fill grid with colours and sprites
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {

				Rectangle cell = cells.get(row + (col * columnCount));
				char temp = boardArray.get(row).get(col);
				if (temp == '1' || temp == '2' || temp == '3' || temp == '4'
						|| temp == 'c') {
					// g2d.setColor(new Color(200, 180, 160));
					// g2d.fill(cell);
					g.drawImage(spriteDown, col * cellWidth, row * cellHeight,
							cellWidth, cellHeight, null);
				} else if (temp == 'd') { // destructible
					g.drawImage(spriteDestructible, col * cellWidth, row
							* cellHeight, cellWidth, cellHeight, null);
				} else if (temp == 'P') { // portal
					g.drawImage(portal, col * cellWidth, row
							* cellHeight, cellWidth, cellHeight, null);
				} else if (temp == 'e') { // explosion
					g.drawImage(spriteExplosionYellow, col * cellWidth, row
							* cellHeight, cellWidth, cellHeight, null);
				} else if (temp == 'm') { // monster
					g.drawImage(spriteMonster, col * cellWidth, row
							* cellHeight, cellWidth, cellHeight, null);
				} else if (temp == 'b') { // bomb
					g.drawImage(spriteBomb, col * cellWidth, row * cellHeight,
							cellWidth, cellHeight, null);
				} else if (temp == 'x') {
					// g2d.setColor(new Color(200, 180, 160));
					// g2d.fill(cell);
				} else if (temp == 'w') {
					g2d.setColor(Color.GRAY);
					g2d.fill(cell);
				}
				/*
				 * else { // floor ('f') g2d.setColor(new Color(200, 180, 160));
				 * g2d.fill(cell); }
				 */
			}
		}

		g2d.setColor(new Color(200, 180, 160));
		for (Rectangle cell : cells) {
			g2d.draw(cell);
		}

		g2d.dispose();
	}
}
