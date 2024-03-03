package model.agent;

import core.ApplicationFramework;
import model.game.Coordinate;
import model.game.Direction;

import java.util.ArrayList;

public abstract class Agent {
	public final int HEIGHT = ApplicationFramework.getInstance().HEIGHT;
	public final int WIDTH = ApplicationFramework.getInstance().WIDTH;
	private final ArrayList<Direction> directionsToApple = new ArrayList<>();
	
	public void GeneratePath() {
	
	}
	
	public void Wait() {
		try {
			Thread.sleep(5);
		}catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isLegaMove(ArrayList<Coordinate> snakeTemp, Direction direction) {
		Coordinate head = snakeTemp.get(snakeTemp.size() - 1).move(direction);
		return !((snakeTemp.contains(head) && !snakeTemp.get(0).equals(head)) || !ApplicationFramework.getInstance().getGame().getBoard().contains(head));
	}
	
	public ArrayList<Coordinate> useDirectionsOnSnake(ArrayList<Coordinate> snakeTemp, ArrayList<Direction> directionArrayList) {
		ArrayList<Coordinate> snake = (ArrayList<Coordinate>) snakeTemp.clone();
		for(Direction direction : directionArrayList) {
			snake.add(snake.get(snake.size() - 1).move(direction));
			if(snake.get(snake.size() - 1) != ApplicationFramework.getInstance().getGame().getAppleCoordinate())
				snake.remove(0);
		}
		return snake;
	}
	
	public int getHEIGHT() {
		return HEIGHT;
	}
	
	public int getWIDTH() {
		return WIDTH;
	}
	
	public ArrayList<Direction> getDirectionsToApple() {
		return directionsToApple;
	}
	
}
