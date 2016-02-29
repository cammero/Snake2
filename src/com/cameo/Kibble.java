package com.cameo;

import javax.swing.*;
import java.util.Random;

public class Kibble {

	/** Identifies a random square to display a kibble
	 * Any square is ok, so long as it doesn't have any snake segments in it. 
	 * There is only one Kibble and when the snake eats it, then it moves. 
	 */

	//Mouse image obtained from http://www.clker.com/clipart-pink-mouse-2.html
	//Learned of ImageIcon class from http://zetcode.com/tutorials/javagamestutorial/snake/

	private int kibbleX; //This is the x-coordinant square number (not pixel)
	private int kibbleY;  //This is the y-coordinant square number (not pixel)
	
	public Kibble(Snake s){
		//Kibble needs to know where the snake is, so it does not create a kibble in the snake
		//Pick a random location for kibble, check if it is in the snake
		//If in snake, try again
		
		moveKibble(s);
	}
	
	protected void moveKibble(VisualComponentLargerThanASquare v){
		
		Random rng = new Random();
		boolean kibbleInVisualComponent = true;
		while (kibbleInVisualComponent == true) {
			//Generate random kibble location
			kibbleX = rng.nextInt(SnakeGame.xSquares);
			kibbleY = rng.nextInt(SnakeGame.ySquares);
			kibbleInVisualComponent = v.isVisualComponentSegment(kibbleX, kibbleY);
		}
	}
	protected void moveKibble(MazeWall mw){

	}

	public int getKibbleX() {
		return kibbleX;
	}

	public int getKibbleY() {
		return kibbleY;
	}
}
