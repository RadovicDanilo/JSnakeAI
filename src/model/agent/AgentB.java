package model.agent;

import core.ApplicationFramework;
import model.game.Coordinates;
import model.game.Direction;
import model.game.Game;

import java.util.ArrayList;
//TODO optimise all of this.
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
        if(directionsToApple.size()==0){
            return new ArrayList<>();
        }
        for (ArrayList<Direction> directions : directionsToApple) {
            ArrayList<Coordinates> currentSnake = useDirectionsOnSnake(snake, directions);
            if (currentSnake.get(currentSnake.size()-1).equals(game.getAppleCoordinate())) {
                if (hasHamiltonianCycle(currentSnake)) {
                    return directions;
                } else {
                    directionsToApple.remove(directions);
                }
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


    ArrayList<Coordinates> emptyBlocks = new ArrayList<>();
    ArrayList<Coordinates> blockAdjacentToHead = new ArrayList<>();
    public boolean hasHamiltonianCycle(ArrayList<Coordinates> snake){

        ArrayList<ArrayList<Coordinates>> blockVisited = new ArrayList<>();
        blockVisited.add(new ArrayList<>());
        blockVisited.get(0).add(snake.get(snake.size()-1));

        for (int i = 0; i < game.getHeight(); i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                emptyBlocks.add(new Coordinates(i,j));
            }
        }
        emptyBlocks.removeIf(snake::contains);
        emptyBlocks.add(snake.get(snake.size()-1));

        blockAdjacentToHead.add(new Coordinates(snake.get(snake.size()-1).getX()-1,snake.get(snake.size()-1).getY()));
        blockAdjacentToHead.add(new Coordinates(snake.get(snake.size()-1).getX()+1,snake.get(snake.size()-1).getY()));
        blockAdjacentToHead.add(new Coordinates(snake.get(snake.size()-1).getX(),snake.get(snake.size()-1).getY()-1));
        blockAdjacentToHead.removeIf(cords -> cords.getX() < 0 || cords.getY() < 0 || cords.getX() == getHeight() || cords.getY() == getWidth());
        return makeHamiltonianCycle(blockVisited,snake);

    }
    public boolean makeHamiltonianCycle( ArrayList<ArrayList<Coordinates>> blockVisited,ArrayList<Coordinates> snake){
        System.out.println("[INFO] NUMBER OF HAMILTONIAN PATH BEING PROCESSED: " + blockVisited.size());
        if(snake.size()>0){
            emptyBlocks.add(snake.get(0));
            snake.remove(0);
        }
        if(blockVisited.size()==0){
            return false;
        }
        if(blockVisited.size()==getWidth()*getHeight()-10){
            return true;
        }
        ArrayList<ArrayList<Coordinates>> nextBlocksVisited = new ArrayList<>();
        for(ArrayList<Coordinates> hamiltons : blockVisited){
            ArrayList<Coordinates> nextFinalBlocks = new ArrayList<>();
            nextFinalBlocks.add(new Coordinates(hamiltons.get(hamiltons.size()-1).getX()-1,hamiltons.get(hamiltons.size()-1).getY()));
            nextFinalBlocks.add(new Coordinates(hamiltons.get(hamiltons.size()-1).getX()+1,hamiltons.get(hamiltons.size()-1).getY()));
            nextFinalBlocks.add(new Coordinates(hamiltons.get(hamiltons.size()-1).getX(),hamiltons.get(hamiltons.size()-1).getY()-1));
            nextFinalBlocks.add(new Coordinates(hamiltons.get(hamiltons.size()-1).getX(),hamiltons.get(hamiltons.size()-1).getY()+1));
            for(Coordinates c : nextFinalBlocks){
                ArrayList<Coordinates> tempHamiltons = (ArrayList<Coordinates>) hamiltons.clone();
                tempHamiltons.add(c);
                if(emptyBlocks.contains(c)&&!(tempHamiltons.contains(blockAdjacentToHead))){
                    nextBlocksVisited.add(tempHamiltons);
                }
            }
        }
        return makeHamiltonianCycle(nextBlocksVisited,snake);
    }

    @Override
    public ArrayList<Direction> getDirectionsToApple() {
        return directionsToApple;
    }


}
