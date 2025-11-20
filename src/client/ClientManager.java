package client;

import java.io.IOException;
import view.Lobby;

public class ClientManager {

    private ClientThread clientThread;
    private Lobby lobby;

    public ClientManager() {
        // 서버 연결
        try {
            clientThread = new ClientThread("10.20.107.60", 50023, this);
            clientThread.start();
        } catch (IOException e) {
            System.err.println("서버 연결 실패: " + e.getMessage());
        }
    }

    // 로비 생성 시 자신의 로비와 연결
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }


    // 서버로 메세지 전송
    public void sendMessage(String message) {
        if (clientThread != null) {
            clientThread.sendMessage(message);
        } else {
            System.err.println("서버에 연결되지 않았습니다.");
        }
    }

    // 서버에게서 받은 메세지 처리
    public void handleMessage(String message) {
        System.out.println("[Client 수신]: " + message);

        if (lobby == null) return;

        // Case 1: 플레이어 입장 (서버가 "Join:닉네임"을 보냄)
        if (message.startsWith("Join:")) {
            String nickname = message.substring(5); // "Join:" 제거하고 닉네임만 추출
            // 로비 화면의 리스트에 닉네임 추가
            lobby.newPlayerEntered(nickname);
        }
        // Case 2: 게임 시작 (서버가 "Start:"를 보냄)
        else if (message.startsWith("Start:")) {
            // 로비 화면을 게임 화면으로 전환
            lobby.start();
        }
        // Case 3: 채팅 메시지 등 그 외 처리
        else {
            // 예: lobby.appendChat(message); 와 같이 구현 가능
        }
    }

    // 연결 종료 시 처리
    public void onDisconnected() {
        System.out.println("서버와의 연결이 종료되었습니다.");
    }
}