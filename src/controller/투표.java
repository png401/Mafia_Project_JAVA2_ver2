package controller;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Player;

public class 투표 implements IState {
	
	Scanner sc = new Scanner(System.in);
	
	@Override
	public void limitTime(int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(사회자 매니저) {
		//투표 시작 안내 브로드캐스트
        매니저.getCommandManager().broadcastAll("System:낮 투표가 시작되었습니다. 마피아라고 생각하는 사람의 번호를 입력하세요.");
		
		//투표 시간 동안 대기(30초)
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//투표 결과 집계 + 사망 처리
		voteResult(매니저);
		
		//다음을 위해 투표 정보 초기화
		for(Player p : 매니저.players) {
			p.setVoteTargetId(0);
		}
	}
	
	
	private void voteResult(사회자 매니저) {
		
		
		//득표 수 집계(key: targetId, 받은 표 수)
		Map<Integer, Integer> voteCount = new HashMap<>();
		
		for(Player p : 매니저.players) {
			if(p.is_alive==false) continue;
			if (p.getVoteTargetId() == 0) continue; // 투표 안 한 사람은 패스??
			
			
			if(voteCount.containsKey(p.getVoteTargetId())) {
				int before = voteCount.get(p.getVoteTargetId());
				voteCount.put(p.getVoteTargetId(), before+1);
			}
			else {
				voteCount.put(p.getVoteTargetId(), 1);
			}
		}
		
		if (voteCount.isEmpty()) {
            매니저.getCommandManager().broadcastAll(
                    "System:유효한 투표가 없어 아무도 처형되지 않았습니다.");
            return;
        }
		
		//최다 득표자 찾기
		int maxVoteCount = 0;
		int maxVoteId = 0;
		boolean tie = false;
		
		//(타겟ID, 득표수) 엔트리에서 하나씩 뽑아내서 maxVote 갱신
		for(Map.Entry<Integer, Integer> entry : voteCount.entrySet()) {
			if(entry.getValue() > maxVoteCount) {
				maxVoteCount = entry.getValue();
				maxVoteId = entry.getKey();
				tie = false;
			} else if(entry.getValue()==maxVoteCount) {
				tie = true;//최다 득표 여러명
			}
		}
		
		//최다 득표자가 한 명이 아닐 경우 처리
		if(tie==true) {
			매니저.getCommandManager().broadcastAll("System:최다 득표자가 여러 명이라 오늘은 아무도 처형되지 않습니다.");
			return;
		}
		
		//여기까지 와서 최다 득표자 결정되면 사망처리
		Player votedMax = 매니저.getPlayerById(maxVoteId);
		if(votedMax == null || votedMax.is_alive==false) {
			System.out.println("[투표 결과] 최다 득표자의 상태가 유효하지 않습니다.");
			return;
		}
		
		votedMax.is_alive = false;
		매니저.ghosts.add(votedMax);
		매니저.players.remove(votedMax);
		
		String msg = "System:낮 투표 결과, " + votedMax.id + "번 플레이어("
                + votedMax.nickname + ")가 처형되었습니다. 직업은 "
                + votedMax.getRole() + "입니다.";
        System.out.println(msg); // 서버 로그
        매니저.getCommandManager().broadcastAll(msg);
	}

}
