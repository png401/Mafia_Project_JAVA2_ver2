package server;

import controller.IState;
import controller.RoleFactory;
import controller.사회자;
import model.Player;

public class JoinCommand implements ICommand{
	private CommandManager networkBrain;
	private 사회자 logicBrain;
	public JoinCommand(CommandManager cm, 사회자 logic) {
		this.networkBrain = cm;
		this.logicBrain=logic;
	}

	// 클라이언트가 들어오면 닉네임을 받아서 Player 객체 생성
	@Override
	public void execute(ServerThread sender, String payload, IState currentState) {
		// TODO Auto-generated method stub

		// Player 객체를 사회자한테 받아서 Lobby에 닉네임을 broadcast하기
		//Player newPlayer = logicBrain.createNewPlayer(payload);
		//sender.setPlayer(newPlayer);
		
		networkBrain.broadcastAll(payload); // 여기서 payload 플레이어의 닉네임
	}

}
