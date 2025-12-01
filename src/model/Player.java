package model;

import server.ServerThread;

public class Player {
	private String nickname;
	private int id;
	private String role;
	private boolean is_alive = true;
	private ISkill skill = null;
	private String skillName = null;
	
	//transient가 뭘까 지워도 되는걸까
	private ServerThread serverThread;
	
	//이 플레이어가 밤에 지목한 타겟id
	private volatile int nightTargetId=-1;
	//private volatile int voteTargetId;

	public Player(String nickname, int id) {
		// TODO Auto-generated constructor stub
		this.setNickname(nickname);
		this.setId(id);
	}

	public String getRole() {
		return role;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getNightTargetId() {
		return nightTargetId;
	}

	public void setNightTargetId(int nightTargetId) {
		this.nightTargetId = nightTargetId;
	}

	public ServerThread getServerThread() {
		// TODO Auto-generated method stub
		return serverThread;
	}
	
	public void setServerThread(ServerThread serverThread) {
        this.serverThread = serverThread;
    }

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getIs_alive() {
		return is_alive;
	}

	public void setIs_alive(boolean is_alive) {
		this.is_alive = is_alive;
	}

	public ISkill getSkill() {
		return skill;
	}

	public void setSkill(ISkill skill) {
		this.skill = skill;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	
}
