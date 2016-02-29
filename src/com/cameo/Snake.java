package com.cameo;

import java.awt.Point;
import java.util.LinkedList;

public class Snake extends VisualComponentLargerThanASquare{

	final int DIRECTION_UP = 0;
	final int DIRECTION_DOWN = 1;
	final int DIRECTION_LEFT = 2;
	final int DIRECTION_RIGHT = 3;  //These are completely arbitrary numbers. 

	private boolean hitWall = false;
	private boolean ateTail = false;
	private boolean hitComponent = false;

	//private int snakeSquares[][];  //represents all of the squares on the screen
	//NOT pixels!
	//A 0 means there is no part of the snake in this square
	//A non-zero number means part of the snake is in the square
	//The head of the snake is 1, rest of segments are numbered in order

	private int currentHeading;  //Direction snake is going in, or direction user is telling snake to go
	private int lastHeading;    //Last confirmed movement of snake. See moveSnake method

	private int growthIncrement = 2; //how many squares the snake grows after it eats a kibble

	private int justAteMustGrowThisMuch = 0;

	private int snakeHeadX, snakeHeadY; //store coordinates of head - first segment

	private boolean warpWallsOn = false; //snake cannot go through walls without dying unless this is set to true

	public Snake(int maxX, int maxY, int squareSize){
		this.maxX = maxX;
		this.maxY = maxY;
		this.squareSize = squareSize;
        this.firstSegmentX = snakeHeadX;
        this.firstSegmentY = snakeHeadY;
        this.size = 3; //size of snake - how many segments?
		//Create and fill snakeSquares with 0s
		gameSquares = new int[maxX][maxY];
		fillGameSquaresWithZeros();
		createStartSnake();
	}

	protected void createStartSnake(){
		//snake starts as 3 horizontal squares in the center of the screen, moving left
		int screenXCenter = (int) maxX/2;  //Cast just in case we have an odd number
		int screenYCenter = (int) maxY/2;  //Cast just in case we have an odd number

		gameSquares[screenXCenter][screenYCenter] = 1;
        gameSquares[screenXCenter+1][screenYCenter] = 2;
        gameSquares[screenXCenter+2][screenYCenter] = 3;

		snakeHeadX = screenXCenter;
		snakeHeadY = screenYCenter;

		currentHeading = DIRECTION_LEFT;
		lastHeading = DIRECTION_LEFT;
		
		justAteMustGrowThisMuch = 0;
	}

	public void snakeUp(){
		if (currentHeading == DIRECTION_UP || currentHeading == DIRECTION_DOWN) { return; }
		currentHeading = DIRECTION_UP;
	}
	public void snakeDown(){
		if (currentHeading == DIRECTION_DOWN || currentHeading == DIRECTION_UP) { return; }
		currentHeading = DIRECTION_DOWN;
	}
	public void snakeLeft(){
		if (currentHeading == DIRECTION_LEFT || currentHeading == DIRECTION_RIGHT) { return; }
		currentHeading = DIRECTION_LEFT;
	}
	public void snakeRight(){
		if (currentHeading == DIRECTION_RIGHT || currentHeading == DIRECTION_LEFT) { return; }
		currentHeading = DIRECTION_RIGHT;
	}

