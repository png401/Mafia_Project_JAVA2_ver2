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
		// 각 살아있는 플레이어에게 투표 입력 받기
		for(Player p : 매니저.players) {
			if(p.is_alive==false) continue;
			
			int targetId = voteWho(p, 매니저);
			p.setVoteTargetId(targetId);
		}
		
		//투표 결과 집계 + 사망 처리
		voteResult(매니저);
		
		//다음을 위해 투표 정보 초기화
		for(Player p : 매니저.players) {
			p.setVoteTargetId(0);
		}
	}
	
	private int voteWho(Player self, 사회자 매니저) {
		//생존자 목록
		while(true) {
		System.out.println("[투표]"+self.nickname+"님, 누가 마피아인 것 같나요? 의심가는 ID를 입력하세요.");
		
		//targetId받아와야 함. 콘솔 입력 받는 거 말고. 수정 필요.
		int targetId = sc.nextInt();
		
		Player target = 매니저.getPlayerById(targetId);
		
		if(target == null) {
			System.out.println("존재하지 않는 ID입니다. 다시 입력해주세요.");
			continue;
		}
		
		if(target.is_alive==false) {
			System.out.println("이미 사망한 플레이어입니다. 다시 입력해주세요.");
			continue;
		}
		
		return targetId;
		}
	}
	
	private void voteResult(사회자 매니저) {
		//득표 수 집계(key: targetId, 받은 표 수)
		Map<Integer, Integer> voteCount = new HashMap<>();
		
		for(Player p : 매니저.players) {
			if(p.is_alive==false) continue;
			
			if(voteCount.containsKey(p.getVoteTargetId())) {
				int before = voteCount.get(p.getVoteTargetId());
				voteCount.put(p.getVoteTargetId(), before+1);
			}
			else {
				voteCount.put(p.getVoteTargetId(), 1);
			}
		}
		
		//최다 득표자 찾기
		int maxVote = 0;
		int maxVoteId = 0;
		boolean tie = false;
		
		//
		for(Map.Entry<Integer, Integer> entry : voteCount.entrySet()) {
			if(entry.getValue() > maxVote) {
				maxVote = entry.getValue();
				maxVoteId = entry.getKey();
				tie = false;
			} else if(entry.getValue()==maxVote) {
				tie = true;//최다 득표 여러명
			}
		}
		
		//최다 득표자가 한 명이 아닐 경우 처리
		if(tie==true) {
			System.out.println("[투표 결과] 최다 득표자가 여러 명입니다.");
			return;
		}
		
		//최다 득표자 사망처리
		Player votedMax = 매니저.getPlayerById(maxVoteId);
		if(votedMax == null || votedMax.is_alive==false) {
			System.out.println("[투표 결과] 최다 득표자의 상태가 유효하지 않습니다.");
			return;
		}
		
		votedMax.is_alive = false;
		매니저.ghosts.add(votedMax);
		
		System.out.println("[투표 결과] " + votedMax.nickname + " 님이 사망했습니다.");
        System.out.println(votedMax.nickname+"의 직업은 " + votedMax.getRole()+"입니다.");
		
		
	}

}
