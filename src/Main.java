import core.ApplicationFramework;
import model.agent.Agent;
import model.agent.AgentA;
import model.agent.AgentB;
import model.agent.JSnakeAgent;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        ApplicationFramework main = ApplicationFramework.getInstance();
        main.setAgent(new AgentB());
        main.initialize();
    }


}
