package model;

public class Inspect implements ISkill{

	@Override
	public void skill(Player self, int targetId) {
		// TODO Auto-generated method stub
		self.setNightTargetId(targetId);
	}

}