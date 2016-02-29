package com.cameo;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Cameo on 2/28/2016.
 */
public class VisualComponentsLargerThanASquare {

    private int gameSquares[][];  //represents all of the squares on the screen

    private int maxX, maxY, squareSize;

    private int size;   //size of snake - how many segments?

    private int firstSegmentX, firstSegmentY; //store coordinates of the first segment

    public VisualComponentsLargerThanASquare(int maxX, int maxY, int squareSize){
        this.maxX = maxX;
        this.maxY = maxY;
        this.squareSize = squareSize;
        //Create and fill snakeSquares with 0s
        gameSquares = new int[maxX][maxY];
        fillGameSquaresWithZeros();
        //createStartSnake();
    }

    private void fillGameSquaresWithZeros() {
        for (int x = 0; x < this.maxX; x++){
            for (int y = 0 ; y < this.maxY ; y++) {
                gameSquares[x][y] = 0;
            }
        }
    }

    public LinkedList<Point> segmentsToDraw(){
        //Return a list of the actual x and y coordinates of the top left of each snake segment
        //Useful for the Panel class to draw the snake
        LinkedList<Point> segmentCoordinates = new LinkedList<Point>();
        for (int segment = 1 ; segment <= size ; segment++ ) {
            //search array for each segment number
            for (int x = 0 ; x < maxX ; x++) {
                for (int y = 0 ; y < maxY ; y++) {
                    if (gameSquares[x][y] == segment){
                        //make a Point for this segment's coordinates and add to list
                        Point p = new Point(x * squareSize , y * squareSize);
                        segmentCoordinates.add(p);
                    }
                }
            }
        }
        //System.out.println(segmentCoordinates.toString());
        return segmentCoordinates;
    }

    public boolean isVisualComponentSegment(int Xcoordinate, int Ycoordinate) {
        if (gameSquares[Xcoordinate][Ycoordinate] == 0) {
            return false;
        }
        return true;
    }
}
