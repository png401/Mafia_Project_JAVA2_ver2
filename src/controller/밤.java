package controller;

import java.util.Scanner;

import model.Player;

public class 밤 implements IState{
	Scanner sc = new Scanner(System.in);

	private int	healedID =0;
	private int killedID =0;


	public void setKilledID(int killedID) {
		this.killedID = killedID;
	}

	public void setHealedID(int healedID) {
		this.healedID = healedID;
	}

	@Override
	public void limitTime(int time) {
		// TODO Auto-generated method stub
		int startSecond = 15; //시간제한 15초
	}

	public void execute(사회자 매니저) {
		

		for(Player p : 매니저.players) {
			//죽었음 행동 불가
			if(!p.is_alive) continue;
			//skill이 null이면 넘어감
			if(p.skill == null) continue;

			//int targetId = askTargetId(p, 매니저);
			//p.skill.skill(p, targetId);
		}

		nightResult(매니저);

		//다음 밤 위해 초기화
		for(Player p : 매니저.players) {
			p.setNightTargetId(0);
		}

	}

//	private int askTargetId(Player self, 사회자 매니저) {
//		while(true) {
//			System.out.println("\n현재 생존자 목록:");
//			for(Player p : 매니저.players) {
//				if(p.is_alive) {
//					System.out.println("ID " + p.id + " - " + p.nickname + " (" + p.getRole() + ")");
//				}
//			}
//
//			System.out.print("[" + self.getRole() + " " + self.nickname
//                    + "] 대상 ID를 입력하세요: ");
//
//			int targetId = sc.nextInt();
//
//			Player target = 매니저.getPlayerById(targetId);
//
//			if (target == null) {
//                System.out.println("존재하지 않는 ID입니다. 다시 입력해주세요.");
//                continue;
//            }
//            if (!target.is_alive) {
//                System.out.println("이미 사망한 플레이어입니다. 다시 입력해주세요.");
//                continue;
//            }
//
//            return targetId;
//		}
//	}

	private void nightResult(사회자 매니저) {
		int mafiaTargetId=0;
		int doctorTargetId=0;
		int policeTargetId=0;

		// 각 역할별로 이번 밤에 누굴 골랐는지 모은다.
		for(Player p : 매니저.players) {
			if(!p.is_alive) continue;

			int target = p.getNightTargetId();

			String role = p.getRole();
			if("mafia".equals(role)) {
				mafiaTargetId = target;
			}
			else if("doctor".equals(role)) {
				doctorTargetId = target;
			}
			else if("police".equals(role)) {
				policeTargetId = target;
			}
		}

		//경찰 조사 결과 - 나중에 경찰에게만으로 변경
		if(policeTargetId !=0) {
			Player 피조사자 = 매니저.getPlayerById(policeTargetId);
			System.out.println("[결과] " + 피조사자.nickname + "의 직업은 "+ 피조사자.getRole() + "입니다.");
		}

		//의사랑 마피아랑 똑같은 애 지목하면
		if (mafiaTargetId != 0 && mafiaTargetId == doctorTargetId) {
            System.out.println("[결과] 의사의 보호로 아무도 죽지 않았습니다!");
            return;
        }

		//마피아가 지목해서 사망했으면
		if(mafiaTargetId != 0) {
			Player 사망자 = 매니저.getPlayerById(mafiaTargetId);
			if (사망자 != null && 사망자.is_alive) {
                사망자.is_alive = false;
                매니저.ghosts.add(사망자);
                System.out.println("[결과] " + 사망자.nickname + "이(가) 밤에 사망했습니다.");
            } else {
                System.out.println("[결과] 마피아가 지목한 대상이 유효하지 않습니다.");
            }
		}


		}

	}
