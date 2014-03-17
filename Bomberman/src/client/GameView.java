package client;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class GameView extends JPanel implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArrayList<ArrayList<Character>> boardArray = null;
	private static char playerNum;
	private int columnCount;
	private int rowCount;
	BufferedImage spriteDown = null, spriteBomb = null, spriteExplosionYellow = null,
			spriteDestructible = null, spriteMonster = null;
	Semaphore semaphore;

	public GameView(ArrayList<ArrayList<Character>> args, char playerNum, Semaphore semaphore) {
		boardArray = args;
		GameView.playerNum = playerNum;
		columnCount = boardArray.get(0).size();
		rowCount = boardArray.size() - 1;
		this.semaphore = semaphore;
		addKeyListener(this);
		
		try {
			spriteDown = ImageIO.read(new File("resources/bmanDown.png"));
			spriteBomb = ImageIO.read(new File("resources/bmanBomb.png"));
			spriteExplosionYellow = ImageIO.read(new File("resources/bmanExplosionYellow.png"));
			spriteDestructible = ImageIO.read(new File("resources/bmanDestructible.png"));
			spriteMonster = ImageIO.read(new File("resources/bmanMonster.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		JFrame f = new JFrame("Bomberman");
		GameView d = new GameView(boardArray, playerNum, semaphore);
		f.add(d);
		f.pack();
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Bomberman");
		f.setLocationRelativeTo(null);
		f.setAlwaysOnTop (true);
		f.setFocusable(true);
		f.setVisible(true);
		
		while(true){
			try {
				this.semaphore.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boardArray = ClientReceive.tileMap;
			f.validate();
			f.repaint();
		}
	}

	@Override
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_D) {
			Client.currMove = "right";
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			Client.currMove = "left";
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			Client.currMove = "up";
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			Client.currMove = "down";
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Client.currMove = "bomb";
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Dimension getPreferredSize() {
		return (new Dimension(605, 605));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// this.setBackground(new Color(30, 150, 30));
		Graphics2D g2d = (Graphics2D) g.create();
		List<Rectangle> cells = new ArrayList<>(columnCount * rowCount);;
		
		
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
				if (temp == '1' || temp == '2' || temp == '3' || temp == '4' || temp == 'c') {
					// g2d.setColor(new Color(200, 180, 160));
					// g2d.fill(cell);
					g.drawImage(spriteDown, col * cellWidth, row * cellHeight,
							cellWidth, cellHeight, null);
				} else if (temp == 'd'){ // destructible
					g.drawImage(spriteDestructible, col * cellWidth, row * cellHeight,
							cellWidth, cellHeight, null);
				} else if (temp == 'e'){ // explosion
					g.drawImage(spriteExplosionYellow, col * cellWidth, row * cellHeight,
							cellWidth, cellHeight, null);
				} else if (temp == 'm'){ // monster
					g.drawImage(spriteMonster, col * cellWidth, row * cellHeight,
							cellWidth, cellHeight, null);
				} else if (temp == 'b'){ // bomb
					g.drawImage(spriteBomb, col * cellWidth, row * cellHeight,
							cellWidth, cellHeight, null);
				} else if (temp == 'x'){
					// g2d.setColor(new Color(200, 180, 160));
					// g2d.fill(cell);
				} else if (temp == 'w'){
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
