package model;

public class Inspect implements ISkill{

	@Override
	public int skill(Player self) {
		int targetID = 0;//경찰이 조사할 id값 받음
		System.out.println("경찰이 ID: "+targetID+"를 선택했습니다.");
		System.out.println(targetID+"의 직업은 를 선택했습니다.");
		
		return targetID;
	}

}