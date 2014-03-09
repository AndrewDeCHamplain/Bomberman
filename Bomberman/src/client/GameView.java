package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GameView implements Runnable {

	public static ArrayList<ArrayList<Character>> boardArray = null;
	private static char playerNum;

	public GameView(ArrayList<ArrayList<Character>> args, char playerNum) {
		boardArray = args;
		GameView.playerNum = playerNum;
		System.out.println("Player num " + playerNum);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}

				JFrame frame = new JFrame("Bomberman");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				frame.add(new GamePane());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				// frame.setAlwaysOnTop(true);
				// create controller -> make as observable
				// add key listener
			}
		});
	}

	public class GamePane extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int columnCount = boardArray.get(0).size();
		private int rowCount = boardArray.size() - 1;
		private List<Rectangle> cells;
		private BufferedImage sprite_down = null;
		private Point selectedCell;

		public GamePane() {

			cells = new ArrayList<>(columnCount * rowCount);

			KeyAdapter keyHandler;

			keyHandler = new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {

					synchronized (Client.currMove) {
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
					}

				}
			};
			// repaint();
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(600, 600);
		}

		@Override
		public void invalidate() {
			cells.clear();
			selectedCell = null;
			super.invalidate();
		}

		@Override
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
						Rectangle cell = new Rectangle(xOffset
								+ (col * cellWidth), yOffset
								+ (row * cellHeight), cellWidth, cellHeight);
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
					char temp = boardArray.get(row).get(col);
					if (temp == GameView.playerNum) {
						// g2d.setColor(new Color(200, 180, 160));
						// g2d.fill(cell);
						g.drawImage(sprite_down, col * cellWidth, row
								* cellHeight, cellWidth, cellHeight, null);

					} else if (temp == 'x') {
						// g2d.setColor(new Color(200, 180, 160));
						// g2d.fill(cell);
					} else if (temp == 'w') {
						g2d.setColor(Color.GRAY);
						g2d.fill(cell);
					}
					/*
					 * else { // floor ('f') g2d.setColor(new Color(200, 180,
					 * 160)); g2d.fill(cell); }
					 */
				}
			}
			// g.drawImage(sprite_down, cellWidth, cellHeight, cellWidth,
			// cellHeight, null);

			g2d.setColor(new Color(200, 180, 160));
			for (Rectangle cell : cells) {
				g2d.draw(cell);
			}
			repaint();

			g2d.dispose();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new GameView(boardArray, playerNum);

	}

}
