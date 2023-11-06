package model.agent;

import core.ApplicationFramework;
import model.game.Coordinates;
import model.game.Direction;
import model.game.Game;

import java.util.ArrayList;
import java.util.Collection;

public class AgentD extends Agent {
    private final Direction[] allDirections = {Direction.LEFT,Direction.RIGHT,Direction.UP,Direction.DOWN};
    private Game game = ApplicationFramework.getInstance().getGame();
    private ArrayList<Coordinates> snake = ApplicationFramework.getInstance().getGame().getSnake();
    private ArrayList<Direction> directionsToApple = new ArrayList<>();
    private ArrayList<Direction> directionsToTail  = new ArrayList<>();

    @Override
    public void GeneratePath() {
        snake = ApplicationFramework.getInstance().getGame().getSnake();
        ArrayList<ArrayList<Direction>> listOfDirection = new ArrayList<>();
        listOfDirection.add(new ArrayList<>());
        directionsToApple = getDirectionsToApple(listOfDirection);
    }

    public ArrayList<Direction> getDirectionsToApple(ArrayList<ArrayList<Direction>> listOfDirectionsToApple){
        System.out.println("DIR TO APPLE "+listOfDirectionsToApple.size());
        if(listOfDirectionsToApple.size()==0){
            directionsToApple = new ArrayList<>();
            ArrayList<ArrayList<Direction>> directionsToTailTempo = new ArrayList<>();
            directionsToApple.add(directionsToTail.get(0));
            return directionsToApple;
        }
        for (int i = 0; i < listOfDirectionsToApple.size(); i++) {
            ArrayList<Coordinates> currentSnake = useDirectionsOnSnake(snake, listOfDirectionsToApple.get(i));
            if(currentSnake.get(currentSnake.size()-1).equals(game.getAppleCoordinate())&&hasPathToTail(currentSnake)){
                this.directionsToTail.addAll(getDirectionsFromTailToHead(currentSnake));
                return listOfDirectionsToApple.get(i);
            }
        }
        ArrayList<ArrayList<Direction>> directionsToAppleTemp = new ArrayList<>();
        for(int i = 0; i < listOfDirectionsToApple.size(); i++) {
            ArrayList<Coordinates> tempSnake = useDirectionsOnSnake(game.getSnake(), listOfDirectionsToApple.get(i));
            for(Direction direction : allDirections){
                if(isLegaMove(tempSnake, direction)){
                    ArrayList<Direction> directionsTemp = (ArrayList<Direction>) listOfDirectionsToApple.get(i).clone();
                    directionsTemp.add(direction);
                    directionsToAppleTemp.add(directionsTemp);
                }
            }
        }
        listOfDirectionsToApple.removeIf(e ->(true));
        directionsToAppleTemp.removeIf(e ->(e.size()==0));
        return getDirectionsToApple(directionsToAppleTemp);
    }

    private Collection<Direction> getDirectionsFromTailToHead(ArrayList<Coordinates> currentSnake) {
        ArrayList<Direction> tempDir = new ArrayList<>();
        for (int i = 0; i < currentSnake.size()-1; i++) {
            if (currentSnake.get(i).getX()+1==currentSnake.get(i+1).getX()&&
                    currentSnake.get(i).getY()==currentSnake.get(i+1).getY()){
                tempDir.add(Direction.DOWN);
            }
            if (currentSnake.get(i).getX()-1==currentSnake.get(i+1).getX()&&
                    currentSnake.get(i).getY()==currentSnake.get(i+1).getY()){
                tempDir.add(Direction.UP);
            }
            if (currentSnake.get(i).getX()==currentSnake.get(i+1).getX()&&
                    currentSnake.get(i).getY()+1==currentSnake.get(i+1).getY()){
                tempDir.add(Direction.RIGHT);
            }
            if (currentSnake.get(i).getX()==currentSnake.get(i+1).getX()&&
                    currentSnake.get(i).getY()-1==currentSnake.get(i+1).getY()){
                tempDir.add(Direction.LEFT);
            }
        }
        return tempDir;
    }

    Coordinates tailCoordinates;
    public boolean hasPathToTail(ArrayList<Coordinates> currentSnake){
        ArrayList<ArrayList<Direction>> listOfDirectionToTail = new ArrayList<>();
        listOfDirectionToTail.add(new ArrayList<>());
        tailCoordinates = currentSnake.get(0);
        ArrayList<Coordinates> tempSnake = (ArrayList<Coordinates>) currentSnake.clone();
        tempSnake.add(game.getAppleCoordinate());
        ArrayList<Direction> DirectionToTailTemp = getDirectionsToTail(listOfDirectionToTail, tempSnake);
        if(DirectionToTailTemp.size()==0){
            return false;
        }else {
            this.directionsToTail = DirectionToTailTemp;
            return true;
        }
    }
    private ArrayList<Direction> getDirectionsToTail(ArrayList<ArrayList<Direction>> listOfDirectionToTail, ArrayList<Coordinates> snake) {

        if(listOfDirectionToTail.size()==0){
            return new ArrayList<>();
        }
        for (int i = 0; i < listOfDirectionToTail.size(); i++) {
            ArrayList<Coordinates> currentSnake = useDirectionsOnSnake(snake, listOfDirectionToTail.get(i));
            if(currentSnake.get(currentSnake.size()-1).equals(tailCoordinates)){
                return listOfDirectionToTail.get(i);
            }
        }
        ArrayList<ArrayList<Direction>> directionsToAppleTemp = new ArrayList<>();
        for(int i = 0; i < listOfDirectionToTail.size(); i++) {
            ArrayList<Coordinates> tempSnake = useDirectionsOnSnake(snake, listOfDirectionToTail.get(i));
            for(Direction direction : allDirections){
                if(isLegaMove(tempSnake, direction)){
                    ArrayList<Direction> directionsTemp = (ArrayList<Direction>) listOfDirectionToTail.get(i).clone();
                    directionsTemp.add(direction);
                    directionsToAppleTemp.add(directionsTemp);
                }
            }
        }

        directionsToAppleTemp.removeIf(e ->(e.size()==0));
        return getDirectionsToTail(directionsToAppleTemp,snake);
    }

    @Override
    public ArrayList<Direction> getDirectionsToApple() {
        return directionsToApple;
    }


}
