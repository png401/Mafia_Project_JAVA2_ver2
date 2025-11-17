package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import model.Player;

// 주 역할: 담당 클라이언트의 말을 듣고 networkBrain에게 전달
// networkBrain가 메세지 보내라고 시키면 담당 클라이언트에게 채팅 보냄
public class ServerThread extends Thread {
	private Player player; // 각자 본인의 플레이어는 가지고 있게 바꿨습니다.
    private Socket socket;
    private CommandManager networkBrain;
    private PrintWriter pw;

    public ServerThread(Socket socket, CommandManager cm) {
        this.socket = socket;
        this.networkBrain = cm;
    }
    
    // 서버가 플레이어의 역할, 생사여부 등을 확인할 때 사용하기 위해 만들었습니다.
    public void setPlayer(Player player) { // 사회자한테 받은 플레이어 등록.
    	this.player=player;
    }
    public Player getPlayer() {
    	return this.player;
    }

    @Override
    public void run() {
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            this.pw = pw;

            // networkBrain에게 새로운 클라이언트(나) 연결된 거 알리기
            networkBrain.handleNewConnection(this);

            String request="";
            // 클라이언트 연결이 끊어지면 readLine()이 null 반환
            while ((request = bf.readLine()) != null) {
            	networkBrain.processMessage(this, request); // 받은 메세지를 networkBrain에게 전달
            }
        } catch (IOException e) {
        	log("클라이언트 연결 오류");
        } finally {
        	networkBrain.handleDisconnect(this); // 클라이언트 연결 해제 시 networkBrain에게 알리기
        }
    }

    // networkBrain가 메세지 보내라 하면 각자의 소켓으로 메세지를 보냄
    public void sendMessage(String message) {
        if (pw != null) {
            pw.println(message);
        } else {
            log("PrintWriter 초기화 안 됨");
        }
    }
    
    // networkBrain가 사용
    // nickname 관련 메소드들 삭제.

    private void log(String log) {
        System.out.println("[Thread " + this.threadId() + "] " + log);
    }
}