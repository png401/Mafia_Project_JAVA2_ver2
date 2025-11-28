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

	@Override
	public void limitTime(int time) {
		// TODO Auto-generated method stub
		int startSecond = 15; // 시간제한 15초
	}

	public void execute(사회자 매니저) {
		매니저.getCommandManager().broadcastAll("System:"+"밤이 시작되었습니다. 30초 안에 각자 능력을 사용할 플레이어의 ID를 입력해주세요");
		// 시간제한 15초
		try {
			Thread.sleep(30000);//일단 TEST를 위해 15초
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		nightResult(매니저);

		// 다음 밤 위해 초기화
		for (Player p : 매니저.players) {
			p.setNightTargetId(0);
		}

	}

	private void nightResult(사회자 매니저) {
		int mafiaTargetId = 0;
		int doctorTargetId = 0;
		int policeTargetId = 0;
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 각 역할별로 이번 밤에 누굴 골랐는지 모은다.
		for (Player p : 매니저.players) {
			if (!p.is_alive)
				continue;

			int target = p.getNightTargetId();

			System.out.println("밤타겟:"+target); //디버그용

			String role = p.getRole();
			if ("mafia".equals(role)) {
				mafiaTargetId = target;
			} else if ("doctor".equals(role)) {
				doctorTargetId = target;
			} else if ("police".equals(role)) {
				policeTargetId = target;

				System.out.println("경찰 조사 대상:"+policeTargetId +" target: "+ target);//디버그용

				// 0인지 체크하는 걸 추가했음
				if (policeTargetId == 0) {
					p.getServerThread().sendMessage("System:시간 내에 대상을 지목하지 못했습니다.");
					continue;
				}


				Player 피조사자 = 매니저.getPlayerById(policeTargetId);
				if (피조사자 != null) {
					if (피조사자.getRole().equals("mafia")) {
						p.getServerThread().sendMessage("Inspect:1");
					} else {
						p.getServerThread().sendMessage("Inspect:0");
					}
				} else {
					System.out.println("경찰: 잘못된 대상을 지목했습니다.");
				}

			}
		}



		// 마피아가 지목해서 사망했으면
		if (mafiaTargetId != 0) {
			Player 사망자 = 매니저.getPlayerById(mafiaTargetId);
			if (사망자 != null && 사망자.is_alive) {
                // 의사랑 마피아랑 똑같은 애 지목하면
                if (mafiaTargetId == doctorTargetId) {
                    매니저.setKilledID(0);
                    String msg = "System:" + "[밤 결과] "+사망자.id + "번 플레이어가 공격당했지만 의사의 치료로 생존했습니다!";
                    System.out.println(msg); // 서버 로그
                    매니저.getCommandManager().broadcastAll(msg);
                    return;
                }
                else {
                    사망자.is_alive = false;
                    매니저.ghosts.add(사망자);
                    매니저.players.remove(사망자.id - 1);
                    매니저.setKilledID(사망자.id);
                    String msg = "System:" + "[밤 결과] "+ 사망자.id + "번 플레이어가 사망했습니다.";
                    System.out.println(msg); // 서버 로그
                    매니저.getCommandManager().broadcastAll(msg);
                }
			}
            else {
				System.out.println("[결과] 마피아가 지목한 대상이 유효하지 않습니다.");
			}
		}


	}

}
