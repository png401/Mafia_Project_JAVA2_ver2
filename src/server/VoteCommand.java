package server;

import controller.IState;
import controller.사회자;

public class VoteCommand implements ICommand{
    private CommandManager networkBrain;
    private 사회자 logicBrain;
    public VoteCommand(CommandManager cm, 사회자 logic) {
        this.networkBrain = cm;
        this.logicBrain=logic;
    }

    @Override
    public void execute(ServerThread sender, String payload, IState currentState) {
        // TODO Auto-generated method stub

    }
}
