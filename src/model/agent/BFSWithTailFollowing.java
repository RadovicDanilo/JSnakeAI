package model.agent;

import core.ApplicationFramework;
import model.game.Coordinate;
import model.game.Direction;
import model.game.Game;

import java.util.ArrayList;

public class BFSWithTailFollowing extends Agent {
	private final Game game = ApplicationFramework.getInstance().getGame();
	private ArrayList<Coordinate> snake;
	private ArrayList<Direction> directionsToApple;
	private ArrayList<Direction> pathToTail;
	
	@Override
	public void GeneratePath() {
		snake = ApplicationFramework.getInstance().getGame().getSnake();
		this.directionsToApple = findPathBFS();
	}
	int maxSize = 10000;
	public ArrayList<Direction> findPathBFS() {
		ArrayList<ArrayList<Direction>> listOfDirectionsToApple = new ArrayList<>();
		listOfDirectionsToApple.add(new ArrayList<>());
		
		while(listOfDirectionsToApple.size() != 0) {
			if(listOfDirectionsToApple.size() > maxSize && snake.size() > WIDTH) {
				hasPathToTail(new ArrayList<>(), snake, false);
				maxSize = 1000;
				return pathToTail;
			}
			for(ArrayList<Direction> directions : listOfDirectionsToApple) {
				ArrayList<Coordinate> currentSnake = useDirectionsOnSnake(snake, directions);
				if(currentSnake.get(currentSnake.size() - 1).equals(game.getAppleCoordinate()) && (hasPathToTail(directions, snake, true))) {
					maxSize = 5000;
					return directions;
				}
			}
			listOfDirectionsToApple = newListOfDirections(listOfDirectionsToApple, snake);
		}
		return new ArrayList<>();
	}
	
	private ArrayList<ArrayList<Direction>> newListOfDirections(ArrayList<ArrayList<Direction>> listOfDirectionsToApple, ArrayList<Coordinate> snake) {
		ArrayList<ArrayList<Direction>> tempListOfDirectionsToAppleTemp = new ArrayList<>();
		for(ArrayList<Direction> directions : listOfDirectionsToApple) {
			ArrayList<Coordinate> tempSnake = useDirectionsOnSnake(snake, directions);
			for(Direction direction : Direction.values()) {
				if(isLegaMove(tempSnake, direction)) {
					ArrayList<Direction> directionsTemp = (ArrayList<Direction>) directions.clone();
					directionsTemp.add(direction);
					tempListOfDirectionsToAppleTemp.add(directionsTemp);
				}
			}
		}
		tempListOfDirectionsToAppleTemp.removeIf(e->(e.size() == 0));
		return tempListOfDirectionsToAppleTemp;
	}
	
	public boolean hasPathToTail(ArrayList<Direction> directionsTmp, ArrayList<Coordinate> snakeTmp, boolean hasReachedApple) {
		if(this.snake.size() == WIDTH * HEIGHT - 1)
			return true;
		ArrayList<Coordinate> snake;
		if(hasReachedApple) {
			ArrayList<Direction> direction = (ArrayList<Direction>) directionsTmp.clone();
			direction.remove(directionsTmp.size() - 1);
			
			snake = (ArrayList<Coordinate>) snakeTmp.clone();
			snake = useDirectionsOnSnake(snake, direction);
			snake.add(game.getAppleCoordinate());
		}else {
			snake = snakeTmp;
		}
		final Coordinate tail = snake.get(0);
		
		ArrayList<ArrayList<Direction>> listOfDirectionsToTail = new ArrayList<>();
		ArrayList<ArrayList<Direction>> visitedNodes = new ArrayList<>();
		listOfDirectionsToTail.add(new ArrayList<>());
		
		while(listOfDirectionsToTail.size() != 0) {
			for(ArrayList<Direction> directions : listOfDirectionsToTail) {
				ArrayList<Coordinate> currentSnake = useDirectionsOnSnake(snake, directions);
				
				if(currentSnake.get(currentSnake.size() - 1).equals(tail)) {
					this.pathToTail = directions;
					return true;
				}
			}
			listOfDirectionsToTail = newListOfDirections(listOfDirectionsToTail, snake);
		}
		return false;
	}
	
	@Override
	public ArrayList<Direction> getDirectionsToApple() {
		return directionsToApple;
	}
	
	@Override
	public String toString() {
		return "BFSWithTailFollowing";
	}
}
