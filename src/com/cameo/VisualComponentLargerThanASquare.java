package com.cameo;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Cameo on 2/28/2016.
 */
public class VisualComponentLargerThanASquare {

    protected int gameSquares[][];  //represents all of the squares on the screen
    protected int maxX, maxY, squareSize;
    protected int size;   //number of segments (squares) of each component
    protected int firstSegmentX, firstSegmentY; //store coordinates of the first segment

    protected void fillGameSquaresWithZeros() {
        for (int x = 0; x < this.maxX; x++){
            for (int y = 0 ; y < this.maxY ; y++) {
                gameSquares[x][y] = 0;
            }
        }
    }

    protected LinkedList<Point> segmentsToDraw(){
        //Returns a list of the actual x and y coordinates of the top left of each component's segment
        //Useful for the Panel class to draw the component
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
        return segmentCoordinates;
    }

    //Checks to see if component is at a specific location (X,Y coordinates)
    public boolean isVisualComponentSegment(int Xcoordinate, int Ycoordinate) {
        if (gameSquares[Xcoordinate][Ycoordinate] == 0) {
            return false;
        }
        return true;
    }
}
