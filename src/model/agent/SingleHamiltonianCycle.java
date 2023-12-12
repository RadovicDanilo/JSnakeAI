package model.agent;

import model.game.Direction;

import java.util.ArrayList;

public class SingleHamiltonianCycle extends Agent {
	@Override
	public ArrayList<Direction> getDirectionsToApple() {
		ArrayList<Direction> directionsToApple = new ArrayList<>();
		for(int i = 0; i < getHEIGHT() - 3; i++) {
			directionsToApple.add(Direction.DOWN);
		}
		for(int i = 0; i < getWIDTH() / 2 - 1; i++) {
			directionsToApple.add(Direction.RIGHT);
			for(int j = 0; j < getHEIGHT()-2; j++) {
				directionsToApple.add(Direction.UP);
			}
			directionsToApple.add(Direction.RIGHT);
			for(int j = 0; j < getHEIGHT()-2; j++) {
				directionsToApple.add(Direction.DOWN);
			}
		}
		directionsToApple.add(Direction.RIGHT);
		for(int j = 0; j < getHEIGHT()-1; j++) {
			directionsToApple.add(Direction.UP);
		}
		for(int j = 0; j < getWIDTH()-1; j++) {
			directionsToApple.add(Direction.LEFT);
		}
		directionsToApple.add(Direction.DOWN);
		directionsToApple.add(Direction.DOWN);
		
		return directionsToApple;
	}
}
