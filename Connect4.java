import java.util.HashMap;

public class Connect4{
	private static HashMap<Integer, String> board = new HashMap<Integer, String>(7);
	private static boolean endGame = false;
	private static UI gameUI;

	public Connect4(){
		initializeBoard();
	}
	public static void addPiece(int column, int row, int player){
		String currentCol = board.get(column);
		String newCol;
		char token;
		if(player == 1) token = '1';
		else token = '2'; 

		newCol = currentCol.substring(0, row) + token;
		for(int i = row+1; i < 6; i++) newCol += '0'; 

		if(hasWinner(column, row, token)){
			endGame = true;
			if(player == 1) gameUI.updateStatus(4);
			else gameUI.updateStatus(3);
		}
		//else if (Check if draw)
	}
	private static boolean hasWinner(int col, int row, char player){
		if(checkHor(col, row, player) || checkVer(col, row, player) || checkDiagonalLeft(col, row, player) || checkDiagonalRight(col, row, player))
			return true;
		else return false;
	}
	/* Horizontal */
	private static boolean checkHor(int col, int row, char player){
		int connected = 1;
		/* To the left */
		if(col != 1){
			for(int i = col-1; i >= 1; i--){
				if(board.get(i).charAt(row) == player) connected++;
				else i = 1;		
			}
		}
		/* To the right */
		if(col != 7){
			for(int i = col + 1; i <= 7; i++){
				if(board.get(i).charAt(row) == player) connected++;
				else i = 7;
			}
		}
		if(connected == 4) return true;
		else return false;
	}
	/* Vertical */
	private static boolean checkVer(int col, int row, char player){
		int connected = 1;
		if(row >= 3){
			for(int i = row - 1; i >= 0; i--){
				if(board.get(col).charAt(i) == player) connected++;
				else i = 0;
			}
		}
		if(connected == 4) return true;
		else return false;
	}
	/* Diagonal to the Left */
	private static boolean checkDiagonalLeft(int col, int row, char player){
		int connected = 1;
		/* Downwards */
		if((col != 7) && (row != 0)){
			for(int i = col+1, j = row-1; i <= 7 && j >= 0; i++, j--){
				if(board.get(i).charAt(j) == player) connected++;
				else i = 7;
			}
		}
		/* Upwards */
		if((col != 1) && (row != 5)){
			for(int i = col-1, j = row+1; i >= 1 && j < 6; i--, j++){
				if(board.get(i).charAt(j) == player) connected++;
				else i = 1;
			}
		}
		if(connected == 4) return true;
		else return false;
	}
	/* Diagonal to the Right */
	private static boolean checkDiagonalRight(int col, int row, char player){
		int connected = 1;
		/* Downwards */
		if((col != 1) && (row != 0)){
			for(int i = col-1, j = row-1; i >= 1 && j >= 0; i--, j--){
				if(board.get(i).charAt(j) == player) connected++;
				else i = 1;
			}
		}
		/* Upwards */
		if((col != 7) && (row != 5)){
			for(int i = col+1, j = row+1; i <= 7 && j < 6; i++, j++){
				if(board.get(i).charAt(j) == player) connected++;
				else i = 7;
			}
		}
		if(connected == 4) return true;
		else return false;
	}
	private static void initializeBoard(){
		for(int i = 1; i <= 7; i++){
			board.put(i, "000000");
		}
	}
	public static HashMap<Integer, String> getBoard(){
		return board;
	}
	public void setUI(UI gameUI){
		this.gameUI = gameUI;
	}
}