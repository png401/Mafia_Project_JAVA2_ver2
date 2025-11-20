package client;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {

    private final String host;
    private final int port;
    private final ClientManager clientManager;

    private Socket socket;
    private InputThread inputThread;
    private OutputThread outputThread;

    public ClientThread(String host, int port, ClientManager clientManager) throws IOException {
        this.host = host;
        this.port = port;
        this.clientManager = clientManager;
    }

    @Override
    public void run() {
        try {
            // 1. 소켓 연결
            socket = new Socket(host, port);
            System.out.println("서버(" + host + ":" + port + ")에 접속되었습니다.");

            // 2. 스트림 생성 (한글 처리를 위해 UTF-8 인코딩 지정)
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);

            // 3. 송수신 스레드 시작
            outputThread = new OutputThread(out);
            inputThread = new InputThread(in, clientManager);

            outputThread.start();
            inputThread.start();

            // 4. 수신 스레드가 끝날 때까지 대기 (연결 유지)
            inputThread.join();

        } catch (Exception e) {
            System.err.println("네트워크 오류 발생: " + e.getMessage());
        } finally {
            close();
            clientManager.onDisconnected();
        }
    }

    public void sendMessage(String message) {
        if (outputThread != null) {
            outputThread.sendMessage(message);
        }
    }

    private void close() {
        try {
            if (inputThread != null) inputThread.interrupt();
            if (outputThread != null) outputThread.interrupt();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}