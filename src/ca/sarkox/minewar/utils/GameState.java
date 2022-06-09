package ca.sarkox.minewar.utils;

public enum GameState {
	
	LOBBY, STUFFING, FIGHTING, FINISH;

	private static GameState currentState;

	public static void setState(GameState state) {
		currentState = state;
	}

	public static boolean isState(GameState state) {
		return currentState == state;
	}

	public static GameState getState() {
		return currentState;
	}
}