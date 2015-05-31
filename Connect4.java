import java.util.HashMap;

public class Connect4{
	private static HashMap<Integer, String> board = new HashMap<Integer, String>(7);
	public static void main(String [] args){
		initializeBoard();
	}
	@SuppressWarnings("unused")
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
		if((col != 1) && (row != 6)){
			for(int i = col-1, j = row+1; i >= 1 && j <= 6; i--, j++){
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
		if((col != 7) && (row != 6)){
			for(int i = col+1, j = row+1; i <= 7 && j <= 6; i++, j++){
				if(board.get(i).charAt(j) == player) connected++;
				else i = 7;
			}
		}
		if(connected == 4) return true;
		else return false;
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
	
	static double getStateScore2(char player, int row, int col){
		return 0;
	}
	
	int MinMax(int col, int row, char player) {
		Tree mainTree = new Tree(col, row, player, new Node(board, 0));
		return MaxMove (col, row, player, mainTree);
	}
	 
	/*int MinMaxIterative(int col, int row, char player){
		boolean isMax = true;
		while(!hasWinner(col, row, player)){
			if(isMax){
				
				isMax=!isMax;
			}else{
				
				isMax=!isMax;
			}
		}
	}*/
	int MaxMove(int col, int row, char player, Tree tree) {
		if (hasWinner(col, row, player)) {
			return 9;//EvalGameState(config);
		}
		else {
			Node bestMove = tree.getChild(0);//initialize best move
			Node currMove = tree.getChild(1);
			Tree currTree = new Tree(col, row, '2', currMove);
			for(int i = 0; i <= tree.children.size()-1; i++) {//iterate on each move
				if (getStateScore2('2', row, tree.children.indexOf(MinMove(col, row, '2', currTree))) > getStateScore2('2', row, tree.children.indexOf(bestMove))) 
					bestMove = currMove;
			}
			return tree.children.indexOf(bestMove)+1;
		}
	}
	 
	int MinMove(int col, int row, char player, Tree tree) {
		Node bestMove = tree.getChild(1);
		Node currMove = tree.getChild(0);//initialize best move
		Tree currTree = new Tree(col, row, '1', currMove);
		for(int i = 0; i <= tree.children.size()-1; i++) {//iterate on all moves
			if (getStateScore2('2', row, tree.children.indexOf(MaxMove(col, row, '1', currTree))) > getStateScore2('2', row, tree.children.indexOf(bestMove))) 
				bestMove = currMove;
		}
		return tree.children.indexOf(bestMove)+1;
	}
	
}