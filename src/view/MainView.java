package view;


import core.ApplicationFramework;
import model.game.Coordinate;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
	private final int HEIGHT = ApplicationFramework.getInstance().HEIGHT;
	private final int WIDTH = ApplicationFramework.getInstance().WIDTH;
	private static MainView instance;
	
	private MainView() {
	}
	
	private void initialize() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		setSize(WIDTH * 40, HEIGHT * 40);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(WIDTH * 40, HEIGHT * 40));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("JSnakeAI");
		instance.getContentPane().add(generateBoardJPanel());
	}
	
	public static MainView getInstance() {
		if(instance == null) {
			instance = new MainView();
			instance.initialize();
		}
		return instance;
	}
	
	private JPanel generateBoardJPanel() {//TODO make this look better
		JPanel panel = new JPanel();
		GridLayout gl = new GridLayout(HEIGHT, WIDTH);
		gl.setHgap(2);
		gl.setVgap(2);
		panel.setLayout(gl);
		panel.setLocation(0, 0);
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				JPanel square = new JPanel();
				Coordinate coordinate = new Coordinate(j, i);
				if(coordinate.equals(ApplicationFramework.getInstance().getGame().getAppleCoordinate())) {
					square.setBackground(Color.RED);
				}else if(coordinate.equals(ApplicationFramework.getInstance().getGame().getHeadCoordinate())) {
					square.setBackground(Color.BLACK);
				}else if(coordinate.equals(ApplicationFramework.getInstance().getGame().getSnake().get(0))) {
					square.setBackground(Color.BLUE);
				}else if(ApplicationFramework.getInstance().getGame().getSnake().contains(coordinate)) {
					square.setBackground(Color.GREEN);
				}else {
					square.setBackground(Color.GRAY);
				}
				square.setSize(10, 10);
				square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				panel.add(square);
			}
		}
		return panel;
	}
	
	public void update() {
		instance.getContentPane().removeAll();
		instance.getContentPane().add(generateBoardJPanel());
		instance.validate();
	}
}
