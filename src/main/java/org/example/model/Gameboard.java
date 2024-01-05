package org.example.model;

import org.example.model.layers.Layers;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Gameboard {
    public final int POINT_SIZE_PIXELS = 20;
    public int[][] gameboard;
    public List<Square> squaresOnTheBoard = new ArrayList<>();
    public List<Point> pointsUsed = new ArrayList<>();

    public Gameboard() {

        gameboard = Layers.LAYER_1.getLayer();
    }

    public void giveCoordinates(Queue<Square> allSquaresReadyToPlace){

        Square square = allSquaresReadyToPlace.poll();

        for (int y = 0; y < gameboard.length; y++) {
            for (int x = 0; x < gameboard[0].length; x++) {
                Point point = new Point(y, x);
                if (pointsUsed.contains(point)) continue;

                if (gameboard[y][x] == 1){
                    addUsedPoints(y, x);

                    square.setTopLeftPoint(setCoordinates(x, y));
                    square.setTopRightPoint(setCoordinates(x + 1, y));
                    square.setBottomLeftPoint(setCoordinates(x,y + 1 ));
                    square.setBottomRightPoint(setCoordinates(x + 1,y + 1 ));

                    squaresOnTheBoard.add(square);
                    square = allSquaresReadyToPlace.poll();
                }
            }
        }
    }

    private void addUsedPoints(int y, int x) {
        pointsUsed.add(new Point(y, x));
        pointsUsed.add(new Point(y + 1, x));
        pointsUsed.add(new Point(y, x + 1));
        pointsUsed.add(new Point(y + 1, x + 1));
    }

    private Point setCoordinates(int x, int y) {

        Point point = new Point(POINT_SIZE_PIXELS * x, POINT_SIZE_PIXELS * y);
        return point;
    }

    public void removeSquare(Square square){
        squaresOnTheBoard.remove(square);
        Point topLeftPoint = square.getTopLeftPoint();
        Point topRightPoint = square.getTopRightPoint();
        Point bottomLeftPoint = square.getBottomLeftPoint();
        Point bottomRightPoint = square.getBottomRightPoint();

        gameboard[topLeftPoint.y/POINT_SIZE_PIXELS][topLeftPoint.x/POINT_SIZE_PIXELS] = 0;
        gameboard[topRightPoint.y/POINT_SIZE_PIXELS][topRightPoint.x/POINT_SIZE_PIXELS] = 0;
        gameboard[bottomLeftPoint.y/POINT_SIZE_PIXELS][bottomLeftPoint.x/POINT_SIZE_PIXELS] = 0;
        gameboard[bottomRightPoint.y/POINT_SIZE_PIXELS][bottomRightPoint.x/POINT_SIZE_PIXELS] = 0;
    }


}
