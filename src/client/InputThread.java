package client;

import java.io.BufferedReader;
import java.io.IOException;

public class InputThread extends Thread {

    private final BufferedReader in;
    private final ClientManager clientManager;

    public InputThread(BufferedReader in, ClientManager clientManager) {
        this.in = in;
        this.clientManager = clientManager;
    }

    @Override
    public void run() {
        String message;
        try {
            // 서버로부터 메시지가 올 때까지 대기하다가 읽음
            while ((message = in.readLine()) != null) {
                // 메시지를 읽자마자 Manager에게 전달
                clientManager.handleMessage(message);
            }
        } catch (IOException e) {
            System.out.println("수신 스레드 종료 (연결 끊김)");
        }
    }
}