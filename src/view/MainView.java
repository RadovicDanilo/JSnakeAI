package view;


import core.ApplicationFramework;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainView extends JFrame {
    private final int height = ApplicationFramework.getInstance().height;
    private final int width = ApplicationFramework.getInstance().width;

    private static MainView instance;
    private MainView(){}
    private void initialize(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(width*40, height*40);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(width*40, height*40));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("JSnakeAI");

        GridLayout gl = new GridLayout(20, 20);
        gl.setHgap(2);
        gl.setVgap(2);
        JPanel panel = new JPanel();
        panel.setLayout(gl);
        panel.setLocation(0,0);
        for(int i=0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JPanel square = new JPanel();
                if (ApplicationFramework.getInstance().getGame().getBoard()[i][j] == 0) {
                    square.setBackground(Color.WHITE);
                }
                if (ApplicationFramework.getInstance().getGame().getBoard()[i][j] == 1) {
                    square.setBackground(Color.BLUE);
                }
                if (ApplicationFramework.getInstance().getGame().getBoard()[i][j] == 2) {
                    square.setBackground(Color.RED);
                }
                square.setSize(10, 10);
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panel.add(square);
            }
        }
        instance.add(panel);
    }
    public static MainView getInstance()
    {
        if(instance == null)
        {
            instance = new MainView();
            instance.initialize();
        }
        return instance;
    }
}
