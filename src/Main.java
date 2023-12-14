import core.ApplicationFramework;
import model.agent.BFSWithTailFollowing;
import model.agent.PertubatedHamiltonianCycle;
import model.agent.SingleHamiltonianCycle;

public class Main {
	public static void main(String[] args) {
		ApplicationFramework main = ApplicationFramework.getInstance();
		main.setAgent(new BFSWithTailFollowing());
		main.initialize();
	}
}
