package client;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//ClientManager로부터 메세지 전송 요청을 받고 서버로 메세지 전송
public class OutputThread extends Thread {

    private final PrintWriter out;
    private final BlockingQueue<String> messageQueue;

    public OutputThread(PrintWriter out) {
        this.out = out;
        this.messageQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void run() {
        try {
            // 스레드가 중단(interrupt) 신호를 받기 전까지 계속 실행
            while (!Thread.currentThread().isInterrupted()) {
                // 큐에 메시지가 들어올 때까지 대기 (Blocking)
                String message = messageQueue.take();
                
                // 메시지를 서버로 전송, 뷰에서 출력
                out.println(message);
            }
        } catch (InterruptedException e) {
            // 스레드가 중단되면(ex. 연결 종료) InterruptedException 발생
            System.out.println("OutputThread 종료 (Interrupted).");
            Thread.currentThread().interrupt(); // 인터럽트 상태 복원
        }
    }

    //전송할 메세지를 큐에 추가 (ClientManager 호출)
    public void sendMessage(String message) {
        try {
            // 큐에 메시지를 넣는데 큐가 가득 차면 대기
            messageQueue.put(message);
        } catch (InterruptedException e) {
            System.err.println("메시지 큐 추가 중 오류: " + e.getMessage());
            Thread.currentThread().interrupt(); // 인터럽트 상태 복원
        }
    }
}