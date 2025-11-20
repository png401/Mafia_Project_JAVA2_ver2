package client;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class OutputThread extends Thread {

    private final PrintWriter out;
    private final BlockingQueue<String> queue; // 메시지 전송 대기열

    public OutputThread(PrintWriter out) {
        this.out = out;
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // 큐에서 메시지를 꺼내 서버로 전송 (큐가 비면 대기)
                String message = queue.take();
                out.println(message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // 외부에서 호출하여 메시지를 큐에 넣음
    public void sendMessage(String message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}