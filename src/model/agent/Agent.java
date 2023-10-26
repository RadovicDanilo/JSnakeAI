package model.agent;

import core.ApplicationFramework;
import model.game.Coordinates;
import model.game.Direction;

import java.util.ArrayList;

public abstract class Agent {
    private final int height = ApplicationFramework.getInstance().height;
    private final int width = ApplicationFramework.getInstance().width;
    private ArrayList<Direction> directionsToApple = new ArrayList<>();
    public void GeneratePath(){

    }

    public ArrayList<Direction> getDirectionsToApple() {
        return directionsToApple;
    }

    public void setDirectionsToApple(ArrayList<Direction> directionsToApple) {
        this.directionsToApple = directionsToApple;
    }
}
