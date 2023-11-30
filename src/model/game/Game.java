package model.game;

import core.ApplicationFramework;
import view.MainView;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

public class Game {
	public final int HEIGHT = ApplicationFramework.getInstance().HEIGHT;
	public final int WIDTH = ApplicationFramework.getInstance().WIDTH;
	private int moves = 0;
	private final ArrayList<Coordinate> board;
	private final ArrayList<Coordinate> snake;
	private Coordinate appleCoordinate;
	private Coordinate headCoordinate;
	private boolean isRunning = true;
	
	public Game() {
		board = new ArrayList<>();
		snake = new ArrayList<>();
		snake.add(new Coordinate(0, 0));
		snake.add(new Coordinate(0, 1));
		snake.add(new Coordinate(0, 2));
		headCoordinate = new Coordinate(0, 2);
		
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				board.add(new Coordinate(i, j));
			}
		}
		GenerateApple();
	}
	
	public void Run() {
		while(isRunning) {
			ApplicationFramework.getInstance().getAgent().GeneratePath();
			if(ApplicationFramework.getInstance().getAgent().getDirectionsToApple().size() == 0) {
				EndGame("GAME_LOST");
				break;
			}
			for(Direction d : ApplicationFramework.getInstance().getAgent().getDirectionsToApple()) {
				System.out.println("SNAKE MOVED: " + d);
				move(d);
				if(!isRunning)
					break;
				ApplicationFramework.getInstance().getAgent().Wait();
			}
		}
	}
	
	public void GenerateApple() {
		if(snake.size() == WIDTH * HEIGHT) {
			appleCoordinate = new Coordinate(HEIGHT, WIDTH);
			MainView.getInstance().update();
			EndGame("GAME_WON");
			return;
		}
		while(true) {
			int x = RandomGenerator.getDefault().nextInt(WIDTH);
			int y = RandomGenerator.getDefault().nextInt(HEIGHT);
			if(!snake.contains(new Coordinate(x, y))) {
				appleCoordinate = new Coordinate(x, y);
				return;
			}
		}
	}
	
	private void EndGame(String notifications) {
		System.out.println(notifications);
		isRunning = false;
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("src/log.txt", true));
			pw.println(moves + "," + snake.size() + "," + (WIDTH * HEIGHT) + "," + ApplicationFramework.getInstance().getAgent());
			pw.close();
		}catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void move(Direction direction) {
		Coordinate head;
		headCoordinate = snake.get(snake.size() - 1);
		head = headCoordinate.move(direction);
		if((snake.contains(head) && head != snake.get(0)) || !board.contains(head)) {
			isRunning = false;
			EndGame("GAME_LOST");
			return;
		}
		moves++;
		if(head.equals(appleCoordinate)) {
			snake.add(head);
			headCoordinate = head;
			GenerateApple();
			MainView.getInstance().update();
		}else {
			snake.remove(0);
			snake.add(head);
			headCoordinate = head;
			MainView.getInstance().update();
		}
	}
	
	public ArrayList<Coordinate> getSnake() {
		return snake;
	}
	
	public ArrayList<Coordinate> getBoard() {
		return board;
	}
	
	public Coordinate getAppleCoordinate() {
		return appleCoordinate;
	}
	
	public Coordinate getHeadCoordinate() {
		return headCoordinate;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
