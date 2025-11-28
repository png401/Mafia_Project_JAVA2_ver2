package model;

import server.ServerThread;

public class Player {
	private String nickname;
	private int id;
	private String role;
	private boolean is_alive = true;
	private ISkill skill = null;
	private String skillName = null;
	
	private ServerThread serverThread;
	
	//이 플레이어가 밤에 지목한 타겟id
	private volatile int nightTargetId;

	public Player(String nickname, int id) {
		// TODO Auto-generated constructor stub
		this.nickname = nickname;
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public String getSkillName() {
		return skillName;
	}
	
	public void setSkillName(String skillName) {
		this.skillName = skillName;
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

	public boolean getIs_alive() {
		return is_alive;
	}

	public String getNickname() {
		return nickname;
	}

	public void setIs_alive(boolean is_alive) {
		this.is_alive = is_alive;
	}

	public int getId() {
		return id;
	}

	public ISkill getSkill() {
		return skill;
	}

	public void setSkill(ISkill skill) {
		this.skill = skill;
	}

	
}
