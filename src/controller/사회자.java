package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Player;

public class 사회자 {
	//사회자 객체 하나만 있어야 되니까 싱글톤 생성해봤음
	private static 사회자 매니저;
	
	IState gameState = null;
	RoleFactory roleFactory = new RoleFactory();
	
	public List <Player> players = new ArrayList<>();
	public Map<Integer, Player> playersById = new HashMap<>();
	public Map<String, Player> playersByNickname = new HashMap<>();
	public List <Player> ghosts = new ArrayList<>();
	
	public int dayCount=0;
	
	private int killedID=0;
	private int healedID=0;
	
	public static 사회자 getInstance() {
		if(매니저 == null) {
			매니저 = new 사회자();
		}
		return 매니저;
	}

	public void setKilledID(int killedID) {
		this.killedID = killedID;
	}

	public void setHealedID(int healedID) {
		this.healedID = healedID;
	}

	private 사회자() {
		매니저 = this;
		roleFactory = new RoleFactory();
	}
	
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
	
	// 수정중~
	public void start() {
		init_game();
		Scanner sc = new Scanner(System.in);
		
//        while(true) {
//        	set_state(new 밤());//처음 상태: 밤
//        	this.gameState.execute(매니저);
//        	checkEnd();
//        	
//        	set_state(new 토론());
//        	this.gameState.execute(매니저);
//        	//Server.execute(Player player);
//        	
//        	set_state(new 투표());
//        	this.gameState.execute(매니저);
//        	checkEnd();
//        }
        
	}
	
	public IState getState() {
		return gameState;
	}
	
}

	
