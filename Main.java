import java.awt.event.WindowEvent;
public class Main{
	public static Connect4 gameLogic1;
	public static Connect4Test gameLogic2;
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
		gameLogic1 = new Connect4();
		gameLogic2 = new Connect4Test();
		gameUI = new UI("faye", gameLogic1.getBoard(), gameLogic1, gameLogic2);

		gameLogic1.setUI(gameUI);
		gameLogic2.setUI(gameUI);
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