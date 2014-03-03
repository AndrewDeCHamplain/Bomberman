import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class GameView implements Runnable{

	public static char[][] boardArray = null;
	/*public static char[][] boardArray = {{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
										{'w','x','1','f','f','f','f','f','f','f','f','f','f','f','x','x','w'},
										{'w','x','w','f','w','f','w','f','w','f','w','f','w','f','w','x','w'},
										{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
										{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
										{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
										{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
										{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
										{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
										{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
										{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
										{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
										{'w','f','w','f','w','f','w','f','w','f','w','f','w','f','w','f','w'},
										{'w','f','f','f','f','f','f','f','f','f','f','f','f','f','f','f','w'},
										{'w','x','w','f','w','f','w','f','w','f','w','f','w','f','w','x','w'},
										{'w','x','x','f','f','f','f','f','f','f','f','f','f','f','x','x','w'},
										{'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'}
										};
	
	public static void main(String[] args) {
        new GameView();
    }
    */

    public GameView(char[][] args){
    	boardArray = args;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Bomberman");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new GamePane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class GamePane extends JPanel {

    	
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int columnCount = boardArray.length;
        private int rowCount = boardArray[0].length;
        private List<Rectangle> cells;
        private Point selectedCell;
        private BufferedImage sprite_down = null;

        public GamePane() {
        	
            cells = new ArrayList<>(columnCount * rowCount);
            MouseAdapter mouseHandler;
            mouseHandler = new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    Point point = e.getPoint();

                    int width = getWidth();
                    int height = getHeight();

                    int cellWidth = width / columnCount;
                    int cellHeight = height / rowCount;

                    int column = e.getX() / cellWidth;
                    int row = e.getY() / cellHeight;

                    selectedCell = new Point(column, row);
                    repaint();

                }
            };
            addMouseMotionListener(mouseHandler);
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
            Graphics2D g2d = (Graphics2D) g.create();

            int index; 	
           
            int width = getWidth();
            int height = getHeight();

            int cellWidth = width / columnCount;
            int cellHeight = height / rowCount;

            int xOffset = (width - (columnCount * cellWidth)) / 2;
            int yOffset = (height - (rowCount * cellHeight)) / 2;

            if (cells.isEmpty()) {
                for (int row = 0; row < rowCount; row++) {
                    for (int col = 0; col < columnCount; col++) {
                        Rectangle cell = new Rectangle(
                                xOffset + (col * cellWidth),
                                yOffset + (row * cellHeight),
                                cellWidth,
                                cellHeight);             
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
                	
                	index = row + (col * columnCount);
                	Rectangle cell = cells.get(index);
                	
                	if(boardArray[row][col] == '1'){
                		g2d.setColor(new Color(200, 180, 160));
                		//g2d.fill(cell);
                		g.drawImage(sprite_down, col*cellWidth, row*cellHeight, cellWidth, cellHeight, null);
                		
                	}
                	else if(boardArray[row][col] == 'x'){
                		g2d.setColor(new Color(200, 180, 160));
                		//g2d.fill(cell);
                	}
                	else if(boardArray[row][col] == 'w'){
                		g2d.setColor(Color.GRAY);
                		g2d.fill(cell);
                	}
                	/*else { // floor ('f')
                		g2d.setColor(new Color(200, 180, 160));
                		g2d.fill(cell);
                	}*/
                }
            }
			
			
            g2d.setColor(new Color(200, 180, 160));
            for (Rectangle cell : cells) {
                g2d.draw(cell);
            }
            
            
            
            g2d.dispose();
        }
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new GameView(boardArray);
	}
}
