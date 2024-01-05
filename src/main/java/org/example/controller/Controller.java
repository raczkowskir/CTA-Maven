package org.example.controller;

import org.example.model.Model;
import org.example.model.Square;
import org.example.view.GameboardView;
import org.example.model.PocketSlots;
import org.example.view.SquareView;

public class Controller implements EventListener{

    private static Controller controller;
    private Model model;

    @Override
    public void moveToPocket(SquareView squareView, GameboardView gameboardView) {

        Square square = squareView.getSquare();
        square.onClick();
//        model.gameboard.removeSquare(square);

        PocketSlots freeSlot = square.getPocketSlot();
        gameboardView.addSquareViewsToPocket(squareView, freeSlot);

    }

    private Controller(Model model) {
        this.model = model;
    }

    public static Controller getInstance(Model model){
        if (controller == null) controller = new Controller(model);
        return controller;
    }
}
