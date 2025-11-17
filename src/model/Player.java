package model;

public class Player {
	public String nickname;
	public int id;
	private String role;
	public boolean is_alive = true;
	public ISkill skill = null;
	
	public Player(String nickname, int id) {
		// TODO Auto-generated constructor stub
		this.nickname = nickname;
		this.id = id;
	}

	public String getPlayer(int ID) {
		return nickname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
