package server;

import controller.IState;
import controller.밤;
import controller.사회자;
import controller.투표;
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

    	int targetId = Integer.parseInt(payload);
    	
    	if(currentState instanceof 밤) {
            if (sender.getPlayer() != null) {
                sender.getPlayer().setNightTargetId(targetId);
                System.out.println("[서버] " + sender.getPlayer().nickname + " -> 타겟 " + targetId + " 설정완료");
            }
    	}
    	else if(currentState instanceof 투표) {
    		logicBrain.voteResult.set(targetId, (logicBrain.voteResult.get(targetId)+1));
    	}
    }
}
