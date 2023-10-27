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
        ApplicationFramework.getInstance().getAgent().GeneratePath();
        while (isRunning) {
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
        //generat a path
    }

    private void EndGame(Notifications notifications) {
        isRunning=false;
        notifySubscribers(Notifications.GAME_LOST);
    }


    public void move(Direction direction) {
        Coordinates head = new Coordinates(0,0);
        System.out.println("[INFO] snake size"+snake.size());
        System.out.println("[INFO] sanke head at ("+snake.get(snake.size()-1).getX()+","+snake.get(snake.size()-1).getY()+")");
        System.out.println("[INFO] Snake moves " + direction);

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
        if((snake.contains(head) && head!=snake.get(0))|| head.getX()< 0 || head.getY()< 0 || head.getX() == height || head.getY() == width ){
            EndGame(Notifications.GAME_LOST);
            return;
        }
        if(board[head.getX()][head.getY()]==0){
            board[head.getX()][head.getY()] = 1;
            snake.add(head);
            board[snake.get(0).getX()][snake.get(0).getY()] = 0;
            snake.remove(0);
            notifySubscribers(Notifications.UPDATE_UI);
        }
        if(board[head.getX()][head.getY()]==2){
            board[head.getX()][head.getY()] = 1;
            snake.add(head);
            GenerateApple();
            notifySubscribers(Notifications.UPDATE_UI);
        }
        headCoordinate = head;
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
