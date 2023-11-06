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
    public void Wait(){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean isLegaMove(ArrayList<Coordinates> snakeTemp, Direction direction){
        ArrayList<Coordinates> snake = (ArrayList<Coordinates>) snakeTemp.clone();
        Coordinates head = null;
        switch (direction) {
            case LEFT -> head = new Coordinates(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY() - 1);
            case RIGHT -> head = new Coordinates(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY() + 1);
            case UP -> head = new Coordinates(snake.get(snake.size() - 1).getX() - 1, snake.get(snake.size() - 1).getY());
            case DOWN -> head = new Coordinates(snake.get(snake.size()-1).getX() + 1, snake.get(snake.size()-1).getY());
        }
        return !((snake.contains(head) && head!=snake.get(0)) || head.getX()< 0 || head.getY()< 0 || head.getX() == getHeight() || head.getY() == getWidth());
    }
    public ArrayList<Coordinates> useDirectionsOnSnake(ArrayList<Coordinates> snakeTemp, ArrayList<Direction> directionArrayList) {
        ArrayList<Coordinates> snake = (ArrayList<Coordinates>) snakeTemp.clone();
        for(Direction direction: directionArrayList){
            snake.remove(0);
            switch (direction){
                case DOWN -> snake.add(new Coordinates(snake.get(snake.size()-1).getX()+1,snake.get(snake.size()-1).getY()));
                case UP -> snake.add(new Coordinates(snake.get(snake.size()-1).getX()-1,snake.get(snake.size()-1).getY()));
                case LEFT -> snake.add(new Coordinates(snake.get(snake.size()-1).getX(),snake.get(snake.size()-1).getY()-1));
                case RIGHT -> snake.add(new Coordinates(snake.get(snake.size()-1).getX(),snake.get(snake.size()-1).getY()+1));
            }
        }
        return snake;
    }

    public ArrayList<Direction> getDirectionsToApple() {
        return directionsToApple;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
