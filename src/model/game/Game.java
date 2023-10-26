package model.game;

import core.ApplicationFramework;

import java.util.ArrayList;
import java.util.random.RandomGenerator;

public class Game {
    private final int height = ApplicationFramework.getInstance().height;
    private final int width = ApplicationFramework.getInstance().width;
    private int[][] board;
    private ArrayList<Coordinates> snake;

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
    public void move(Direction direction) {
        snake.remove(0);
        Coordinates head = new Coordinates(0,0);
        switch (direction) {
            case LEFT ->
                    head = new Coordinates(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY() - 1);
            case RIGHT ->
                    head = new Coordinates(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY() + 1);
            case UP ->
                    head = new Coordinates(snake.get(snake.size() - 1).getX() - 1, snake.get(snake.size() - 1).getY());
            case DOWN ->
                    head = new Coordinates(snake.get(snake.size() - 1).getX() + 1, snake.get(snake.size() - 1).getY());
        }
        if(snake.contains(head)||
                head.getX()< 0 ||
                head.getY()< 0 ||
                head.getX() > height -1||
                head.getY() > width -1){
            endGame();
        }
        if(board[head.getX()][head.getY()]==0){
            board[head.getX()][head.getY()] = 1;
            snake.add(head);
            board[snake.get(0).getX()][snake.get(0).getY()] = 0;
            snake.remove(0);
            GenerateApple();
        }
        if(board[head.getX()][head.getY()]==1){
            endGame();
        }
        if(board[head.getX()][head.getY()]==2){
            board[head.getX()][head.getY()] = 1;
            snake.add(head);
            GenerateApple();
        }
    }

    private void endGame() {

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
            return;
        }
        while(true){
            int x = RandomGenerator.getDefault().nextInt(height);
            int y = RandomGenerator.getDefault().nextInt(width);
            if(!snake.contains(new Coordinates(x,y))){
                board[x][y]=2;
                break;
            }
        }
        //generat a path
    }
    public int[][] getBoard() {
        return this.board;
    }

    public ArrayList<Coordinates> getSnake() {
        return snake;
    }
}
