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

    protected int mazeWallSize = 4 * SnakeGame.squareSize; //number of squares that make up the wall

    private int maxX, maxY, squareSize;

    protected int mazeWallStartX; //X-coordinant of start of wall
    protected int mazeWallStartY; //Y-coordinant of start of wall

    public MazeWall(Snake s) {
        moveMazeWall(s);
    }

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

    protected void createMazeWall(Graphics g){

        Random r = new Random();
        boolean verticleMazeWall = r.nextBoolean();
        if (verticleMazeWall){

            g.fillRect(mazeWallStartX, mazeWallStartY, mazeWallSize, SnakeGame.squareSize);
        }
        else{
            g.fillRect(mazeWallStartX, mazeWallStartY, SnakeGame.squareSize, mazeWallSize);
        }
    }

    public LinkedList<Point> mazeWallSegmentsToDraw(){
        //Return a list of the actual x and y coordinates of the maze wall
        //Useful for the Panel class to draw the maze wall
        LinkedList<Point> mazeWallSegmentCoordinates = new LinkedList<>();
        //make a Point for this segment's coordinates and add to list

        Point p = new Point(mazeWallStartX * squareSize , mazeWallStartY * squareSize);
        mazeWallSegmentCoordinates.add(p);

        Random r = new Random();
        boolean verticalMazeWall = r.nextBoolean();
        int x = getMazeWallStartX();
        int y = getMazeWallStartY();
        //int count = 0;
        if (verticalMazeWall){
            ArrayList<Integer> yCoor = new ArrayList<>();
            //for (y, y < mazeWallSize, y++){
                    p = new Point(x * squareSize , y+1 * squareSize);
                    mazeWallSegmentCoordinates.add(p);
                    //mazeWallStartX, mazeWallStartY, mazeWallSize, SnakeGame.squareSize);
                }

//        else{
//            mazeWallSegmentCoordinates.add(p);
//            mazeWallStartX, mazeWallStartY, SnakeGame.squareSize, mazeWallSize);
//        }

        return mazeWallSegmentCoordinates;
    }

    public int getMazeWallStartX() {
        return mazeWallStartX;
    }

    public void setMazeWallStartX(int mazeWallStartX) {
        this.mazeWallStartX = mazeWallStartX;
    }

    public int getMazeWallStartY() {
        return mazeWallStartY;
    }

    public void setMazeWallStartY(int mazeWallStartY) {
        this.mazeWallStartY = mazeWallStartY;
    }

}
