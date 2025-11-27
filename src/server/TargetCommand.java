package server;

import controller.IState;
import controller.사회자;
import model.Player;

public class TargetCommand implements ICommand {
    private CommandManager networkBrain;
    private 사회자 logicBrain;
    private int count = 0;
    
    public TargetCommand(CommandManager cm, 사회자 logic) {
        this.networkBrain = cm;
        this.logicBrain=logic;
    }

    @Override
    public void execute(ServerThread sender, String payload, IState currentState) {
        for (Player player : logicBrain.players) {
			if (player.getServerThread() == sender) {
				int target = Integer.parseInt(payload);
				player.setNightTargetId(target);
			}
		}
    }
}
