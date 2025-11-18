package model;

public class Heal implements ISkill{

	@Override
	public int skill(Player self) {
		int targetID = 0;//의사가 정한 살릴 id값 받음
		System.out.println("의사가 ID: "+targetID+"를 살리기로 선택했습니다.");
		return targetID;
	}
	
}