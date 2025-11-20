package server;

import controller.IState;
import controller.밤;
import controller.사회자;

public class MafiaChatCommand implements ICommand{
	private CommandManager networkBrain;
	private 사회자 logicBrain;
	public MafiaChatCommand(CommandManager cm, 사회자 logic) {
		this.networkBrain = cm;
		this.logicBrain=logic;
	}
	
	@Override
	public void execute(ServerThread sender, String payload, IState currentState) {
		// TODO Auto-generated method stub
		// 밤 상태에만 채팅 가능
		if(currentState instanceof 밤) {			
			if(sender.getPlayer().getRole().equals("MAFIA")) {
				networkBrain.broadcastToMafia(sender.getPlayer().nickname + ": " + payload);
			}
		}
	}

}
