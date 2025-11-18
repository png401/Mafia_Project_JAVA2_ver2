package controller;

import java.util.Scanner;

import model.Player;

public class 밤 implements IState{
	Scanner sc = new Scanner(System.in);
	
	@Override
	public void limitTime(int time) {
		// TODO Auto-generated method stub
		
	}
	
	public void start_night() {
		
	}

	@Override
	public void execute(사회자 manager) {
		// TODO 마피아는 마피아채팅해서 누구 죽일지 결정
		// TODO 의사는 누구 살릴지 결정
		// TODO 경찰은 누굴 조사할지 결정
		// 이 결과 알려주는 역할도 해야되는데 사회자가
		for(Player p : manager.players) {
			//죽었음 행동 불가
			if(!p.is_alive) continue;
			
			//skill이 null이면 넘어감
			if(p.skill == null) continue;
			
			//skill 실행(model은 행동 모름->controller가 호출)
			//p.skill.skill();
		}
		
		
		
	}
	
			
		
		
	}
