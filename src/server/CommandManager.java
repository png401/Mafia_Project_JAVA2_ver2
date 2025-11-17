package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.사회자;


// -- 클라이언트에서 메세지를 보낼 때 join, message, mafia_message 이런걸 구분해서 보내줘야 할 것 같습니당. --


// 주 역할: 모든 게임 로직, 상태, 클라이언트 목록 관리
public class CommandManager {
	
	private Map<String, ICommand> commandMap; // Command 패턴을 위한 필드
	private List<ServerThread> allClients = Collections.synchronizedList(new ArrayList<>()); // 여러 스레드가 동시에 접근하므로 동기화된 리스트 사용
	private 사회자 logicBrain;
	
	public CommandManager(사회자 logicBrain) {
		this.logicBrain = logicBrain;
		
		// 커맨드 패턴 적용을 위한 커멘드 초기화
		this.commandMap = new HashMap<>();
		this.commandMap.put("join", new JoinCommand(this, logicBrain));
        this.commandMap.put("message", new AllChatCommand(this, logicBrain));
        this.commandMap.put("mafia_message", new MafiaChatCommand(this, logicBrain));
	}
	
	// TODO 이것도 수정해야 함. 닉네임을 누가 부여할 것인지 결정x
    public synchronized void handleNewConnection(ServerThread clientThread) {
        allClients.add(clientThread);
        System.out.println("[CommandManager] 새 연결. 총 " + allClients.size() + "명");
        // 수정 가능. 일단은 사용자가 직접 join:닉네임을 쳐야 닉네임이 저장되는 형식임.(해당 클라이언트에게만 보이는 메세지)
        clientThread.sendMessage("서버에 접속했습니다. 게임에서 사용하실 닉네임을 입력해주세요: ");
    }
	
    public synchronized void handleDisconnect(ServerThread clientThread) {
        allClients.remove(clientThread);
        System.out.println("[CommandManager] 연결 해제: " + clientThread.getPlayer().nickname + ". 남은 인원 " + allClients.size() + "명");
        broadcastAll(clientThread.getPlayer().nickname + "님이 퇴장했습니다."); // 모든 유저에게 퇴장 알림
    }

	// ServerThread가 이 메소드를 호출하면 command 패턴을 적용하여 처리
	public synchronized void processMessage(ServerThread sender, String rawMessage) {
	    if(rawMessage == null || rawMessage.equals("")) return;
	    System.out.println("[CommandManager] 메시지 받음: " + rawMessage);
		
	    String[] tokens = rawMessage.split(":", 2);
	    String commandKey = tokens[0]; // "join", "message", "mafia_message" 중 하나
	    String payload;
	    if(tokens.length > 1) {
	    	payload = tokens[1]; // 유저가 보낸 메세지(실제 내용)
	    } else {
	    	payload="";
	    }
	    
	    // 커맨드 객체에게 실행을 위임
	    ICommand command = commandMap.get(commandKey);
	    if (command != null) {
	        command.execute(sender, payload, logicBrain.getState()); // set_state()는 있는데 getState()는 없더라고용..
	    } else {
	    	// message, mafia_message가 아닌 명령어가 왔을 시
	    	sender.sendMessage("알 수 없는 명령어입니다: " + commandKey);
	    }
	}
	
	public void commandMessage() {
		
	}
	
	public void broadcastAll(String message) {
	    // 동기화 리스트긴한데 혹시 반복 중에 캐릭터가 제거되거나 하면 에러나니까 동기화 블록으로 묶었습니다.
	    synchronized (allClients) {
	        for (ServerThread client : allClients) {
	            client.sendMessage(message);
	        }
	    }
	}
	
	public void broadcastToMafia(String message) {
	     synchronized (allClients) {
	         for (ServerThread client : allClients) {
	        	 // 여기도 똑같이 플레이어의 역할을 가져와야 해서..
	             if (client.getPlayer().getRole().equals("MAFIA")) {
	                 client.sendMessage(message);
	             }
	         }
	     }
	}
	
	
}
