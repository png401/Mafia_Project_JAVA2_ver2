package controller;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import model.Heal;
import model.ISkill;
import model.Inspect;
import model.Kill;
import model.Player;

public class RoleFactory {
	
	//아니.. 중복 없게 id 부여해야 하는데.....
	public Player createPlayer(String nickname, int id) {
		Player player = new Player(nickname, id);
		
		String role;
		ISkill skill = null;
		
		if(id==1) {
			role = "마피아";
			skill = new Kill();
		}
		
		else if(id==2) {
			role = "의사";
			skill = new Heal();
		}
		
		else if(id==3) {
			role = "경찰";
			skill = new Inspect();
		}
		
		else {
			role = "시민";
			skill = null;
		}
		
		player.setRole(role);
		player.skill = skill;
		
		return player;
	}
	
}
