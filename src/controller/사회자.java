package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Player;
import server.CommandManager;
import server.ServerThread;

public class 사회자 {
	//사회자 객체 하나만 있어야 되니까 싱글톤 생성해봤음
	private static 사회자 매니저;
	private 사회자() {};
	
	IState gameState = null;
	RoleFactory roleFactory = new RoleFactory();
	
	public List <Player> players = new ArrayList<>();
	public Map<Integer, Player> playersById = new HashMap<>();
	public Map<String, Player> playersByNickname = new HashMap<>();
	public List <Player> ghosts = new ArrayList<>();

    //통신 매니저 저장소
    private CommandManager commandManager;

	public int dayCount=0;
	
	private int killedID=0;
	
	public List<Integer> voteResult = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0));

	public void setKilledID(int killedID) {
		this.killedID = killedID;
	}
	
	public static synchronized 사회자 getInstance() {
		if(매니저 == null) {
			매니저 = new 사회자();
		}
		return 매니저;
	}

    // Setter: ServerManager에서 호출
    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    // Getter: 밤.java 등에서 호출
    public CommandManager getCommandManager() {
        return this.commandManager;
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
	
	public IState getState() {
		// TODO Auto-generated method stub
		return this.gameState;
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
		
		for(Player player : players) {
			ServerThread thread = player.getServerThread();
			thread.sendMessage("Role:"+player.getRole());
			thread.sendMessage("Skill:"+player.getSkillName());
			thread.sendMessage("Players:"+playersMessage());
		}
		
	}
	
	public String playersMessage() {
		String playerList = "";
		for (Player player : players) {
			playerList = playerList + player.id +" " + player.nickname + ";";
		}
		return playerList;
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
			
			if("mafia".equals(p.getRole())) 생존마피아++;
			else 생존시민++;
		}
		
		if(생존마피아==0) {
			commandManager.broadcastAll("System:"+"마피아가 모두 검거됐습니다. 시민 승리!");
			System.exit(0);
		}
		
		if(생존마피아>=생존시민) {
			commandManager.broadcastAll("System:"+"마피아와 시민의 수가 같아졌습니다. 마피아 승리!");
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
	
	public void getNight() {
		
	}
	
	public void start() {
		init_game();
		
        while(true) {
        	this.set_state(new 밤());
        	this.gameState.execute(매니저);
        	checkEnd();      	
        	
        	this.set_state(new 토론());
        	this.gameState.execute(매니저);
        	//Server.execute(Player player);
        	
        	this.set_state(new 투표());
        	this.gameState.execute(매니저);
        	checkEnd();
        	
        	dayCount++;
        }
	}

}

	
