import core.ApplicationFramework;
import model.agent.*;

public class Main {
	public static void main(String[] args) {
		ApplicationFramework main = ApplicationFramework.getInstance();
		main.setAgent(new BFSWithTailFollowing());
		main.initialize();
	}
}
