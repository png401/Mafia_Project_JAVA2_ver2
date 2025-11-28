package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.management.relation.Role;

import model.Heal;
import model.ISkill;
import model.Inspect;
import model.Kill;
import model.Player;
import server.ServerThread;

public class RoleFactory {
	
	//아니.. 중복 없게 id 부여해야 하는데.....
	public Player createPlayer(String nickname, int id) {
		Player player = new Player(nickname, id);
		return player;
	}
	
	public void randomRole(List <Player> players) {
		/*인원 수에 따라 의사, 경찰 1로 고정, 마피아는 4-1, 5-2, 6-2
		 * 4명:마피아1,시민1,의사1,경찰1, 
		 * 5명:마피아2,시민1,의사1,경찰1, 
		 * 6명:마피아2,시민2,의사1,경찰1
		 */
		
		List <String> roles = new ArrayList<>();
		int n = players.size();

		//n나누기3 반올림하면 마피아수
		int numMafia = (int)Math.round(n/3.0);

		for(int i=0; i<numMafia; i++) {
			roles.add("mafia");			
		}

		roles.add("doctor");
		roles.add("police");

		//나머지 시민
		int numCitizen = n - roles.size();
		for(int i=0; i<numCitizen; i++) {
			roles.add("citizen");
		}

		//직업 랜덤 섞기
		//역할 리스트 roles를 완전히 뒤섞음. 피셔-예이츠 알고리즘?
		Collections.shuffle(roles);

		//플레이어들에게 역할 배정 스킬 배정
		for(int i=0; i<n; i++) {
			Player p = players.get(i);
			String role = roles.get(i);//섞인 역할 배분
			p.setRole(role);

			switch(role) {
			case "mafia":
				p.setSkill(new Kill());
				p.setSkillName("kill");
				break;
			case "doctor":
				p.setSkill(new Heal());
				p.setSkillName("heal");
				break;
			case "police":
				p.setSkill(new Inspect());
				p.setSkillName("Inspect");
				break;
			default:
				p.setSkill(null);
				p.setSkillName("null");
			}			
			
		}

	}

}
