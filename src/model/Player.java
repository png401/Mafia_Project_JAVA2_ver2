package model;

public class Player {
	public String nickname;
	public int id;
	private String role;
	public boolean is_alive = true;
	public ISkill skill = null;
	
	//이 플레이어가 밤에 지목한 타겟id
	private int nightTargetId;

	public Player(String nickname, int id) {
		// TODO Auto-generated constructor stub
		this.nickname = nickname;
		this.id = id;
	}

	public String getRole() {
		return role;
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


	
}
