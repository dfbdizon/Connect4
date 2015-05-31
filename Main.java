public class Main{
	public static Connect4 gameLogic;
	public static UI gameUI;
	public static void main(String [] args){
		gameLogic = new Connect4();
		gameUI = new UI("faye", gameLogic.getBoard(), gameLogic);

		gameLogic.setUI(gameUI);
	}
}