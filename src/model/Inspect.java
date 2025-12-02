package model;

import controller.사회자;

public class Inspect implements ISkill{

	@Override
    public void skill(Player self, 사회자 manager) {
        int targetId = self.getNightTargetId();

        if (targetId == -1) {
            self.getServerThread().sendMessage("System:시간 내에 대상을 지목하지 못했습니다.");
            return;
        }

        Player 피조사자 = manager.getPlayerById(targetId);
        if (피조사자 == null) {
            self.getServerThread().sendMessage("System:잘못된 대상을 지목했습니다.");
            return;
        }

        if ("mafia".equals(피조사자.getRole())) {
            self.getServerThread().sendMessage("Inspect:1");
        } else {
            self.getServerThread().sendMessage("Inspect:0");
        }
    }

}