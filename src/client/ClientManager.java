package client;

import java.io.IOException;

import javax.swing.DefaultListModel;

import view.Lobby;
import view.MafiaChatView;
import view.View2;

public class ClientManager {
    private ClientThread clientThread;
    private Lobby lobby;
    private MafiaChatView mafiaChatView;
    
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}

    public ClientManager() {
        // 서버 연결
        try {

            clientThread = new ClientThread("10.240.13.180", 50023, this);

            clientThread.start();
        } catch (IOException e) {
            System.err.println("서버 연결 실패: " + e.getMessage());
        }
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
    public void handleMessage(String message) throws IOException {
        System.out.println("[Client 수신]: " + message);

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
        // Case 3: 직업 배정 (사회자가 "Role:직업"을 보냄)
        else if (message.startsWith("Role:")) {
        	String role = message.substring(5);
        	System.out.println("내 역할: "+role);
        	lobby.getView().setRoleView(role);

            // 직업이 마피아면 마피아 채팅창 따로 또 띄우기
            if ("mafia".equals(role)) { // 문자열 비교 주의 ("mafia"인지 "MAFIA"인지 서버랑 맞추세요)
                if (mafiaChatView == null) {
                    mafiaChatView = new MafiaChatView(this);
                    mafiaChatView.setVisible(true);
                }
            }
        }
        else if (message.startsWith("Skill:")) {
        	String skill = message.substring(6);
        	System.out.println("내 역할: "+skill);
        	lobby.getView().setSkillButton(skill);
        }
        //Case 4: playerList 처리
        else if (message.startsWith("Players:")) {
        	String playersMessage = message.substring(8);
        	String[] playersMessageSplit = playersMessage.split(";");

        	DefaultListModel<String> players = new DefaultListModel<String>();
        	for (String string : playersMessageSplit) {
				players.addElement(string);
			}

        	lobby.getView().setPlayersModel(players);
        }
        // Case 4: 채팅 메시지 등 그 외 처리
        else if (message.startsWith("Message:")){
        	String chatMessage = message.substring(8);
        	lobby.getView().allChat(chatMessage);
        }
        // 
        else if (message.startsWith("Inspect:")) {
        	String result = message.substring(8);
        	if (result.equals("1"))
        		lobby.getView().allChat("======================조사결과, 마피아입니다======================");
        	else if (result.equals("0")) 
        		lobby.getView().allChat("======================조사결과, 마피아가 아닙니다======================");
        }
        else if (message.startsWith("System:")) {
            String sysMsg = message.substring(7);
            lobby.getView().allChat("======================" + sysMsg+ "======================"); // 채팅창에 출력
        }
        else if (message.startsWith("Vote:")){
        	lobby.getView().setSkillButton("vote");
        }
        else if (message.startsWith("Mafia_message:")) {
            String chat = message.substring(14); 
            
            // 마피아 창이 켜져있으면 채팅 추가
            if (mafiaChatView != null) {
                mafiaChatView.mafiaChat(chat);
            }
        }
    }

    // 연결 종료 시 처리
    public void onDisconnected() {
        System.out.println("서버와의 연결이 종료되었습니다.");
    }

}