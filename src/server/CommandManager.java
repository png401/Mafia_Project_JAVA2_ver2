package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.사회자;


public class CommandManager {

    private Map<String, ICommand> commandMap;
    private List<ServerThread> allClients = Collections.synchronizedList(new ArrayList<>()); // 여러 스레드가 동시에 접근하므로 동기화된 리스트 사용
    private 사회자 logicBrain;

    public CommandManager(사회자 logicBrain) {
        this.logicBrain = logicBrain;

        this.commandMap = new HashMap<>();
        this.commandMap.put("Start", null);
        this.commandMap.put("Join", new JoinCommand(this, logicBrain));
        this.commandMap.put("Message", new AllChatCommand(this, logicBrain));
        this.commandMap.put("Target", new TargetCommand(this, logicBrain));
        this.commandMap.put("Mafia_message", new MafiaChatCommand(this, logicBrain));
    }

    public synchronized void handleNewConnection(ServerThread clientThread) {
        allClients.add(clientThread);
        System.out.println("[CommandManager] 새 연결. 총 " + allClients.size() + "명");
    }

    public synchronized void handleDisconnect(ServerThread clientThread) {
        allClients.remove(clientThread);
        //퇴장 알림 nickname 없이 쓰는 걸로 고쳐야함
        //System.out.println("[CommandManager] 연결 해제: " + clientThread.getPlayer().nickname + ". 남은 인원 " + allClients.size() + "명");
        //broadcastAll(clientThread.getPlayer().nickname + "님이 퇴장했습니다."); // 모든 유저에게 퇴장 알림
    }

    // 클라이언트가 보내는 메세지 처리 메소드
    public synchronized void processMessage(ServerThread sender, String rawMessage) {
        if (rawMessage == null || rawMessage.equals("")) return;
        System.out.println("[CommandManager] 메시지 받음: " + rawMessage);

        String[] tokens = rawMessage.split(":", 2);
        String commandKey = tokens[0]; // "Start", "Join", "Message", "Mafia_message" 중 하나
        String payload; // 유저가 보낸 메세지(실제 내용)
        
        if (tokens.length > 1) {
            payload = tokens[1];
        } else {
            payload = "";
        }

        ICommand command = commandMap.get(commandKey);

        if (command != null) {
            command.execute(sender, payload, logicBrain.getState());
        } else {
            // Start 명령어일 시 모든 클라이언트에게 게임이 시작되었다고 알려주기.
        	broadcastAll("Start:");
            logicBrain.start();
        }
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
                /*if (client.getPlayer().getRole().equals("MAFIA")) {
                    client.sendMessage(message);
                }*/
            	client.sendMessage(message);
            }
        }
    }
}
