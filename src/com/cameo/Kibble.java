package com.cameo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Kibble {

	/** Identifies a random square to display a kibble
	 * Any square is ok, so long as it doesn't have any snake segments in it. 
	 * There is only one Kibble and when the snake eats it, then it moves. 
	 */

	private int kibbleX; //This is the x-coordinate square number (not pixel)
	private int kibbleY;  //This is the y-coordinate square number (not pixel)
	
	public Kibble(ArrayList v){
		//Kibble needs to know where other components are, so it does not create a kibble in the snake
		//Pick a random location for kibble, check if it is in the snake
		//If in snake, try again
		
		moveKibble(v);
	}
	
	protected void moveKibble(ArrayList<VisualComponentLargerThanASquare> v){
		
		Random rng = new Random();
		boolean kibbleInVisualComponent = true;
		while (kibbleInVisualComponent == true) {
			//Generate random kibble location
			kibbleX = rng.nextInt(SnakeGame.xSquares);
			kibbleY = rng.nextInt(SnakeGame.ySquares);
            for (VisualComponentLargerThanASquare component : v) {
                kibbleInVisualComponent = component.isVisualComponentSegment(kibbleX, kibbleY);
                if (kibbleInVisualComponent){
                    break;
                }
            }
		}
	}

	public int getKibbleX() {
		return kibbleX;
	}

	public int getKibbleY() {
		return kibbleY;
	}
}
