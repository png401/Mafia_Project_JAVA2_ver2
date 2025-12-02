package model;

import controller.사회자;

public class Heal implements ISkill{

	 @Override
	    public void skill(Player self, 사회자 manager) {
	        int targetId = self.getNightTargetId();
	        if (targetId == -1) {
	            self.getServerThread().sendMessage("System:시간 내에 대상을 지목하지 못했습니다.");
	            return;
	        }
	        manager.registerDoctorHeal(targetId);
	    }
	
}