package server;

import controller.IState;
import controller.사회자;

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
        // TODO Auto-generated method stub
        int targetId = Integer.parseInt(payload);
        sender.getPlayer().setNightTargetId(targetId); // Player의 객체의 NightTargetId 변경
        count++;
        
        if(count == 4) {
        	logicBrain.getState().execute(logicBrain);
        	count = 0;
        }
    }
}
