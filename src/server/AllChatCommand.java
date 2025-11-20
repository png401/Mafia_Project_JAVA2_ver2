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
			/*죽으면 뷰에서 막기로
			if(sender.getPlayer().is_alive) {
				networkBrain.broadcastAll(sender.getPlayer().nickname + ": " + payload);
			} // 죽었으면 그냥 아무것도 안되게 했습니다. 상태를 알려주는 것도 좋을 것 같아요.
			*/
		} else {
			sender.sendMessage("채팅은 토론 상태에서만 할 수 있습니다.");
		}
	}

}
