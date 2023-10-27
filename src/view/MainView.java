package view;


import core.ApplicationFramework;
import model.observer.ISubscriber;
import model.observer.Notifications;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame implements ISubscriber {
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
        instance.getContentPane().add(generateBoardJPanel());
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
    private JPanel generateBoardJPanel(){
        JPanel panel = new JPanel();
        GridLayout gl = new GridLayout(height, width);
        gl.setHgap(2);
        gl.setVgap(2);
        panel.setLayout(gl);
        panel.setLocation(0,0);
        for(int i=0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JPanel square = new JPanel();
                if (ApplicationFramework.getInstance().getGame().getBoard()[i][j] == 0) {
                    square.setBackground(Color.DARK_GRAY);
                }
                if (ApplicationFramework.getInstance().getGame().getBoard()[i][j] == 1) {
                    square.setBackground(Color.GREEN);
                }
                if (ApplicationFramework.getInstance().getGame().getBoard()[i][j] == 2) {
                    square.setBackground(Color.RED);
                }
                square.setSize(10, 10);
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                panel.add(square);
            }
        }
        return panel;
    }
    @Override
    public void update(Object notification) {
        if(!(notification instanceof Notifications))
            return;
        if(notification==Notifications.UPDATE_UI){
            System.out.println(notification);
            instance.getContentPane().removeAll();
            instance.getContentPane().add(generateBoardJPanel());
            instance.validate();
        }

    }
}
