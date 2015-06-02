import java.awt.event.WindowEvent;
public class Main{
	public static Connect4 gameLogic;
	public static UI gameUI;
	public static int firstPlayerWin = 0;
	public static int firstPlayerLose = 0;
	public static int draws = 0;
	public static int numberOfPlays = 0;

	public static void main(String [] args){
		play();
	}

	public static void play(){
		numberOfPlays++;
		System.out.println("Game #" + numberOfPlays);
		System.out.println("First Player Wins: " + firstPlayerWin);
		System.out.println("First Player Lose: " + firstPlayerLose);
		System.out.println("Draws: " + draws);
		gameLogic = new Connect4();
		gameUI = new UI("faye", gameLogic.getBoard(), gameLogic);

		gameLogic.setUI(gameUI);
	}

	public static void playAgain(int winner){
		if(winner == 1) firstPlayerWin++;
		else if(winner == 2) firstPlayerLose++;
		else if(winner == 3)draws++;

		if(numberOfPlays < 10) play();
		else{
			System.out.println("First Player Wins: " + firstPlayerWin);
			System.out.println("First Player Lose: " + firstPlayerLose);
			System.out.println("Draws: " + draws);
		}
	}
}