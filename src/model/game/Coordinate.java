package model.game;

public class Coordinate {
	private final int x;
	private final int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coordinate move(Direction direction) {
		Coordinate head = new Coordinate(0, 0);
		switch(direction) {
			case LEFT -> {
				head = new Coordinate(x - 1, y);
				break;
			}
			case RIGHT -> {
				head = new Coordinate(x + 1, y);
				break;
			}
			case UP -> {
				head = new Coordinate(x, y - 1);
				break;
			}
			case DOWN -> {
				head = new Coordinate(x, y + 1);
				break;
			}
		}
		return head;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof Coordinate)) {
			return false;
		}
		return ((Coordinate) obj).getX() == this.x && ((Coordinate) obj).getY() == this.y;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
