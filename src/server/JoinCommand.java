package server;

import controller.IState;
import controller.사회자;
import model.Player;

// -------- 로비에 입장한 사람 목록 띄우기.(이건 서버가 안 해도 될까요?)
public class JoinCommand implements ICommand{
	private CommandManager networkBrain;
	private 사회자 logicBrain;
	public JoinCommand(CommandManager cm, 사회자 logic) {
		this.networkBrain = cm;
		this.logicBrain=logic;
	}
	
	@Override
	public void execute(ServerThread sender, String payload, IState currentState) {
		// TODO Auto-generated method stub
		// 입장한 사람의 Player 객체를 등록(이런 느낌으로 만들면 될 것 같아요)
		//Player newPlayer = logicBrain.createNewPlayer(payload);
		//sender.setPlayer(newPlayer);
		
		//gameManager.broadcastAll(payload+"님이 입장했습니다.");
	}

}
