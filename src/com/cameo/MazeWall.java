package com.cameo;

/**
 * Fills a series of adjacent squares to form a wall that the snake cannot move through. If the snake hits the wall,
 * the game is over. Much of this code mirrors Snake.
 */
public class MazeWall extends VisualComponentLargerThanASquare{

    public MazeWall(int maxX, int maxY, int squareSize){
        this.maxX = maxX;
        this.maxY = maxY;
        this.squareSize = squareSize;
        this.size = 5; //Number of squares in each maze wall

        //Create and fill mazeWallSquares with 0s
        gameSquares = new int[maxX][maxY];
        fillGameSquaresWithZeros();
        createMazeWall();
    }

    //Generates a random location for maze wall start. Currently not in use.
    /*
    protected void moveMazeWall(Snake s) {
        Random r = new Random();
        boolean mazeWallInSnake = true;
        while (mazeWallInSnake == true) {
            //Generate random wall location
            firstSegmentX = r.nextInt(SnakeGame.xSquares);
            firstSegmentY = r.nextInt(SnakeGame.ySquares);
            mazeWallInSnake = s.isVisualComponentSegment(firstSegmentX, firstSegmentY);
        }
    }
    */

    protected void createMazeWall() {

        /*
        Code similar to how the snake was created, except visualize the screen being divided into four equal quadrants
        Maze walls will form an L with the point of the L starting at the center of each quadrant.
        Quadrants are numbered 1 to 4 counter-clockwise, starting with the lower right quadrant as 1.

        The following statements are cast just in case we have an odd number.
        */

        int oneFourthScreenX = (int) maxX / 4; //center of left quadrants
        int threeFourthsScreenX = (int) maxX * 3 / 4; //center of right quadrants

        int oneFourthScreenY = (int) maxY / 4;  //center of lower quadrants
        int threeFourthsScreenY = (int) maxY * 3 / 4; //center of upper quadrants

        //Quadrant 1 maze wall
        gameSquares[threeFourthsScreenX][threeFourthsScreenY] = 1;
        gameSquares[threeFourthsScreenX + 1][threeFourthsScreenY] = 1;
        gameSquares[threeFourthsScreenX + 2][threeFourthsScreenY] = 1;
        gameSquares[threeFourthsScreenX][threeFourthsScreenY + 1] = 1;
        gameSquares[threeFourthsScreenX][threeFourthsScreenY + 2] = 1;

        //Quadrant 2 maze wall
        gameSquares[oneFourthScreenX][threeFourthsScreenY] = 1;
        gameSquares[oneFourthScreenX - 1][threeFourthsScreenY] = 1;
        gameSquares[oneFourthScreenX - 2][threeFourthsScreenY] = 1;
        gameSquares[oneFourthScreenX][threeFourthsScreenY + 1] = 1;
        gameSquares[oneFourthScreenX][threeFourthsScreenY + 2] = 1;

        //Quadrant 3 maze wall
        gameSquares[oneFourthScreenX][oneFourthScreenY] = 1;
        gameSquares[oneFourthScreenX - 1][oneFourthScreenY] = 1;
        gameSquares[oneFourthScreenX - 2][oneFourthScreenY] = 1;
        gameSquares[oneFourthScreenX][oneFourthScreenY - 1] = 1;
        gameSquares[oneFourthScreenX][oneFourthScreenY - 2] = 1;

        //Quadrant 4 maze wall
        gameSquares[threeFourthsScreenX][oneFourthScreenY] = 1;
        gameSquares[threeFourthsScreenX + 1][oneFourthScreenY] = 1;
        gameSquares[threeFourthsScreenX + 2][oneFourthScreenY] = 1;
        gameSquares[threeFourthsScreenX][oneFourthScreenY - 1] = 1;
        gameSquares[threeFourthsScreenX][oneFourthScreenY - 2] = 1;
    }
}
