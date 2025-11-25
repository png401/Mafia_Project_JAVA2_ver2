import client.ClientManager;
import controller.사회자;
import view.Lobby;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ClientManager clientManager = new ClientManager();
		
		Lobby lobby = new Lobby(clientManager);
		clientManager.setLobby(lobby);
		lobby.setVisible(true);
		
		//ClientManager를 여기서 만들자
	}

}
