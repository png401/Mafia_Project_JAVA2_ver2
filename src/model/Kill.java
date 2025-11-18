package model;

import controller.사회자;

public class Kill implements ISkill{

	@Override
	public int skill(Player self) {
		int targetID = 0;//마피아 그룹 채팅하고 결정된 죽일id값 받음
		System.out.println("마피아가 ID: "+targetID+"를 선택했습니다.");
		return targetID;
	}
}