package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Player;
import view.Lobby;
import view.View;

public class 사회자 {
	//사회자 객체 하나만 있어야 되니까 싱글톤 생성해봤음
	private static 사회자 매니저;
	
	IState gameState = null;
	RoleFactory roleFactory = new RoleFactory();
	
	public List<Lobby> lobbyList;
	
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

	public 사회자() {
		매니저 = this;
	}
	
	public void setLobby(Lobby newLobby) {
		this.lobbyList.add(newLobby); 
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

	public List<Player> getPlayers() {
		return players;
	}

	public void set_state(IState state) {
		this.gameState = state;
		if(state!=null) {
		System.out.println("=========="+this.gameState.getClass().getSimpleName()+"==========");
		}
	}
		
	public Player createNewPlayer(String nickname) {
		Player newPlayer = roleFactory.createPlayer(nickname, players.size()+1);
		addPlayer(newPlayer);
		return newPlayer;
	}
	
	//Start 받으면
	public void init_game() {
		System.out.println("=====마피아 게임 시작=====");		
			
		roleFactory.randomRole(players);
		for (Player player : players) {
			for (Lobby lobby : lobbyList) {
				if(lobby.getClientManager().getMyName() == player.nickname) {
					lobby.getView().setRoleView(player.getRole());
				}
				
			}
		}
		//각 클라이언트들에게 player 객체를 mapping 해줘야함
		
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
        	this.set_state(new 밤());
        	this.gameState.execute(매니저);
        	checkEnd();
        	
        	//밤의 결과를 날릴 부분
        	if(killedID == healedID) {
        		System.out.println("[결과] 의사가 보호. 아무도 안 죽음.");
        		return;
        	}
        	
        	Player target = 매니저.getPlayerById(killedID);
        	
        	if(target != null && target.is_alive) {
        		target.is_alive = false;
        		매니저.ghosts.add(target);
        		System.out.println("[결과] "+target.nickname+"이 사망함.");
        	}
        	
        	this.set_state(new 토론());
        	this.gameState.execute(매니저);
        	//Server.execute(Player player);
        	
        	this.set_state(new 투표());
        	this.gameState.execute(매니저);
        	checkEnd();
        }
	}

	public IState getState() {
		// TODO Auto-generated method stub
		return null;
	}

}

	
