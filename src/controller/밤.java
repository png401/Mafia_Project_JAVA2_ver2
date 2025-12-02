package controller;

import java.util.Scanner;

import model.Player;

public class 밤 implements IState {

	private int healedID = 0;
	private int killedID = 0;

	public void setKilledID(int killedID) {
		this.killedID = killedID;
	}

	public void setHealedID(int healedID) {
		this.healedID = healedID;
	}

	public void execute(사회자 매니저) {
		매니저.getCommandManager().broadcastAll("System:"+"밤이 시작되었습니다. 30초 안에 각자 능력을 사용할 플레이어의 ID를 입력해주세요");
		// 시간제한 15초
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		nightResult(매니저);

		// 다음 밤 위해 초기화
		for (Player p : 매니저.getPlayers()) {
			p.setNightTargetId(0);
		}
		매니저.resetNightActions();

	}

    private void nightResult(사회자 매니저) {

        // 1) 각 플레이어의 스킬에게 할 일 시킴.
        for (Player p : 매니저.getPlayers()) {
            if (!p.getIs_alive()) continue;
            if (p.getSkill() == null) continue;

            p.getSkill().skill(p, 매니저);
        }

        // 2) 스킬들이 manager에 등록해둔 결과만 가지고 마무리 처리
        int mafiaTargetId = 매니저.getMafiaTargetId();
        int doctorTargetId = 매니저.getDoctorTargetId();

        if (mafiaTargetId != -1) {
            Player 사망자 = 매니저.getPlayerById(mafiaTargetId);
            if (사망자 != null && 사망자.getIs_alive()) {

                if (mafiaTargetId == doctorTargetId) {
                    매니저.setKilledID(0);
                    String msg = "System:[밤 결과] " + 사망자.getId()
                        + "번 플레이어가 공격당했지만 의사의 치료로 생존했습니다!";
                    매니저.getCommandManager().broadcastAll(msg);
                    return;
                } else {
                    사망자.setIs_alive(false);
                    매니저.addGhost(사망자);
                    매니저.setKilledID(사망자.getId());
                    매니저.getCommandManager().broadcastAll("List:" + 사망자.getId());
                    String msg = "System:[밤 결과] " + 사망자.getId() + "번 플레이어가 사망했습니다.";
                    매니저.getCommandManager().broadcastAll(msg);
                }
            } else {
                System.out.println("[결과] 마피아가 지목한 대상이 유효하지 않습니다.");
            }
        }
    }
}
