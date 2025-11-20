package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Player;

public class 사회자 {
	
	IState gameState = null;
	RoleFactory roleFactory = new RoleFactory();
	
	public List <Player> players = new ArrayList<>();
	public Map<Integer, Player> playersById = new HashMap<>();
	public Map<String, Player> playersByNickname = new HashMap<>();
	public List <Player> ghosts = new ArrayList<>();
	
	public int dayCount=0;
	
	public void addPlayer(Player p) {
		players.add(p);
		playersById.put(p.id, p);
		playersByNickname.put(p.nickname, p);
	}
	
	public Player getPlayerById(int id) {
		return playersById.get(id);
	}
	
	public Player getPlayerByNickname(String nickname) {
		return playersByNickname.get(nickname);
	}
	
	public void set_state(IState state) {
		this.gameState = state;
		if(state!=null) {
		System.out.println("=========="+this.gameState.getClass().getSimpleName()+"==========");
		}
	}
	
	public void init_game() {
		Scanner sc = new Scanner(System.in);
		System.out.println("=====마피아 게임 시작=====");
		System.out.println("플레이어 수 입력: ");
		int n = sc.nextInt();
		sc.nextLine();
		
		for(int i=0; i<n; i++) {
			System.out.println((i+1)+"번 플레이어 닉네임: ");
			String nickname = sc.nextLine();
			
			Player p = new Player(nickname, i+1);
			addPlayer(p);
		}
		
		roleFactory.randomRole(players);
	}
	
	public void notifyAll(String message) {
		// 나중에 소켓 브로드캐스트로 대체
        System.out.println("[공지] " + message);
	}
	
	public void checkEnd() {
		int 생존마피아 = 0;
		int 생존시민 = 0;
		
		for(Player p : players) {
			if(p.is_alive == false) continue;//죽은 애 쓰루
			
			if("마피아".equals(p.getRole())) 생존마피아++;
			else 생존시민++;
		}
		
		if(생존마피아==0) {
			notifyAll("마피아가 모두 검거됐습니다. 시민 승리!");
			System.exit(0);
		}
		
		if(생존마피아>=생존시민) {
			notifyAll("마피아와 시민의 수가 같아졌습니다. 마피아 승리!");
			System.exit(0);
		}
	}

	public Player player_ID(int id) {
		for(Player p : players) {
			if(p.id == id) {
				return p;
			}
		}
		return null;
	}
	
	public void start() {
		init_game();
		Scanner sc = new Scanner(System.in);
		
        while(true) {
        	set_state(new 밤());
        	gameState.execute(this);
        	checkEnd();
        	
        	set_state(new 토론());
        	gameState.execute(this);
        	//Server.execute(Player player);
        	
        	set_state(new 투표());
        	gameState.execute(this);
        	checkEnd();
        }
	}
	
}

	
