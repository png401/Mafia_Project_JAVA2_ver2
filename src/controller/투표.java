package controller;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Player;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import model.Player;

public class 투표 implements IState {

	@Override
	public void execute(사회자 매니저) {

		// TODO Auto-generated method stub
		매니저.getCommandManager().broadcastAll("System:"+"투표가 시작되었습니다. 30초 안에 마피아로 의심되는 플레이어의 ID를 입력해주세요");
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		voteResult(매니저);
		
		// 다음 투표 위해 초기화
		매니저.resetVotes();
		
	}
	
	private void voteResult(사회자 매니저) {
		List<Integer> votes = 매니저.getVoteResult();

        int max = Collections.max(votes);
        Integer killedID = votes.indexOf(max);

        // 최대 득표자 수 찾기
        int topCandidates = 0;
        for (int i = 0; i < votes.size(); i++) {
            if (votes.get(i) == max) {
                topCandidates++;
            }
        }

        if (max == 0 || topCandidates > 1) {
            매니저.getCommandManager().broadcastAll(
                "System:" + "[투표 결과] 아무도 사망하지 않았습니다."
            );
        } else {
            Player 사망자 = 매니저.getPlayerById(killedID);
            사망자.setIs_alive(false);
            매니저.addGhost(사망자);
            매니저.getCommandManager().broadcastAll(
                "System:" + "[투표 결과] " + killedID + "번 플레이어가 투표로 사망했습니다."
            );
            // JList 업데이트
            매니저.getCommandManager().broadcastAll("List:" + 사망자.getId());
        }
    }
}
