package core;

import model.agent.Agent;
import model.game.Game;
import view.MainView;

public class ApplicationFramework {
	private static ApplicationFramework instance;
	public final int HEIGHT = 8;
	public final int WIDTH = 8;
	private Game game;
	private Agent agent;
	
	private ApplicationFramework() {
	
	}
	
	public static ApplicationFramework getInstance() {
		if(instance == null) {
			instance = new ApplicationFramework();
			instance.game = new Game();
		}
		return instance;
	}
	
	public void initialize() {
		MainView.getInstance().setVisible(true);
		game.Run();
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
