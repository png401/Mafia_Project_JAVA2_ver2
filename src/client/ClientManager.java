package client;
import java.io.IOException;

//뷰와 클라이언트 사이의 중재자 역할
public class ClientManager {

    private ClientThread clientThread;
    // 뷰를 업데이트할 수 있도록 하는 기능이 필요함.
    public ClientManager() {
        // this.view = view;
    }

    //서버 연결 시도하기
    public void connect(String host, int port) {
        try {
            this.clientThread = new ClientThread(host, port, this);
            this.clientThread.start();
        } catch (IOException e) {
            // 뷰에 연결 실패 메세지도 필요할까요?
            System.err.println("서버 연결 실패: " + e.getMessage());
        }
    }

    //뷰에서 받은 메세지(스킬 사용, 투표 등)을 서버에 전달
    public void sendMessage(String message) {
        if (clientThread != null) {
            clientThread.sendMessage(message);
        } else {
        	//뷰에도 표시?
            System.err.println("서버에 연결되지 않았습니다.");
        }
    }

    //InputThread가 서버로부터 메세지 수신 시...
    public void handleMessage(String message) {
        // TODO: 서버와 약속된 프로토콜에 따라 메시지를 파싱해야 합니다.
        // (예: "CHAT:P1:안녕하세요", "NOTICE:밤이 되었습니다", "VOTE_RESULT:P3")

        System.out.println("서버로부터 메시지 수신: " + message);

        // --- View 업데이트 예시 ---
        // if (message.startsWith("CHAT:")) {
        //     String[] parts = message.split(":", 3);
        //     view.appendChatMessage(parts[1], parts[2]); // (보낸사람, 메시지)
        // } else if (message.startsWith("NOTICE:")) {
        //     String notice = message.substring(7);
        //     view.showNotice(notice); // 공지사항 표시
        // } else if (message.startsWith("GAME_START:")) {
        //     String role = message.split(":")[1];
        //     view.showRole(role); // 직업 표시
        // } else if (message.startsWith("PLAYER_DIED:")) {
        //     String playerName = message.split(":")[1];
        //     view.updatePlayerListAsDead(playerName); // 사망 처리
        // }
        // ... 기타 등등 (투표 결과, 스킬 결과, 게임 종료 등)
    }

    //서버 연결 종료 시? 혹은 죽었을 때? 아예 채팅도 못 치게?
    public void onDisconnected() {
        // 서버 연결 끊겼다고 뷰에도 표시하기
    	// 뷰 버튼 비활성화하기 해야 함!
        System.err.println("서버와 연결이 끊겼습니다.");
    }
}