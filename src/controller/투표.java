package controller;

import model.Player;

public class 투표 implements IState {
	

	@Override
	public void limitTime(int time) {
		// TODO Auto-generated method stub
	}

	@Override
	public void execute(사회자 매니저) {
		// TODO Auto-generated method stub
		매니저.getCommandManager().broadcastAll("Vote:");
		매니저.getCommandManager().broadcastAll("System:"+"투표가 시작되었습니다. 30초 안에 마피아로 의심되는 플레이어의 ID를 입력해주세요");
		
		try {
			Thread.sleep(30000);//일단 TEST를 위해 15초
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		voteResult(매니저);
		
		// 다음 투표 위해 초기화
		for (int i = 0; i < 매니저.voteResult.length; i++) {
			매니저.voteResult[i] = 0;
		}
		
	}
	
	private void voteResult(사회자 매니저) {
		int killedID = 0;
		for (int i = 0; i < 매니저.voteResult.length; i++) {
			if(매니저.voteResult[i] >= 매니저.voteResult[killedID]) killedID = i;
		}
		if(매니저.voteResult[killedID] == 0 || 매니저.voteResult[killedID] == 매니저.voteResult[0]) {
			매니저.getCommandManager().broadcastAll("System:"+"[투표 결과] 아무도 사망하지 않았습니다.");		
		}
		else {
			Player 사망자 = 매니저.getPlayerById(killedID);
			사망자.is_alive = false;
            매니저.ghosts.add(사망자);
			매니저.getCommandManager().broadcastAll("System:"+"[투표 결과] "+(killedID)+"번 플레이어가 투표로 사망했습니다.");
			//Jlist 업데이트
            매니저.getCommandManager().broadcastAll("List:"+(사망자.id));
		}
			
	}

}