	protected void moveSnake(){
		//Called every clock tick
		
		//Must check that the direction snake is being sent in is not contrary to current heading
		//So if current heading is down, and snake is being sent up, then should ignore.
		//Without this code, if the snake is heading up, and the user presses left then down quickly, the snake will back into itself.
		if (currentHeading == DIRECTION_DOWN && lastHeading == DIRECTION_UP) {
			currentHeading = DIRECTION_UP; //keep going the same way
		}
		if (currentHeading == DIRECTION_UP && lastHeading == DIRECTION_DOWN) {
			currentHeading = DIRECTION_DOWN; //keep going the same way
		}
		if (currentHeading == DIRECTION_LEFT && lastHeading == DIRECTION_RIGHT) {
			currentHeading = DIRECTION_RIGHT; //keep going the same way
		}
		if (currentHeading == DIRECTION_RIGHT && lastHeading == DIRECTION_LEFT) {
			currentHeading = DIRECTION_LEFT; //keep going the same way
		}

		if (SnakeGame.mazeWall.isVisualComponentSegment(snakeHeadX, snakeHeadY)){
			hitComponent = true;
		}

		//Did you hit the wall, snake? 
		//Or eat your tail? Don't move. 

		if (hitWall == true || ateTail == true || hitComponent == true) {
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}

		//Use snakeSquares array, and current heading, to move snake

		//Put a 1 in new snake head square
		//increase all other snake segments by 1
		//set tail to 0 if snake did not just eat
		//Otherwise leave tail as is until snake has grown the correct amount 

		//Find the head of the snake - snakeHeadX and snakeHeadY

		//Increase all snake segments by 1
		//All non-zero elements of array represent a snake segment

		for (int x = 0 ; x < maxX ; x++) {
			for (int y = 0 ; y < maxY ; y++){
				if (gameSquares[x][y] != 0) {
                    gameSquares[x][y]++;
				}
			}
		}

		//now identify where to add new snake head
		if (currentHeading == DIRECTION_UP) {		
			//Subtract 1 from Y coordinate so head is one square up
			snakeHeadY-- ;
		}
		if (currentHeading == DIRECTION_DOWN) {		
			//Add 1 to Y coordinate so head is 1 square down
			snakeHeadY++ ;
		}
		if (currentHeading == DIRECTION_LEFT) {		
			//Subtract 1 from X coordinate so head is 1 square to the left
			snakeHeadX -- ;
		}
		if (currentHeading == DIRECTION_RIGHT) {		
			//Add 1 to X coordinate so head is 1 square to the right
			snakeHeadX ++ ;
		}

		//This makes snake hit the wall. Warp walls are initially turned off during the game. The user can turn warp
        //walls on at any point during the game by pressing w.

		if (snakeHeadX >= maxX || snakeHeadX < 0 || snakeHeadY >= maxY || snakeHeadY < 0 ) {
            //if warp walls are off, the snake hits the wall if the snake head moves off the
			if (warpWallsOn == false){
				hitWall = true;
				SnakeGame.setGameStage(SnakeGame.GAME_OVER);
				return;
			}
			if (warpWallsOn == true){
				hitWall = false;
				if (snakeHeadX >= maxX || snakeHeadX < 0) {
					//absolute value http://www.tutorialspoint.com/java/java_numbers.htm
					snakeHeadX = maxX - Math.abs(snakeHeadX);
				}
				else if (snakeHeadY >= maxY || snakeHeadY < 0){
					snakeHeadY = maxY - Math.abs(snakeHeadY);
				}
			}
		}

		//This makes the snake eat its tail
		if (gameSquares[snakeHeadX][snakeHeadY] != 0) {
			ateTail = true;
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return;
		}

		//Otherwise, the game is still on. Add new head
        gameSquares[snakeHeadX][snakeHeadY] = 1;

		//If snake did not just eat, then remove tail segment to keep snake the same length.
		//Find highest number, which should now be the same as snakeSize+1, and set to 0
		
		if (justAteMustGrowThisMuch == 0) {
			for (int x = 0 ; x < maxX ; x++) {
				for (int y = 0 ; y < maxY ; y++){
					if (gameSquares[x][y] == size+1) {
                        gameSquares[x][y] = 0;
					}
				}
			}
		}
		else {
			//Snake has just eaten. Leave tail as is. Decrease justAte... variable by 1.
			justAteMustGrowThisMuch -- ;
			size ++;
		}
		
		lastHeading = currentHeading; //Update last confirmed heading
	}



	protected boolean didHitWall(){
		return hitWall;
	}

	protected boolean didEatTail(){
		return ateTail;
	}

	public void turnWarpWallsOn() {
		this.warpWallsOn = true;
	}

	public boolean didEatKibble(Kibble kibble) {
		//If the x,y coordinates equal the x,y coordinates of the head, the snake is considered to have eaten the kibble, and should grow
		//Is this kibble in the snake? It should be in the same square as the snake's head
		if (kibble.getKibbleX() == snakeHeadX && kibble.getKibbleY() == snakeHeadY){
			justAteMustGrowThisMuch += growthIncrement;
			return true;
		}
		return false;
	}

	//This doesn't appear to change the game...
//	public String toString(){
//		String textsnake = "";
//		//This looks the wrong way around. Actually need to do it this way or snake is drawn flipped 90 degrees.
//		for (int y = 0 ; y < maxY ; y++) {
//			for (int x = 0 ; x < maxX ; x++){
//				textsnake = textsnake + snakeSquares[x][y];
//			}
//			textsnake += "\n";
//		}
//		return textsnake;
//	}

	public boolean wonGame() {

		//If all of the squares have snake segments in, the snake has eaten so much kibble 
		//that it has filled the screen. Win!
		for (int x = 0 ; x < maxX ; x++) {
			for (int y = 0 ; y < maxY ; y++){
				if (gameSquares[x][y] == 0) {
					//there is still empty space on the screen, so haven't won
					return false;
				}
			}
		}
		//But if we get here, the snake has filled the screen. win!
		SnakeGame.setGameStage(SnakeGame.GAME_WON);
		
		return true;
	}

	public void reset() {
		hitWall = false;
		ateTail = false;
        hitComponent = false;
        warpWallsOn = false;

		fillGameSquaresWithZeros();
		createStartSnake();
	}

	public boolean isGameOver() {
		if (hitWall == true || ateTail == true){
			SnakeGame.setGameStage(SnakeGame.GAME_OVER);
			return true;
		}
		return false;
	}
}


