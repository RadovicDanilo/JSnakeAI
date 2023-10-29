package model.agent;

import core.ApplicationFramework;
import model.game.Coordinates;
import model.game.Direction;
import model.game.Game;

import java.util.ArrayList;

public class AgentB extends Agent {
    private final Direction[] allDirections = {Direction.LEFT,Direction.RIGHT,Direction.UP,Direction.DOWN};
    private Game game = ApplicationFramework.getInstance().getGame();
    private ArrayList<Coordinates> snake = ApplicationFramework.getInstance().getGame().getSnake();
    private ArrayList<Direction> directionsToApple = new ArrayList<>();

    @Override
    public void GeneratePath() {
        snake = ApplicationFramework.getInstance().getGame().getSnake();
        ArrayList<ArrayList<Direction>> listOfDirection = new ArrayList<ArrayList<Direction>>();
        listOfDirection.add(new ArrayList<>());
        this.directionsToApple = getDirectionsToApple(listOfDirection);

    }

    public ArrayList<Direction> getDirectionsToApple(ArrayList<ArrayList<Direction>> directionsToApple){

        System.out.println("[INFO] ARRAY SIZE = " + directionsToApple.size());

        for (int i = 0; i < directionsToApple.size(); i++) {
            ArrayList<Coordinates> currentSnake = useDirectionsOnSnake(snake, directionsToApple.get(i));
            if(currentSnake.get(currentSnake.size()-1).equals(game.getAppleCoordinate()) && hasHamiltonianCycle(currentSnake)){
                return directionsToApple.get(i);
            }
        }
        ArrayList<ArrayList<Direction>> directionsToAppleTemp = new ArrayList<>();
        for(int i = 0; i < directionsToApple.size(); i++) {
            ArrayList<Coordinates> tempSnake = useDirectionsOnSnake(game.getSnake(), directionsToApple.get(i));
            for(Direction direction : allDirections){
                if(isLegaMove(tempSnake, direction)){
                    ArrayList<Direction> directionsTemp = (ArrayList<Direction>) directionsToApple.get(i).clone();
                    directionsTemp.add(direction);
                    directionsToAppleTemp.add(directionsTemp);
                }
            }
        }
        directionsToApple.removeIf(e ->(true));
        directionsToAppleTemp.removeIf(e ->(e.size()==0));
        return getDirectionsToApple(directionsToAppleTemp);
    }

    public boolean hasHamiltonianCycle(ArrayList<Coordinates> snake){
        return true;
    }

    //TODO combine the two bellow they dont need to be seperated
    private ArrayList<Coordinates> useDirectionsOnSnake(ArrayList<Coordinates> snake, ArrayList<Direction> directionArrayList) {
        ArrayList<Coordinates> nextSnake = (ArrayList<Coordinates>) snake.clone();
        for(Direction direction: directionArrayList){
            nextSnake = move(nextSnake,direction);
        }
        return nextSnake;
    }
    private ArrayList<Coordinates> move(ArrayList<Coordinates> snake, Direction direction){
        snake.remove(0);
        switch (direction){
            case DOWN -> snake.add(new Coordinates(snake.get(snake.size()-1).getX()+1,snake.get(snake.size()-1).getY()));
            case UP -> snake.add(new Coordinates(snake.get(snake.size()-1).getX()-1,snake.get(snake.size()-1).getY()));
            case LEFT -> snake.add(new Coordinates(snake.get(snake.size()-1).getX(),snake.get(snake.size()-1).getY()-1));
            case RIGHT -> snake.add(new Coordinates(snake.get(snake.size()-1).getX(),snake.get(snake.size()-1).getY()+1));
        }
        return snake;
    }

    public boolean isLegaMove(ArrayList<Coordinates> snake, Direction direction){
        Coordinates head = null;
        switch (direction) {
            case LEFT ->
                    head = new Coordinates(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY() - 1);
            case RIGHT ->
                    head = new Coordinates(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY() + 1);
            case UP ->
                    head = new Coordinates(snake.get(snake.size() - 1).getX() - 1, snake.get(snake.size() - 1).getY());
            case DOWN ->
                    head = new Coordinates(snake.get(snake.size()-1).getX() + 1, snake.get(snake.size()-1).getY());
        }
        return !((snake.contains(head) && head!=snake.get(0)) || head.getX()< 0 || head.getY()< 0 || head.getX() == super.getHeight() || head.getY() == super.getWidth());
    }

    @Override
    public ArrayList<Direction> getDirectionsToApple() {
        return directionsToApple;
    }

    @Override
    public void setDirectionsToApple(ArrayList<Direction> directionsToApple) {
        this.directionsToApple = directionsToApple;
    }
}
