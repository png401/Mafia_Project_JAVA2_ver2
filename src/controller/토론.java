package controller;

public class 토론 implements IState{

	public void execute(사회자 매니저) {
		매니저.getCommandManager().broadcastAll("System:"+"DAY "+(매니저.getDayCount()+1)+" 토론이 시작되었습니다. 토론시간은 3분 주어집니다");
		
		try {
			Thread.sleep(180000);//3분
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        // TODO: 실제로는 소켓 채팅 or 콘솔 입력 등
        System.out.println("(여기서 플레이어들이 자유롭게 토론합니다...)");
        // 시간 제한, 채팅 로그 등 넣으려면 여기
	}

}
