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
        Player p = sender.getPlayer();
    	
    	if(currentState instanceof 밤) {
            if (p != null) {
                if(p.is_alive) {
                    p.setNightTargetId(targetId);
                    System.out.println("[서버] " + p.nickname + " -> 타겟 " + targetId + " 설정완료");
                }
            }
    	}
    	else if(currentState instanceof 투표) {
            // 살아있는 플레이어의 투표만 받게 하기.
            if(p.is_alive) {
                logicBrain.voteResult.set(targetId, (logicBrain.voteResult.get(targetId)+1));
            }
    	}
    }
}
