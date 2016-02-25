package com.cameo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.*;

/** This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high score
 * 
 * @author Clara
 *
 */
public class DrawSnakeGamePanel extends JPanel {
	
	private static int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint
	
	private Snake snake;
	private Kibble kibble;
	private Score score;
	
	DrawSnakeGamePanel(Snake s, Kibble k, Score sc){
		this.snake = s;
		this.kibble = k;
		this.score = sc;
	}

	//sets size of window
	public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        gameStage = SnakeGame.getGameStage();
        
        switch (gameStage) {
        case 1: {
        	displayInstructions(g);
        	break;
        } 
        case 2 : {
        	displayGame(g);
        	break;
        }
        case 3: {
        	displayGameOver(g);
        	break;
        }
        case 4: {
        	displayGameWon(g);
        	break;
        }
        }
        
        
        
    }

	private void displayGameWon(Graphics g) {
		// TODO Replace this with something really special!
		g.clearRect(100,100,350,350);
		g.drawString("YOU WON SNAKE!!!", 150, 150);
		
	}
	private void displayGameOver(Graphics g) {

		g.clearRect(100,100,350,350);
		g.drawString("GAME OVER", 150, 150);
		
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String newHighScore = score.newHighScore();
		
		g.drawString("SCORE = " + textScore, 150, 250);
		
		g.drawString("HIGH SCORE = " + textHighScore, 150, 300);
		g.drawString(newHighScore, 150, 400);
		
		g.drawString("Press a key to play again", 150, 350);
		g.drawString("Press q to quit the game",150,400);		
    			
	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);	
	}

	private void displayGameGrid(Graphics g) {

		int maxX = SnakeGame.xPixelMaxDimension;
		int maxY= SnakeGame.yPixelMaxDimension;
		int squareSize = SnakeGame.squareSize;
		
		g.clearRect(0, 0, maxX, maxY);

		g.setColor(Color.BLUE);

		//Draw grid - horizontal lines
		for (int y=0; y <= maxY ; y+= squareSize){			
			g.drawLine(0, y, maxX, y);
		}
		//Draw grid - vertical lines
		for (int x=0; x <= maxX ; x+= squareSize){			
			g.drawLine(x, 0, x, maxY);
		}
	}

	private void displayKibble(Graphics g) {

		//Image help https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
		Image kibbleImage;
		ImageIcon pinkMouse = new ImageIcon("C:\\Users\\Cameo\\OneDrive\\Java Class\\pinkMouse.png");
		kibbleImage = pinkMouse.getImage();

//		//Draw the kibble in red
//		g.setColor(Color.RED);


		int x = kibble.getKibbleX() * SnakeGame.squareSize;
		int y = kibble.getKibbleY() * SnakeGame.squareSize;

		g.drawImage(kibbleImage, x+1, y+1, SnakeGame.squareSize-2, SnakeGame.squareSize-2, this);
		//g.fillRect(x+1, y+1, SnakeGame.squareSize-2, SnakeGame.squareSize-2);
		
	}

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();

		//Draw head in gray
		//g.setColor(Color.LIGHT_GRAY);

		//Draw head
		Image headImage;
		ImageIcon snakeHead = new ImageIcon("C:\\Users\\Cameo\\OneDrive\\Java Class\\cartoonSnakeRoundHead.png");
		headImage = snakeHead.getImage();

		Point head = coordinates.pop();
		g.drawImage(headImage, (int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);
		//g.fillRect((int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		
		//Draw rest of snake in black
		Image bodyImage;
		ImageIcon snakeBody = new ImageIcon("C:\\Users\\Cameo\\OneDrive\\Java Class\\snakeBodySection.png");
		bodyImage = snakeBody.getImage();
		//g.setColor(Color.BLACK);
		for (Point p : coordinates) {
			g.drawImage(bodyImage, (int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);
			//g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		}
	}

	private void displayRock(Graphics g) {}

	private void displayInstructions(Graphics g) {
        g.drawString("Press any key to begin!",150,200);
        g.drawString("Press q to quit the game",150,300);
    	}
	
    
}

