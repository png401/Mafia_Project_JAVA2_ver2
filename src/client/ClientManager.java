package client;

import java.io.IOException;

import controller.사회자;
import model.Player;
import view.Lobby;

public class ClientManager {

    private ClientThread clientThread;
    private Lobby lobby;
    private String myName;
    //private 사회자 사회자 = controller.사회자.getInstance();

    public String getMyName() {
		return myName;
	}

	private Player me; 
        
	public void setMe(Player me) {
		this.me = me;
	}


	public ClientManager(Lobby lobby) {
    	this();
    	this.lobby = lobby;
    }

    public ClientManager() {
        // 서버 연결
        try {
            clientThread = new ClientThread("10.240.165.152", 50023, this);
            clientThread.start();
        } catch (IOException e) {
            System.err.println("서버 연결 실패: " + e.getMessage());
        }
    }

    // 로비 생성 시 자신의 로비와 연결
    /*
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }*/

    public void setMyName(String nickname) {
    	this.myName = nickname;
    	//사회자.addLobby(lobby);
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

        //if (lobby == null) return;

        // Case 1: 플레이어 입장 (서버가 "Join:닉네임"을 보냄)
        if (message.startsWith("Join:")) {
            String nickname = message.substring(5); // "Join:" 제거하고 닉네임만 추출

            // 로비 화면의 리스트에 닉네임 추가
            lobby.newPlayerEntered(nickname);
            System.out.println("view 함수 호출");
        }
        // Case 2: 게임 시작 (서버가 "Start:"를 보냄)
        else if (message.startsWith("Start:")) {
            // 로비 화면을 게임 화면으로 전환
            lobby.start();
        }

        else if (message.startsWith("ROLE:")) {
        	String role = message.substring(5);
        	System.out.println("내 역할: "+role);
        	lobby.getView().setRoleView(role);
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