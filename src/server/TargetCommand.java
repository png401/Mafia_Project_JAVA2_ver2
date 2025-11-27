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
        // TODO Auto-generated method stub
    	
        int targetId = Integer.parseInt(payload);
<<<<<<< HEAD
        
        // 네트워크 쪽 플레이어 (이건 ServerThread가 들고 있는 Player)
        Player netPlayer = sender.getPlayer();

        // 사회자(logicBrain)가 관리하는 players 리스트에서 같은 id를 가진 Player 찾기
        Player logicPlayer = logicBrain.getPlayerById(netPlayer.id);
        
        System.out.println("타겟:"+targetId);
        logicPlayer.setNightTargetId(targetId); // Player의 객체의 NightTargetId 변경
=======
        System.out.println("서버가 받은 타켓:"+targetId);

        // 디버깅용
        Player p = sender.getPlayer();
        System.out.println("[TargetCommand] 수정 중인 Player 객체 주소: " + System.identityHashCode(p));

        p.setNightTargetId(targetId); // Player의 객체의 NightTargetId 변경
>>>>>>> 5f120b9a8f7e5b2d7baa4ef6e823cc372f43ec99
//        count++;
//        
//        if(count == logicBrain.get) {
//        	logicBrain.getState().execute(logicBrain);
//        	count = 0;
//        }
    }
}
