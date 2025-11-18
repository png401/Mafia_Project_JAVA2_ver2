package controller;

public interface IState {
	public void limitTime(int time);

	void execute(사회자 manager);
}