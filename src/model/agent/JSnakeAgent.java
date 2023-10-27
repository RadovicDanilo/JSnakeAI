package model.agent;

import core.ApplicationFramework;
import model.game.Coordinates;
import model.game.Direction;
import model.game.Game;

import java.util.ArrayList;
import java.util.Comparator;

public class JSnakeAgent extends Agent {
    //better variant of AgentA
    private Game game = ApplicationFramework.getInstance().getGame();
    private ArrayList<ArrayList<Direction>> listOfPaths = new ArrayList<>();




    @Override
    public void GeneratePath() {

        int shortestPath = Math.abs(game.getHeadCoordinate().getX()-game.getAppleCoordinate().getX()) +Math.abs(game.getHeadCoordinate().getY()-game.getAppleCoordinate().getY());
        Coordinates headCoordinates = game.getHeadCoordinate();
        Coordinates appleCoordinates = game.getAppleCoordinate();

        getListOfPaths(game.getSnake(), new ArrayList<>());
        listOfPaths.sort((a1, a2) -> {
            return -(a2.size() - a1.size());
        });
        for(ArrayList<Direction> directionArrayList : listOfPaths){
            ArrayList<Coordinates> nextSnake = useDirectionsOnSnake(game.getSnake(), directionArrayList);
            if(hasHamiltonianCycle(nextSnake)){
                super.setDirectionsToApple(directionArrayList);
            }
        }
    }

    private ArrayList<Coordinates> useDirectionsOnSnake(ArrayList<Coordinates> snake, ArrayList<Direction> directionArrayList) {
        ArrayList<Coordinates> nextSnake = snake;
        for(Direction direction: directionArrayList){
            nextSnake = snakeMove(snake,direction);
        }
        return nextSnake;
    }
    private ArrayList<Coordinates> snakeMove(ArrayList<Coordinates> snake,Direction direction){
        snake.remove(0);
        switch (direction){
            case DOWN -> snake.add(new Coordinates(snake.get(snake.size()-1).getX()+1,snake.get(snake.size()-1).getY()));
            case UP -> snake.add(new Coordinates(snake.get(snake.size()-1).getX()-1,snake.get(snake.size()-1).getY()));
            case LEFT -> snake.add(new Coordinates(snake.get(snake.size()-1).getX(),snake.get(snake.size()-1).getY()-1));
            case RIGHT -> snake.add(new Coordinates(snake.get(snake.size()-1).getX(),snake.get(snake.size()-1).getY()+1));
        }
        return snake;
    }
    public void getListOfPaths(ArrayList<Coordinates> snake,ArrayList<Direction> directions){
        if(isLegaMove(snake,Direction.DOWN)){
            //update dir
            directions.add(Direction.DOWN);
            //update snake
            snake.remove(0);
            snake.add(new Coordinates(snake.get(snake.size()-1).getX()+1,snake.get(snake.size()-1).getY()));
            //check if its the apple
            if(snake.get(snake.size()-1)==game.getAppleCoordinate()){
                listOfPaths.add(directions);
            }else{
                getListOfPaths(snake,directions);
            }

        }
        if(isLegaMove(snake,Direction.UP)){
            directions.add(Direction.UP);
            //update snake
            snake.remove(0);
            snake.add(new Coordinates(snake.get(snake.size()-1).getX()-1,snake.get(snake.size()-1).getY()));
            //check if its the apple
            if(snake.get(snake.size()-1)==game.getAppleCoordinate()){
                listOfPaths.add(directions);
            }else{
                getListOfPaths(snake,directions);
            }
        }
        if(isLegaMove(snake,Direction.LEFT)){
            directions.add(Direction.LEFT);
            //update snake
            snake.remove(0);
            snake.add(new Coordinates(snake.get(snake.size()-1).getX(),snake.get(snake.size()-1).getY()-1));
            //check if its the apple
            if(snake.get(snake.size()-1)==game.getAppleCoordinate()){
                listOfPaths.add(directions);
            }else{
                getListOfPaths(snake,directions);
            }
        }
        if(isLegaMove(snake,Direction.RIGHT)){
            directions.add(Direction.RIGHT);
            //update snake
            snake.remove(0);
            snake.add(new Coordinates(snake.get(snake.size()-1).getX(),snake.get(snake.size()-1).getY()+1));
            //check if its the apple
            if(snake.get(snake.size()-1)==game.getAppleCoordinate()){
                listOfPaths.add(directions);
            }else{
                getListOfPaths(snake,directions);
            }
        }
    }
    public boolean hasHamiltonianCycle(ArrayList<Coordinates> snake){
        //TODO THIS IS THE HARD PART OMG

        return false;
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
        return !(snake.contains(head) && head!=snake.get(0))|| head.getX()< 0 || head.getY()< 0 || head.getX() == super.getHeight() || head.getY() == super.getWidth();
    }
}
