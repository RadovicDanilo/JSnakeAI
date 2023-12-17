import core.ApplicationFramework;
import model.agent.SingleHamiltonianCycleWithShortcuts;

public class Main {
	public static void main(String[] args) {
		ApplicationFramework main = ApplicationFramework.getInstance();
		main.setAgent(new SingleHamiltonianCycleWithShortcuts());
		main.initialize();
	}
}
