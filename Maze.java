package com.comp2601.mazesolver;

public class Maze {

    private static int line = 10;
    private static int row;
    private static int col;
    private static final byte  EMPTY = 0;
    private static final byte  WALL = 1;
    private static final byte  PATH = 2;
    private static final byte  START = 3;
    private static final byte  END = 4;
    private static int startR,startC,endR,endC;
    private static byte[][] martix;
    //private static int[][] array = new int[][] { };

    public Maze(int numRows, int numCols,MainActivity mainActivity){
        row = numRows;
        col = numCols;
        martix = new byte [row][col];
        for(numRows =0; numRows<row; numRows++){
            for(numCols=0; numCols<col; numCols++){
                martix[numRows][numCols] = EMPTY;
            }
        }

    }
    public static void setEmpty(int r,int c)
    {

        martix[r][c]=EMPTY;
    }
    public static void setWall(int r,int c)
    {
        martix[r][c]=WALL;
    }
    public static void setStart(int r,int c)
    {
        martix[r][c]=START;
        startC =c;
        startR = r;
    }
    public static void setEnd(int r,int c)
    {
        martix[r][c]=END;
        endR = r;
        endC = c;
    }
    public static boolean getPath(int r,int c)
    {
        if (martix[r][c]==PATH){
            return true;
        }
        return false;
    }


    public static boolean wall(int r, int c){
        return martix[r][c]==WALL;
    }
    public static boolean visit(int r, int c){
        return martix[r][c]==PATH;
    }
    public static void move(int i, int j) {
        if (i == line-1 && j == line-1) {
            return;
        }

        else if (canMove(i, j, i, j+1)) {
            martix[i][j] = PATH;
            move(i, j + 1);
        }

        else if (canMove(i, j, i + 1, j)) {
            martix[i][j] = PATH;
            move(i + 1, j);
        }

        else if (canMove(i, j, i, j - 1)) {
            martix[i][j] = PATH;
            move(i, j - 1);
        }

        else if (canMove(i, j, i - 1, j+1)) {
            martix[i][j] = PATH;
            move(i - 1, j);
        }
    }

    public static boolean canMove(int i, int j, int targetX, int targetY) {
        // left
        if (targetX < 0 || targetY < 0) {

            return false;
        }
        // right
        else if (targetX >line-1 || targetY > line-1) {
            return false;
        }

        // can not path
        else if (martix[targetX][targetY] == WALL || martix[targetX][targetY] == PATH) {
            return false;
        }
        else if (wall(targetX,targetY))
            return false;
        else if (visit(targetX,targetY))
            return false;

        return true;

    }



}