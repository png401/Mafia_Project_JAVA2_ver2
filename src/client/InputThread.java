package client;

import java.io.BufferedReader;
import java.io.IOException;

//서버로부터 메세지를 기다리고 읽는 역할
public class InputThread extends Thread {
    
    private final BufferedReader in;
    private final ClientManager clientManager;

    public InputThread(BufferedReader in, ClientManager clientManager) {
        this.in = in;
        this.clientManager = clientManager;
    }

    @Override
    public void run() {
        String messageFromServer;
        try {
            // 서버로부터 메시지를 계속 읽는데 서버가 연결을 끊거나 스트림이 닫히면 readLine()은 null을 반환함
            while (!Thread.currentThread().isInterrupted() && (messageFromServer = in.readLine()) != null) {
                // 읽은 메시지를 ClientManager에게 전달하여 처리
                clientManager.handleMessage(messageFromServer);
            }
        } catch (IOException e) {
            // 소켓이 닫히거나 네트워크 오류 밝생 시
            if (!"Socket closed".equals(e.getMessage()) && !"Stream closed".equals(e.getMessage())) {
                System.err.println("메시지 수신 중 오류 발생: " + e.getMessage());
            }
        } finally {
            // 서버와 연결이 끊기면 ClientManager에게 알려줘야 함!!
            System.out.println("InputThread 종료.");
        }
    }
}