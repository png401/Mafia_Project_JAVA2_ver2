package server;

public class StartCommand{ //ICommand를 implements하기에는 state, sender, 사회자가 필요 없음 
	private CommandManager networkBrain;
	
	public StartCommand(CommandManager cm) {
		this.networkBrain = cm;
	}
	
	public void execute(String payload) {
		networkBrain.broadcastAll(payload);
	}
	
}
	
