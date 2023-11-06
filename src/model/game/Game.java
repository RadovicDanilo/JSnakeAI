package model.game;

import core.ApplicationFramework;
import model.observer.IPublisher;
import model.observer.ISubscriber;
import model.observer.Notifications;

import java.util.ArrayList;
import java.util.random.RandomGenerator;

public class Game implements IPublisher {
    private final int height = ApplicationFramework.getInstance().height;
    private final int width = ApplicationFramework.getInstance().width;
    private int[][] board;//TODO you can remove this and keep the sanake + apple + w + h because they are enough
    private ArrayList<Coordinates> snake;
    private ArrayList<ISubscriber> subscribers = new ArrayList<>();
    private Coordinates appleCoordinate;
    private Coordinates headCoordinate = new Coordinates(2,0);
    private boolean isRunning = true;

    public Game() {
        board = new int[height][width];
        snake = new ArrayList<>();
        snake.add(new Coordinates(0,0));
        snake.add(new Coordinates(1,0));
        snake.add(new Coordinates(2,0));
//        snake.add(new Coordinates(3,0));
//        snake.add(new Coordinates(4,0));
//        snake.add(new Coordinates(5,0));


        for (int i = 0; i < height; i++) { //TODO. Is this necessary?
            for (int j = 0; j < width; j++) {
                if(snake.contains(new Coordinates(i,j))){
                    this.board[i][j] = 1;
                }else{
                    this.board[i][j] = 0;
                }
            }
        }
        GenerateApple();
    }
    public void Run(){
        while (isRunning) {
            ApplicationFramework.getInstance().getAgent().GeneratePath();
            if(ApplicationFramework.getInstance().getAgent().getDirectionsToApple().size()==0){
                isRunning=false;

                EndGame(Notifications.GAME_LOST);
                break;
            }
            for(Direction d : ApplicationFramework.getInstance().getAgent().getDirectionsToApple()){
                move(d);
                ApplicationFramework.getInstance().getAgent().Wait();
            }
        }

    }
    public void GenerateApple(){
        boolean canGenerate = false;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(board[i][j]==0){
                    canGenerate = true;
                    break;
                }
            }
        }
        if(!canGenerate){
            EndGame(Notifications.GAME_WON);
            return;
        }
        while(true){
            int x = RandomGenerator.getDefault().nextInt(height);
            int y = RandomGenerator.getDefault().nextInt(width);
            if(!snake.contains(new Coordinates(x,y))){
                appleCoordinate = new Coordinates(x,y);
                board[x][y]=2;
                break;
            }
        }
    }

    private void EndGame(Notifications notifications) {
        System.out.println(notifications);
        isRunning=false;
        notifySubscribers(notifications);
    }


    public void move(Direction direction) {
        Coordinates head = new Coordinates(0,0);

        switch (direction) {
            case LEFT -> head = new Coordinates(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY() - 1);
            case RIGHT -> head = new Coordinates(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY() + 1);
            case UP -> head = new Coordinates(snake.get(snake.size() - 1).getX() - 1, snake.get(snake.size() - 1).getY());
            case DOWN -> head = new Coordinates(snake.get(snake.size()-1).getX() + 1, snake.get(snake.size()-1).getY());
        }
        if((snake.contains(head) && !head.equals(snake.get(0))) || head.getX()< 0 || head.getY()< 0 || head.getX() == height || head.getY() == width ){
            System.out.println("SNEKS FIRTS HEAD"+headCoordinate.getX()+" "+headCoordinate.getY());
            System.out.println(direction);
            System.out.println("SNEK MOVED HEAD"+head.getX()+" "+head.getY());
            System.out.println("SNEK HIT HEAD");
            System.out.println("SNEKS REAL tail "+snake.get(0).getX()+" "+snake.get(0).getY());
            EndGame(Notifications.GAME_LOST);
            return;
        }

        if(board[head.getX()][head.getY()]==0){
            board[head.getX()][head.getY()] = 1;
            snake.add(head);
            board[snake.get(0).getX()][snake.get(0).getY()] = 0;
            snake.remove(0);
            headCoordinate = head;
            notifySubscribers(Notifications.UPDATE_UI);
        }
        if(board[head.getX()][head.getY()]==2){
            board[head.getX()][head.getY()] = 1;
            snake.add(head);
            GenerateApple();
            headCoordinate = head;
            notifySubscribers(Notifications.UPDATE_UI);
        }

    }
    @Override
    public void addSubscriber(ISubscriber sub) {
        if(sub==null)
            return;
        if(subscribers.contains(sub))
            return;
        subscribers.add(sub);
    }

    @Override
    public void removeSubscriber(ISubscriber sub) {
        subscribers.remove(sub);
    }

    @Override
    public void notifySubscribers(Object notification) {
        for(ISubscriber sub: subscribers){
            sub.update(notification);
        }
    }
    public int[][] getBoard() {
        return this.board;
    }
    public ArrayList<Coordinates> getSnake() {
        return snake;
    }
    public Coordinates getAppleCoordinate() {
        return appleCoordinate;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<ISubscriber> getSubscribers() {
        return subscribers;
    }

    public Coordinates getHeadCoordinate() {
        return headCoordinate;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
