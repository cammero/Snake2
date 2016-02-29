package com.cameo;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Fills a series of adjacent squares to form a wall that the snake cannot move through. If the snake hits the wall,
 * the game is over. Much of this code mirrors Kibble.
 */
public class MazeWall {

    private int mazeWallSquares[][]; //represents all the squares on the screen

    protected int mazeWallSize = 5 * SnakeGame.squareSize; //number of squares that make up the wall

    private int maxX, maxY, squareSize;

    //protected int quadOneStartX, quadTwoStartX, quadThreeStartX, quadFourStartX; //X-coordinants of starts of walls
    //protected int quadOneStartY, quadTwoStartY, quadThreeStartY, quadFourStartY; //Y-coordinants of starts of walls


    public MazeWall(int maxX, int maxY, int squareSize){
        this.maxX = maxX;
        this.maxY = maxY;
        this.squareSize = squareSize;

        //Create and fill mazeWallSquares with 0s
        //Do I need this for the maze walls?
        mazeWallSquares = new int[maxX][maxY];
        fillMazeWallSquaresWithZeros();
        createMazeWall();
    }

    private void fillMazeWallSquaresWithZeros() {
        for (int x = 0; x < this.maxX; x++){
            for (int y = 0 ; y < this.maxY ; y++) {
                mazeWallSquares[x][y] = 0;
            }
        }
    }

    //Generates a random location for maze wall start. Currently not in use.
    /*
    protected void moveMazeWall(Snake s) {
        Random r = new Random();
        boolean mazeWallInSnake = true;
        while (mazeWallInSnake == true) {
            //Generate random wall location
            mazeWallStartX = r.nextInt(SnakeGame.xSquares);
            mazeWallStartY = r.nextInt(SnakeGame.ySquares);
            mazeWallInSnake = s.isSnakeSegment(mazeWallStartX, mazeWallStartY);
        }
    }
    */

    protected void createMazeWall() {

        /*
        Code similar to how the snake was created, except visualize the screen being divided into four equal quadrants
        Maze walls will form an L with the point of the L starting at the center of each quadrant.
        Quadrants are numbered 1 to 4 counter-clockwise, starting with the lower right quadrant as 1.

        The following are cast just in case we have an odd number.
        */

        int oneFourthScreenX = (int) maxX / 4; //center of left quadrants
        int threeFourthsScreenX = (int) maxX * 3 / 4; //center of right quadrants

        int oneFourthScreenY = (int) maxY / 4;  //center of lower quadrants
        int threeFourthsScreenY = (int) maxY * 3 / 4; //center of upper quadrants

        //Quadrant 1 maze wall
        mazeWallSquares[threeFourthsScreenX][threeFourthsScreenY] = 1;
        mazeWallSquares[threeFourthsScreenX + 1][threeFourthsScreenY] = 2;
        mazeWallSquares[threeFourthsScreenX + 2][threeFourthsScreenY] = 7;
        //mazeWallSquares[threeFourthsScreenX + 3][threeFourthsScreenY] = 4;
        mazeWallSquares[threeFourthsScreenX][threeFourthsScreenY + 1] = 5;
        mazeWallSquares[threeFourthsScreenX][threeFourthsScreenY + 2] = 6;
        //mazeWallSquares[threeFourthsScreenX][threeFourthsScreenY + 3] = 7;

        //Quadrant 2 maze wall
        mazeWallSquares[oneFourthScreenX][threeFourthsScreenY] = 1;
        mazeWallSquares[oneFourthScreenX - 1][threeFourthsScreenY] = 1;
        mazeWallSquares[oneFourthScreenX - 2][threeFourthsScreenY] = 1;
        mazeWallSquares[oneFourthScreenX][threeFourthsScreenY + 1] = 1;
        mazeWallSquares[oneFourthScreenX][threeFourthsScreenY + 2] = 1;

        //Quadrant 3 maze wall
        mazeWallSquares[oneFourthScreenX][oneFourthScreenY] = 1;
        mazeWallSquares[oneFourthScreenX - 1][oneFourthScreenY] = 1;
        mazeWallSquares[oneFourthScreenX - 2][oneFourthScreenY] = 1;
        mazeWallSquares[oneFourthScreenX][oneFourthScreenY - 1] = 1;
        mazeWallSquares[oneFourthScreenX][oneFourthScreenY - 2] = 1;

        //Quadrant 4 maze wall
        mazeWallSquares[threeFourthsScreenX][oneFourthScreenY] = 1;
        mazeWallSquares[threeFourthsScreenX + 1][oneFourthScreenY] = 2;
        mazeWallSquares[threeFourthsScreenX + 2][oneFourthScreenY] = 3;
        mazeWallSquares[threeFourthsScreenX][oneFourthScreenY - 1] = 4;
        mazeWallSquares[threeFourthsScreenX][oneFourthScreenY - 2] = 5;



        /*The following code could be used in the future to randomly generate wall squares
        Random r = new Random();
        boolean verticalMazeWall = r.nextBoolean();
        if (verticalMazeWall){
            g.fillRect(mazeWallStartX, mazeWallStartY, mazeWallSize, SnakeGame.squareSize);
        }
        else{
            g.fillRect(mazeWallStartX, mazeWallStartY, SnakeGame.squareSize, mazeWallSize);
        }
        */
    }

    protected LinkedList<Point> mazeWallSegmentsToDraw(){
        //Return a list of the actual x and y coordinates of the maze wall
        //Useful for the Panel class to draw the maze wall
        LinkedList<Point> mazeWallCoordinates = new LinkedList<>();
        //make a Point for this segment's coordinates and add to list

        //TODO create superclass that both MazeWall and
        for (int segment = 1 ; segment <= mazeWallSize ; segment++ ) {
            //search array for each segment number
            for (int x = 0 ; x < maxX ; x++) {
                for (int y = 0 ; y < maxY ; y++) {
                    //for (int x = 0 ; x < maxX ; x++) {
                    if (mazeWallSquares[x][y] == segment){
                        //make a Point for this segment's coordinates and add to list
                        Point p = new Point(x * squareSize , y * squareSize);
                        mazeWallCoordinates.add(p);
                    }
                }
            }
        }
        return mazeWallCoordinates;
    }
}
