package model.agent;

import core.ApplicationFramework;
import model.game.Coordinates;
import model.game.Direction;
import model.game.Game;

import java.util.ArrayList;
//TODO optimise all of this.
public class AgentC extends Agent {
    private final Direction[] allDirections = {Direction.LEFT,Direction.RIGHT,Direction.UP,Direction.DOWN};
    private Game game = ApplicationFramework.getInstance().getGame();
    private ArrayList<Coordinates> snake = ApplicationFramework.getInstance().getGame().getSnake();
    private ArrayList<Direction> directionsToApple = new ArrayList<>();
    private ArrayList<Direction> directionsToTail  = new ArrayList<>();
    private ArrayList<Direction> directionsToTailReal = new ArrayList<>();

    @Override
    public void GeneratePath() {
        snake = ApplicationFramework.getInstance().getGame().getSnake();
        ArrayList<ArrayList<Direction>> listOfDirection = new ArrayList<ArrayList<Direction>>();
        listOfDirection.add(new ArrayList<>());
        this.directionsToApple = getDirectionsToApple(listOfDirection);

    }

    public ArrayList<Direction> getDirectionsToApple(ArrayList<ArrayList<Direction>> directionsToApple){

        if(directionsToApple.size()==0){
            this.directionsToApple = new ArrayList<>();
            Direction d = directionsToTail.get(0);
            this.directionsToApple.add(d);
            directionsToTail.remove(0);
            return this.directionsToApple;
        }
        for (int i = 0; i < directionsToApple.size(); i++) {
            ArrayList<Coordinates> currentSnake = useDirectionsOnSnake(snake, directionsToApple.get(i));
            if(currentSnake.get(currentSnake.size()-1).equals(game.getAppleCoordinate())){
                if(hasPathToTail(currentSnake)){
                    this.directionsToTailReal=directionsToTail;
                    return directionsToApple.get(i);
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

    Coordinates tailCoordinates;
    public boolean hasPathToTail(ArrayList<Coordinates> currentSnake){
        ArrayList<ArrayList<Direction>> listOfDirectionToTail = new ArrayList<>();
        listOfDirectionToTail.add(new ArrayList<>());
        tailCoordinates = currentSnake.get(0);
        ArrayList<Direction> DirectionToTailTemp = getDirectionsToTail(listOfDirectionToTail, currentSnake);
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


    public void setDirectionsToTailReal(ArrayList<Direction> directionsToTailReal) {
        this.directionsToTailReal = directionsToTailReal;
    }
}
