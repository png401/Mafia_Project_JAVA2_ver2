package model;

public class Heal implements ISkill{

@Override
public void skill(Player self, int targetId) {
	// TODO Auto-generated method stub
	self.setNightTargetId(targetId);
}
	
}