package client;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * ClientThread (네트워크 계층)
 * - 실제 서버와 Socket 연결을 생성하고 관리하는 주체입니다.
 * - 연결이 성공하면, 메시지를 읽는 InputThread와 메시지를 쓰는 OutputThread를 생성하고 시작시킵니다.
 * - ClientManager로부터의 메시지 전송 요청을 OutputThread에 전달합니다.
 */
public class ClientThread extends Thread {

    private final String host;
    private final int port;
    private final ClientManager clientManager;

    private Socket socket;
    private BufferedReader in;  // 서버로부터 읽기
    private PrintWriter out;    // 서버로 쓰기

    private InputThread inputThread;
    private OutputThread outputThread;

    /**
     * ClientThread 생성자
     * @param host 서버 주소
     * @param port 서버 포트
     * @param clientManager 상위 매니저
     * @throws IOException 소켓 생성 실패 시
     */
    public ClientThread(String host, int port, ClientManager clientManager) throws IOException {
        this.host = host;
        this.port = port;
        this.clientManager = clientManager;
    }

    @Override
    public void run() {
        try {
            // 1. 서버에 소켓 연결
            this.socket = new Socket(host, port);

            // 2. 입출력 스트림 생성 :: 서버 -> 클라이언트 (Input)
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            // 클라이언트 -> 서버 (Output)
            this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);

            // 3. Input/Output 스레드 생성 및 시작
            this.outputThread = new OutputThread(out);
            this.inputThread = new InputThread(in, clientManager);
            
            this.outputThread.start();
            this.inputThread.start();

            // TODO: View에 "서버에 성공적으로 연결되었습니다." 메시지 전달
            // clientManager.handleMessage("NOTICE:서버에 연결되었습니다.");

            // InputThread가 종료될 때까지 (즉, 서버 연결이 끊길 때까지)
            // ClientThread는 살아있어야 함.
            inputThread.join();

        } catch (UnknownHostException e) {
            // TODO: View에 "알 수 없는 호스트입니다." 오류 전달
            // clientManager.handleMessage("ERROR:알 수 없는 호스트입니다. " + host);
            System.err.println("알 수 없는 호스트: " + e.getMessage());
        } catch (IOException e) {
            // TODO: View에 "서버 I/O 오류" 전달
            // clientManager.handleMessage("ERROR:서버와 통신 중 오류가 발생했습니다.");
            System.err.println("I/O 오류: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("스레드 대기 중 오류: " + e.getMessage());
            Thread.currentThread().interrupt(); // 스레드 인터럽트 상태 복원
        } finally {
            // 4. 연결 종료 및 자원 정리
            close();
            // 5. 서버? 사회자?에게 연결 종료 알림
            clientManager.onDisconnected();
        }
    }

    /**
     * ClientManager로부터 메시지 전송 요청을 받아 OutputThread에 전달합니다.
     * @param message 서버로 보낼 메시지
     */
    public void sendMessage(String message) {
        if (outputThread != null) {
            outputThread.sendMessage(message);
        }
    }

    /**
     * 모든 자원 (소켓, 스트림 등)을 닫습니다.
     */
    private void close() {
        try {
            // Input/Output 스레드 중지 (InterruptedException 유발)
            if (inputThread != null) inputThread.interrupt();
            if (outputThread != null) outputThread.interrupt();

            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("자원 해제 중 오류 발생: " + e.getMessage());
        }
    }
}