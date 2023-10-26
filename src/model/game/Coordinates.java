package model.game;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(!(obj instanceof Coordinates)){
            return false;
        }
        return ((Coordinates) obj).getX() == this.x && ((Coordinates) obj).getY() == this.y;
    }
}
