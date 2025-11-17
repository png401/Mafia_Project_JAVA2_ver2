package server;

import controller.IState;

public interface ICommand {
	public void execute(ServerThread sender, String payload, IState currentState);
}
