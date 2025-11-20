package controller;

public class 토론 implements IState{

	@Override
	public void limitTime(int time) {
		// TODO Auto-generated method stub

	}

	public void execute(사회자 매니저) {
		System.out.println("\n==============================");
        System.out.println("DAY " + 매니저.dayCount + " - 낮 / 토론");
        System.out.println("==============================");

        // TODO: 실제로는 소켓 채팅 or 콘솔 입력 등
        System.out.println("(여기서 플레이어들이 자유롭게 토론합니다...)");
        // 시간 제한, 채팅 로그 등 넣으려면 여기
	}

}
