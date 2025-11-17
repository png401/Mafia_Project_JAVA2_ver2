package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import controller.사회자;

// 주 역할: 서버 소켓 하나 열고, GameManager를 생성
// 클라이언트가 접속하면 ServerThread를 생성해서 GameManager와 연결시켜주기
public class ServerManager {
	public static final int PORT = 50023;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		
		// 두 개의 브레인을 가짐
		사회자 logicBrain = 사회자.getInstance(); // 일단 new로 객체 생성했음 -> 싱글톤으로 변경 시 수정할 예정
		CommandManager networkBrain = new CommandManager(logicBrain);
		
		try {
			serverSocket = new ServerSocket();

			String hostAddress = InetAddress.getLocalHost().getHostAddress(); // 이건 서버의 주소
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			consoleLog(hostAddress + ":" + PORT); // 서버 주소,포트번호 출력

			// 여러 클라이언트의 요청을 수락하고 writer를 연결
			while (true) {
				Socket socket = serverSocket.accept(); // 여기서 클라이언트를 받고
				new ServerThread(socket, networkBrain).start(); // 클라이언트를 위한 전용 소켓 생성 후 gameManaer 주입
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void consoleLog(String log) {
		System.out.println("[server " + Thread.currentThread().threadId() + "] " + log);
	}
}
