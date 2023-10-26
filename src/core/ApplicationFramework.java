package core;

import model.agent.Agent;
import model.game.Game;
import view.MainView;

public class ApplicationFramework {
    public final int height = 20;
    public final int width = 20;

    private static ApplicationFramework instance;
    private Game game;
    private Agent agent;

    private ApplicationFramework(){

    }
    public void initialize(){
        MainView.getInstance().setVisible(true);
    }
    public static ApplicationFramework getInstance(){
        if(instance==null){
            instance = new ApplicationFramework();
            instance.game=new Game();
        }
        return instance;
    }

    public Game getGame() {
        return this.game;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
