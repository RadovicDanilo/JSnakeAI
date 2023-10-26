package model.agent;

import core.ApplicationFramework;
import model.game.Direction;

public class AgentA extends Agent{
    //The most simple and inefficient solution


    public AgentA() {

    }

    @Override
    public void GeneratePath() {
        super.GeneratePath();
        super.getDirectionsToApple().add(Direction.DOWN);
        super.getDirectionsToApple().add(Direction.DOWN);
        super.getDirectionsToApple().add(Direction.DOWN);
        super.getDirectionsToApple().add(Direction.DOWN);
        super.getDirectionsToApple().add(Direction.DOWN);

    }
}
