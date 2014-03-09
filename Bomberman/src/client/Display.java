package client;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Display extends JPanel implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static char[][] boardArray = {
			{ 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w',
					'w', 'w', 'w', 'w' },
			{ 'w', '1', 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'x', '2', 'w' },
			{ 'w', 'x', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'x', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'f', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'f', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'f', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'f', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'f', 'w' },
			{ 'w', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'f', 'f', 'w' },
			{ 'w', 'x', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w', 'f', 'w',
					'f', 'w', 'x', 'w' },
			{ 'w', '4', 'x', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f', 'f',
					'f', 'x', '3', 'w' },
			{ 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w',
					'w', 'w', 'w', 'w' } };
	private List<Rectangle> cells;
	int columnCount = 17;
	int rowCount = 17;
	static char playerNum = '1';
	static int y = 1;
	static int x = 1;
	private BufferedImage sprite_down = null;
	private Image bg;
	private char c = 'e';

	public Display() {
		cells = new ArrayList<>(columnCount * rowCount);
		addKeyListener(this);
	}

	public static void main(String args[]) {

		JFrame f = new JFrame("Bomberman");
		Display d = new Display();
		f.add(d);
		f.pack();
		f.setResizable(true);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Bomberman");
		f.setLocationRelativeTo(null);
		f.setFocusable(true);
	}
	
	@Override
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Key Pressed");
		if (e.getKeyCode() == KeyEvent.VK_D) {
			boardArray[y][x] = boardArray[y][x++];
			boardArray[y][x] = playerNum;
			
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			boardArray[y][x] = boardArray[y][x--];
			boardArray[y][x] = playerNum;
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			boardArray[y][x] = boardArray[y--][x];
			boardArray[y][x] = playerNum;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			boardArray[y][x] = boardArray[y++][x];
			boardArray[y][x] = playerNum;
		}
		repaint(); 
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
		return (new Dimension(600, 600));
	}


	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// this.setBackground(new Color(30, 150, 30));
		Graphics2D g2d = (Graphics2D) g.create();

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

		try {
			sprite_down = ImageIO.read(new File("resources/bmanDown.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// fill grid with colours and sprites
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {

				Rectangle cell = cells.get(row + (col * columnCount));
				char temp = boardArray[row][col];
				if (temp == playerNum) {
					// g2d.setColor(new Color(200, 180, 160));
					// g2d.fill(cell);
					g.drawImage(sprite_down, col * cellWidth, row * cellHeight,
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
