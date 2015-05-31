public class Connect4{
	HashMap<Integer, String> board = new HashMap<Integer, String>(42);
	public static void main(String [] args){
		initializeBoard();



	}
	private static boolean hasWinner(int col){







	}
	private static void initializeBoard(){
		board.put(1, "000000");
		board.put(2, "000000");
		board.put(3, "000000");
		board.put(4, "000000");
		board.put(5, "000000");
		board.put(6, "000000");
		board.put(7, "000000");
	}


	// Heuristic 1?
	// Count number of possible 4 in a rows that each player can still make in the current state
	
	// Heuristic 2?
	// Score = My Material - Opponent's Material
	// Material = count of all contiguous lines of pieces that a player has, WEIGHTED, so 1 in a row, .1 points, etc.
	// Consider only ung may open positions pa


	private static int getStateScore(){

	}






}