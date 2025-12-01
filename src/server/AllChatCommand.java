package server;

import controller.IState;
import controller.사회자;
import controller.토론;

public class AllChatCommand implements ICommand {
	private CommandManager networkBrain;
	private 사회자 logicBrain;
	public AllChatCommand(CommandManager cm, 사회자 logic) {
		this.networkBrain = cm;
		this.logicBrain=logic;
	}

	@Override
	public void execute(ServerThread sender, String payload, IState currentState) {
		// TODO Auto-generated method stub
		// 토론 상태일 때만 실행됨.
		if(currentState instanceof 토론) {					
			if(sender.getPlayer().getIs_alive()) {
				networkBrain.broadcastAll("Message:"+sender.getPlayer().getNickname() + ": " + payload);
			} 
		} else {
			sender.sendMessage("채팅은 살아있는 사람만 토론 상태에서만 할 수 있습니다.");
		}
	}

}
