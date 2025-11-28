package client;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class OutputThread extends Thread {

    private final PrintWriter out;
    private final BlockingQueue<String> queue;

    public OutputThread(PrintWriter out) {
        this.out = out;
        // 문제 3 해결: 큐 사이즈 제한 (예: 1000개)
        this.queue = new LinkedBlockingQueue<>(1000);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String message = queue.take();

                out.println(message);

                // 문제 2 해결: 강제 플러시
                out.flush();

                // 문제 1 해결: 에러 체크 후 루프 탈출
                if (out.checkError()) {
                    System.err.println("Network error detected. Output thread stopping.");
                    throw new InterruptedException("Stream closed");
                }
            }
        } catch (InterruptedException e) {
            // 스레드 종료 신호 수신
            Thread.currentThread().interrupt();
        } finally {
            // 문제 5 해결: 종료 시 남은 메시지 처리 (선택적)
            cleanupRemainingMessages();
        }
    }

    // 남은 메시지를 보내거나 로그에 남기는 로직
    private void cleanupRemainingMessages() {
        while (!queue.isEmpty()) {
            String msg = queue.poll();
            if (msg != null && !out.checkError()) {
                out.println(msg);
                out.flush();
            }
        }
    }

    // 문제 4 해결: 실패 시 호출자에게 알림 (boolean 반환)
    public boolean sendMessage(String message) {
        try {
            // 큐가 꽉 찼을 때 무한정 대기하지 않고 2초 후 실패 처리 (Backpressure)
            return queue.offer(message, 2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 상태 복구
            return false;
        }
    }
}