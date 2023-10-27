package model.agent;

import core.ApplicationFramework;
import model.game.Direction;

import java.awt.desktop.AppForegroundListener;

public class AgentA extends Agent{
    //The most simple and inefficient solution


    public AgentA() {

    }

    @Override
    public void GeneratePath() {
        for(int i = 2;i<ApplicationFramework.getInstance().height-1;i++){
            super.getDirectionsToApple().add(Direction.DOWN);
        }
        super.getDirectionsToApple().add(Direction.RIGHT);
        for (int i = 1; i < ApplicationFramework.getInstance().width/2; i++) {
            for (int j = 0; j < ApplicationFramework.getInstance().height-2; j++) {
                super.getDirectionsToApple().add(Direction.UP);
            }
            super.getDirectionsToApple().add(Direction.RIGHT);
            for (int j = 0; j < ApplicationFramework.getInstance().height-2; j++) {
                super.getDirectionsToApple().add(Direction.DOWN);
            }
            super.getDirectionsToApple().add(Direction.RIGHT);
        }
        for(int i = 0;i<ApplicationFramework.getInstance().height-1;i++){
            super.getDirectionsToApple().add(Direction.UP);
        }
        for(int i = 0;i<ApplicationFramework.getInstance().height-1;i++){
            super.getDirectionsToApple().add(Direction.LEFT);
        }
        super.getDirectionsToApple().add(Direction.DOWN);
        super.getDirectionsToApple().add(Direction.DOWN);
    }
}
