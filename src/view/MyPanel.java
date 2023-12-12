package view;

import core.ApplicationFramework;
import model.game.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyPanel extends JPanel {
	private final BasicStroke stroke = new BasicStroke(41.0f);
	private final BasicStroke basic = new BasicStroke(1.0f);
	
	public MyPanel() {
		
		setBackground(Color.BLACK);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.GREEN);
		g2d.setStroke(stroke);
		ArrayList<Coordinate> snake = ApplicationFramework.getInstance().getGame().getSnake();
		Coordinate apple = ApplicationFramework.getInstance().getGame().getAppleCoordinate();
		int BLOCK_SIZE = 55;
		int BLOCK_SIZE_half = 27;
		
		for(int i = 0; i < snake.size() - 1; i++) {
			g2d.drawLine(snake.get(i).getX() * BLOCK_SIZE + BLOCK_SIZE_half, snake.get(i).getY() * BLOCK_SIZE + BLOCK_SIZE_half, snake.get(i + 1).getX() * BLOCK_SIZE + BLOCK_SIZE_half, snake.get(i + 1).getY() * BLOCK_SIZE + BLOCK_SIZE_half);
		}
		g2d.setColor(Color.RED);
		Rectangle rectangle = new Rectangle(apple.getX() * BLOCK_SIZE, apple.getY() * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
		g2d.fill(rectangle);
	}
}
