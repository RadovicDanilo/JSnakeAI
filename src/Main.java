import core.ApplicationFramework;
import model.agent.Agent;
import model.agent.AgentA;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        ApplicationFramework main = ApplicationFramework.getInstance();
        main.setAgent(new AgentA());
        main.initialize();
    }


}
