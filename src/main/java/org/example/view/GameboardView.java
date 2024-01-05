package org.example.view;

import org.example.model.Model;
import org.example.model.PocketSlots;
import org.example.model.Square;
import org.example.model.SquareColor;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameboardView extends JPanel {
    public List<SquareView> squareViews = new ArrayList<>();
    private PocketView pocketView;
    private Model model;
    private TreeMap<PocketSlots, SquareView> squareViewsInPocket;

    public GameboardView(Model model) {
        this.model = model;

        setLayout(null);
        setBackground(Color.LIGHT_GRAY);

        drawSquares();
        drawPocket();
        initiateMap();
        repaint();
    }

    public void drawSquares(){

        for (Square square : model.gameboard.squaresOnTheBoard) {
            SquareView squareView = new SquareView(model, square);
            squareView.gameboardView = this;
            squareView.setBounds(square.getPoint().x, square.getPoint().y, squareView.squareSize, squareView.squareSize);
            squareViews.add(squareView);
            add(squareView);
        }
    }

    public void drawPocket(){
        pocketView = new PocketView();
        pocketView.setBounds(pocketView.X, pocketView.Y, pocketView.WIDTH, pocketView.HEIGHT);
        pocketView.setVisible(true);
        add(pocketView);
    }

    private void initiateMap(){
        squareViewsInPocket = new TreeMap<>();

        squareViewsInPocket.put(PocketSlots.SLOT_1, null);
        squareViewsInPocket.put(PocketSlots.SLOT_2, null);
        squareViewsInPocket.put(PocketSlots.SLOT_3, null);
        squareViewsInPocket.put(PocketSlots.SLOT_4, null);
        squareViewsInPocket.put(PocketSlots.SLOT_5, null);
        squareViewsInPocket.put(PocketSlots.SLOT_6, null);
        squareViewsInPocket.put(PocketSlots.SLOT_7, null);
    }

    public void addSquareViewsToPocket(SquareView squareView, PocketSlots pocketSlots){
        squareView.setLocation(pocketSlots.getX(), pocketSlots.getY());
        squareViewsInPocket.put(pocketSlots, squareView);
        if (haveTriples(squareView)) removeTriples(squareView);

        revalidate();
        repaint();
    }

    private void removeTriples(SquareView squareView){

        List<PocketSlots> freedSlots = new ArrayList<>();

        SquareColor color = squareView.getSquare().getSquareColor();
        for (Map.Entry<PocketSlots, SquareView> squareViewEntry : squareViewsInPocket.entrySet()) {
            if (squareViewEntry.getValue() == null) continue;

            SquareColor valueColor = squareViewEntry.getValue().getSquare().getSquareColor();
            if (color.equals(valueColor)){
                freedSlots.add(squareViewEntry.getKey());
                remove(squareViewEntry.getValue());
                squareViews.remove(squareViewEntry.getValue());
            }
        }

        for (PocketSlots freedSlot : freedSlots) {
            squareViewsInPocket.put(freedSlot, null);
        }
    }

    private boolean haveTriples(SquareView squareView){
        int amount = 0;
        SquareColor color = squareView.getSquare().getSquareColor();

        for (SquareView value : squareViewsInPocket.values()) {
            if (value == null) continue;
            SquareColor valueColor = value.getSquare().getSquareColor();
            if (color.equals(valueColor)){
                amount++;
            }
        }

        return amount >= 3;
    }
}
