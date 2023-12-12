package model.agent;

import core.ApplicationFramework;
import model.game.Coordinate;
import model.game.Direction;
import model.game.Game;

import java.util.ArrayList;

public class BFSWithHamiltonianCycle extends Agent {
	private final Game game = ApplicationFramework.getInstance().getGame();
	private ArrayList<Coordinate> snake;
	private ArrayList<Direction> directionsToApple;
	
	@Override
	public void GeneratePath() {
		snake = game.getSnake();
		this.directionsToApple = findPath();
	}
	
	private ArrayList<Direction> findPath() {
		
		
		
		return null;
	}
	
	
	
}
