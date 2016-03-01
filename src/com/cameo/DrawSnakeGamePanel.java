package com.cameo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

/** This class responsible for displaying the graphics, the snake, grid, kibble, instruction text and high score
 * 
 * @author Clara
 *
 */
public class DrawSnakeGamePanel extends JPanel {
	
	private static int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint
	
	private Snake snake;
	private Kibble kibble;
	private Score score;
	private MazeWall mazeWall;

    //The game currently is only set up to play WITH maze walls
	DrawSnakeGamePanel(Snake s, Kibble k, Score sc){
		this.snake = s;
		this.kibble = k;
		this.score = sc;
	}

	DrawSnakeGamePanel(Snake s, Kibble k, Score sc, MazeWall mw){
		this.snake = s;
		this.kibble = k;
		this.score = sc;
		this.mazeWall = mw;
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
            //Easy way to test game won is to uncomment displayGameWon(g) below and then intentionally lose the game.
            //displayGameWon(g);
        	displayGameOver(g);
        	break;
        }
        case 4: {
        	displayGameWon(g);
        	break;
        }
        }
    }

	protected void displayGameWon(Graphics g) {

        int maxX = SnakeGame.xPixelMaxDimension;
        int maxY = SnakeGame.yPixelMaxDimension;
        int squareSize = SnakeGame.squareSize;

        //How to randomly generate a color http://mathbits.com/MathBits/Java/Graphics/Color.htm

        //Fill each square with a randomly generated color
        //TODO Figure out why there is a one square border across the top and left (VERY annoying)

        for (int x = 0; x <= maxX; x += squareSize) {
            for (int y = 0; y <= maxY; y += squareSize) {
                    g.setColor(randomColors());
                    g.fillRect(x, y, x, y);
                }
                g.setColor(Color.BLACK);
                g.drawString("YOU WON SNAKE!!!", 200, 200);
            }
        }

	private void displayGameOver(Graphics g) {

        int maxX = SnakeGame.xPixelMaxDimension;
        int maxY= SnakeGame.yPixelMaxDimension;
        int squareSize = SnakeGame.squareSize;

        for (int x = 0; x <= maxX; x += squareSize) {
            g.setColor(randomColors());
            g.fillRect(x, 0, x, maxY);
        }

        g.setColor(Color.BLACK);
        g.clearRect(100,100,300,300);
		g.drawString("GAME OVER", 150, 150);
		
		String textScore = score.getStringScore();
		String textHighScore = score.getStringHighScore();
		String newHighScore = score.newHighScore();
		
		g.drawString("SCORE = " + textScore, 150, 230);
		
		g.drawString("HIGH SCORE = " + textHighScore, 150, 270);
		g.drawString(newHighScore, 150, 190);
		
		g.drawString("Press a key to play again", 150, 310);
		g.drawString("Press q to quit the game",150, 350);
    			
	}

	private void displayGame(Graphics g) {
		displayGameGrid(g);
		displaySnake(g);
		displayKibble(g);
        displayMazeWall(g);
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

    //TODO Change the display once the user turns warp walls on, so that they know they are on
	//Not currently functional
	protected void displayForWarpWallsOn(Graphics g){
		int maxX = SnakeGame.xPixelMaxDimension;
		int maxY= SnakeGame.yPixelMaxDimension;
		int squareSize = SnakeGame.squareSize;

		g.setColor(Color.GREEN);

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

		//Help with code for images-https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
		//Mouse image obtained from http://www.clker.com/clipart-pink-mouse-2.html
		Image kibbleImage;
		ImageIcon pinkMouse = new ImageIcon("pinkMouse.png");
		kibbleImage = pinkMouse.getImage();

		int x = kibble.getKibbleX() * SnakeGame.squareSize;
		int y = kibble.getKibbleY() * SnakeGame.squareSize;

		g.drawImage(kibbleImage, x+1, y+1, SnakeGame.squareSize-2, SnakeGame.squareSize-2, this);
	}

    private void displayMazeWall(Graphics g){

        //Draws the maze walls by filling the squares of the coordinates supplied by the mazeWall class
        LinkedList<Point> theCoordinates = mazeWall.segmentsToDraw();

        for (Point p : theCoordinates){
            //Draws maze walls in black
			g.setColor(Color.BLACK);
			g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
        }
    }

	private void displaySnake(Graphics g) {

		LinkedList<Point> coordinates = snake.segmentsToDraw();

        //snake head modified from http://www.how-to-draw-funny-cartoons.com/draw-a-snake.html
		Image headImage;
		ImageIcon snakeHead = new ImageIcon("cartoonSnakeRoundHead.png");
		headImage = snakeHead.getImage();

		Point head = coordinates.pop();
		g.drawImage(headImage, (int)head.getX(), (int)head.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);

		Image bodyImage;
		ImageIcon snakeBody = new ImageIcon("snakeBodySection.png");
		bodyImage = snakeBody.getImage();
		for (Point p : coordinates) {
			g.drawImage(bodyImage, (int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize, this);
			//g.fillRect((int)p.getX(), (int)p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
		}
	}

	private void displayInstructions(Graphics g) {

        int maxX = SnakeGame.xPixelMaxDimension;
        int maxY= SnakeGame.yPixelMaxDimension;
        int squareSize = SnakeGame.squareSize;

        //g.clearRect(0, 0, maxX, maxY);

        for (int y = 0; y <= maxY; y += squareSize) {
            g.setColor(randomColors());
            g.fillRect(0, y, maxX, y);
        }

        //g.clearRect(maxX/4, maxY/4, maxX * 3/4, maxY * 3/4);
        g.clearRect(100, 100, 300, 300);

        g.setColor(Color.BLACK);
        g.drawString("Press any key to begin playing Snake!",150,175);

        //TODO next version-allow user to choose whether maze walls are on or
        //Not currently functioning
        // g.drawString("Press m to play with maze walls", 150,250);

        g.drawString("Press q to quit the game at any time",150,250);
		g.drawString("Press w to turn warp walls on at any time",150,325);
    	}


    private Color randomColors(){
        int R = (int) (Math.random() * 256);
        int G = (int) (Math.random() * 256);
        int B = (int) (Math.random() * 256);
        Color randomColor = new Color(R, G, B);
        return randomColor;
    }
}

