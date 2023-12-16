package model.agent;

import core.ApplicationFramework;
import model.game.Coordinate;
import model.game.Direction;
import model.game.Game;

import java.util.ArrayList;

public class PertubatedHamiltonianCycle extends Agent {
	private final Game game = ApplicationFramework.getInstance().getGame();
	private ArrayList<Coordinate> snake;
	private ArrayList<Direction> directionsToApple;
	private ArrayList<Coordinate> initialHamiltonianPathAsCoordinates;
	
	public PertubatedHamiltonianCycle() {
		snake = game.getSnake();
		getInitialHamiltonianPathAsCoordinateArray();
	}
	
	public void getInitialHamiltonianPathAsCoordinateArray() {
		SingleHamiltonianCycle s = new SingleHamiltonianCycle();
		ArrayList<Direction> initialHamiltonianPathAsDirections = s.getDirectionsToApple();
		ArrayList<Coordinate> initialHamiltonianPathAsCoordinates = new ArrayList<>();
		initialHamiltonianPathAsCoordinates.add(snake.get(snake.size() - 1));
		for(Direction d : initialHamiltonianPathAsDirections) {
			initialHamiltonianPathAsCoordinates.add(initialHamiltonianPathAsCoordinates.get(initialHamiltonianPathAsCoordinates.size() - 1).move(d));
		}
		initialHamiltonianPathAsCoordinates.remove(initialHamiltonianPathAsCoordinates.size() - 1);
		ArrayList<Coordinate> val = new ArrayList<>();
		val.add(initialHamiltonianPathAsCoordinates.get(initialHamiltonianPathAsCoordinates.size() - 2));
		val.add(initialHamiltonianPathAsCoordinates.get(initialHamiltonianPathAsCoordinates.size() - 1));
		for(int i = 0; i < initialHamiltonianPathAsCoordinates.size() - 2; i++) {
			val.add(initialHamiltonianPathAsCoordinates.get(i));
		}
		this.initialHamiltonianPathAsCoordinates = val;
	}
	
	@Override
	public void GeneratePath() {
		snake = game.getSnake();
		System.out.println("APPLE " + initialHamiltonianPathAsCoordinates.indexOf(game.getAppleCoordinate()));
		System.out.println("HEAD " + initialHamiltonianPathAsCoordinates.indexOf(game.getHeadCoordinate()));
		System.out.println("TAIL " + initialHamiltonianPathAsCoordinates.indexOf(game.getSnake().get(0)));
		System.out.println();
		
		this.directionsToApple = findPathBFS();
	}
	
	public ArrayList<Direction> findPathBFS() {
		ArrayList<ArrayList<Direction>> listOfDirectionsToApple = new ArrayList<>();
		listOfDirectionsToApple.add(new ArrayList<>());
		
		while(true) {
			for(ArrayList<Direction> directions : listOfDirectionsToApple) {
				ArrayList<Coordinate> currentSnake = useDirectionsOnSnake(snake, directions);
				if(currentSnake.get(currentSnake.size() - 1).equals(game.getAppleCoordinate())) {
					return directions;
				}
			}
			ArrayList<ArrayList<Direction>> tempNewListOfDirection = newListOfDirections(listOfDirectionsToApple, snake);
			if(tempNewListOfDirection.size() == 0) {
				return listOfDirectionsToApple.get(0);
			}
			listOfDirectionsToApple = tempNewListOfDirection;
		}
	}
	
	private ArrayList<ArrayList<Direction>> newListOfDirections(ArrayList<ArrayList<Direction>> listOfDirectionsToApple, ArrayList<Coordinate> snake) {
		ArrayList<ArrayList<Direction>> tempListOfDirectionsToAppleTemp = new ArrayList<>();
		for(ArrayList<Direction> directions : listOfDirectionsToApple) {
			ArrayList<Coordinate> tempSnake = useDirectionsOnSnake(snake, directions);
			for(Direction direction : Direction.values()) {
				if(!isLegaMove(tempSnake, direction)) {
					continue;
				}
				Coordinate lastHead = tempSnake.get(tempSnake.size() - 1);
				Coordinate nextHead = tempSnake.get(tempSnake.size() - 1).move(direction);
				Coordinate nextTail = tempSnake.get(1);
				int lastHeadPos = initialHamiltonianPathAsCoordinates.indexOf(lastHead);
				int nextHeadPos = initialHamiltonianPathAsCoordinates.indexOf(nextHead);
				int nextTailPos = initialHamiltonianPathAsCoordinates.indexOf(nextTail);
				
				if(snake.size() < getHEIGHT() * getWIDTH() / 2 && ((nextTailPos < lastHeadPos && (nextHeadPos < nextTailPos ||
					nextHeadPos > lastHeadPos)) || (nextHeadPos > lastHeadPos && nextHeadPos < nextTailPos))) {
					ArrayList<Direction> directionsTemp = (ArrayList<Direction>) directions.clone();
					directionsTemp.add(direction);
					tempListOfDirectionsToAppleTemp.add(directionsTemp);
				}else if(snake.size() >= getHEIGHT() * getWIDTH() / 2 &&
					(getHEIGHT() * getWIDTH() + nextHeadPos - 1) % getHEIGHT() * getWIDTH() == (getHEIGHT() * getWIDTH() + lastHeadPos) % getHEIGHT() * getWIDTH()) {
					ArrayList<Direction> directionsTemp = (ArrayList<Direction>) directions.clone();
					directionsTemp.add(direction);
					tempListOfDirectionsToAppleTemp.add(directionsTemp);
				}
			}
		}
		tempListOfDirectionsToAppleTemp.removeIf(e->(e.size() == 0));
		return tempListOfDirectionsToAppleTemp;
	}
	
	
	@Override
	public ArrayList<Direction> getDirectionsToApple() {
		return directionsToApple;
	}
	
	public void setDirectionsToApple(ArrayList<Direction> directionsToApple) {
		this.directionsToApple = directionsToApple;
	}
	
	public Game getGame() {
		return game;
	}
	
	public ArrayList<Coordinate> getSnake() {
		return snake;
	}
	
	public void setSnake(ArrayList<Coordinate> snake) {
		this.snake = snake;
	}
	
	public ArrayList<Coordinate> getInitialHamiltonianPathAsCoordinates() {
		return initialHamiltonianPathAsCoordinates;
	}
	
	public void setInitialHamiltonianPathAsCoordinates(ArrayList<Coordinate> initialHamiltonianPathAsCoordinates) {
		this.initialHamiltonianPathAsCoordinates = initialHamiltonianPathAsCoordinates;
	}
	
	@Override
	public String toString() {
		return "PertubatedHamiltonianCycle";
	}
}

