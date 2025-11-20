package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import model.Player;

public class ServerThread extends Thread {
	//private Player player;
    private Socket socket;
    private CommandManager networkBrain;
    private PrintWriter pw;

    public ServerThread(Socket socket, CommandManager cm) {
        this.socket = socket;
        this.networkBrain = cm;
    }
    
    /*
    // 서버가 플레이어의 역할, 생사여부 등을 확인할 때 사용하기 위해 만들었습니다.
    public void setPlayer(Player player) { // 사회자한테 받은 플레이어 등록.
    	this.player=player;
    }
    public Player getPlayer() {
    	return this.player;
    }*/

    @Override
    public void run() {
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            this.pw = pw;

            // 새로운 플레이어의 스레드를 등록
            networkBrain.handleNewConnection(this);

            String request="";
            while ((request = bf.readLine()) != null) {
            	networkBrain.processMessage(this, request);
            }
        } catch (IOException e) {
        	log("클라이언트 연결 오류");
        } finally {
        	networkBrain.handleDisconnect(this); // 클라이언트 연결 해제 시 리스트에서 제거
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

    private void log(String log) {
        System.out.println("[Thread " + this.getId() + "] " + log);
    }
}