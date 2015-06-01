import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Connect4{
	private static HashMap<Integer, String> board = new HashMap<Integer, String>(7);
	private static boolean endGame = false;
	private static UI gameUI;
	private static Random rand = new Random();

	public Connect4(){
		initializeBoard();
	}
	public static void moveAI(){
		int randomCol = rand.nextInt(7) + 1;
		System.out.println("move: add to column " + randomCol);
		gameUI.addToken(randomCol, 1);
	}
	public static void firstMove(){
		gameUI.addToken(4, 1);
	}
	public static int addPiece(int column, int row, int player){
		String currentCol = board.get(column);
		String newCol;
		char token;
		String tokenString;
		if(player == 1){
			tokenString = "1";
			token = '1';
		}
		else{
			tokenString = "2";
			token = '2';
		} 

		newCol = currentCol.replaceFirst("0", tokenString);
		board.put(column, newCol);
		if(hasWinner(column, row, token)){
			System.out.println("winner player " + player);
			endGame = true;
			if(player == 1) return 4;
			else return 3;
		}
		//else if (Check if draw)
		if(player == 1) return 2;
		else return 1;
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
		System.out.println("set ui");
		this.gameUI = gameUI;
		try{
			Thread.sleep(1000);
		} catch(Exception e){
			e.printStackTrace();
		}
		firstMove();
	}
	// Heuristic 1?
	// Count number of possible 4 in a rows that each player can still make in the current state
	
	// Heuristic 2?
	// Score = My Material - Opponent's Material
	// Material = count of all contiguous lines of pieces that a player has, WEIGHTED, so 1 in a row, .1 points, etc.
	// Consider only ung may open positions pa

	//Dapat may magssave ng stateScores ni player 1 and 2 doon sa currentState

	// sa tree, computation ng statescore ay iincrement lang from parent, in favor of AI agent pa rin 
	private static double getStateScore(char player, int row, int col, double myMaterial, double oppMaterial){
		String currentColumn;
		char currentPiece;
		double retDouble = myMaterial - oppMaterial;
		return retDouble;
	}

	protected static double updateMaterial(char player, int row, int col, double prevMaterial, HashMap<Integer, String> board){
		double retDouble = prevMaterial;
		retDouble += checkHowManyInARow(player, row, col);
		return retDouble;
	}
	//do I count ung rows na wala naman na pagasa makabuo pa ng 4? this implementation, NO
	//cinocount ko rin ung doubles, IE 1 in a row sya, pero pwede ring 2 in a row pala etc DUPLICATES, which makes sense since higher chances of winning naman talaga if maraming possible successes sa move na un

	//try: pag nag combi ie 2, + .2 - .1 since ung previous one in a row void na dahil 2 in a row na?, so if nag3, +.3 -.2
	//so parang essentially + .1 lang -_- TRY 
	private static double checkHowManyInARow(char player, int row, int col){
		double retDouble = 0;

		char left, right, up, down, upRight, upLeft, downRight, downLeft;
		char left1, right1, up1, down1, upRight1, upLeft1, downRight1, downLeft1;
		char left2, right2, up2, down2, upRight2, upLeft2, downRight2, downLeft2;
		
		left = getLeft(col, row);
		right = getRight(col, row);
		up = getUp(col, row);
		down = getDown(col, row);
		upRight = getUpRight(col, row);
		upLeft = getUpLeft(col, row);
		downRight = getDownRight(col, row);
		downLeft = getDownLeft(col, row);

		//get left nung nasa taas
		left1 = getLeft(col-1, row);
		right1 = getRight(col+1, row);
		up1 = getUp(col, row+1);
		down1 = getDown(col, row-1);
		upRight1 = getUpRight(col+1, row+1);
		upLeft1 = getUpLeft(col-1, row+1);
		downRight1 = getDownRight(col+1, row-1);
		downLeft1 = getDownLeft(col-1, row-1);

		left2 = getLeft(col-2, row);
		right2 = getRight(col+2, row);
		up2 = getUp(col, row+2);
		down2 = getDown(col, row-2);
		upRight2 = getUpRight(col+2, row+2);
		upLeft2 = getUpLeft(col-2, row+2);
		downRight2 = getDownRight(col+2, row-2);
		downLeft2 = getDownLeft(col-2, row-2);

		//HORIZONTAL
		//check if open ung left
		if(left == '0'){ // 0|P
			//check if open ung right
			if(right == '0'){ //0P|0
				//check if open ung left left or right right
				if(right1 == '0' || left1 == '0'){ //0P0|0 or 0|0P0
					retDouble += .1;
				}
			}
			//if di open ung right, check if piece nya un 
			else if(right == player){ //0P|P
				//check if open ung left1
				if(left1 == '0'){ //0|0PP
					retDouble += .2;
				}
				else if(right1 == '0'){//0PP|0
					retDouble += .2;
				}
				else if(right1 == player){//0PP|P
					retDouble += .3;
					if(right2 == '0'){//0PPP|0
						retDouble += .3;
					}
					else if(right2 == player){//0PPP|P
						retDouble += .10; //perfect 4 in a row
					}
				}
			}
			else{ //0P|sumth
				if(left1 == '0' && left2 == '0'){ //00|0P
					retDouble += .1;
				}
			}
		}
		else if(left == player){ //P|P
			if(right == '0'){ //PP|0
				if(right1 == '0' || left1 == '0'){ //0|PP0 or PP0|0
					retDouble += .2;
				}
				else if(left1 == player){ //P|PP0
					retDouble += .3; 
					if(left2 == '0'){ //0|PPP0
						retDouble += .3;
					}
					else if(left2 == player){ //P|PPP0
						retDouble += .10;
					}
				}
			}
			else if(right == player){ //PP|P
				if(right1 == '0'){//PPP|0
					retDouble += .3;
				}
				else if(right1 == player){ // PPP|P
					retDouble += .10;
				}
				if(left1 == '0'){ //0|PPP
					retDouble += .3;
				}
				else if(left1 == player){ // PPP|P
					retDouble += .10;
				}
			}
		}

		//VERTICAL
		//parang ung nasa taas lang, left lang ay up, right ay down
		if(up == '0'){ // 0|P
			//check if open ung right
			if(down == '0'){ //0P|0
				//check if open ung left left or right right
				if(down1 == '0' || up1 == '0'){ //0P0|0 or 0|0P0
					retDouble += .1;
				}
			}
			//if di open ung right, check if piece nya un 
			else if(down == player){ //0P|P
				//check if open ung left1
				if(up1 == '0'){ //0|0PP
					retDouble += .2;
				}
				else if(down1 == '0'){//0PP|0
					retDouble += .2;
				}
				else if(down1 == player){//0PP|P
					retDouble += .3;
					if(down2 == '0'){//0PPP|0
						retDouble += .3;
					}
					else if(down2 == player){//0PPP|P
						retDouble += .10; //perfect 4 in a row
					}
				}
			}
			else{ //0P|sumth
				if(up1 == '0' && up2 == '0'){ //00|0P
					retDouble += .1;
				}
			}
		}
		else if(up == player){ //P|P
			if(down == '0'){ //PP|0
				if(down1 == '0' || up1 == '0'){ //0|PP0 or PP0|0
					retDouble += .2;
				}
				else if(up1 == player){ //P|PP0
					retDouble += .3; 
					if(up2 == '0'){ //0|PPP0
						retDouble += .3;
					}
					else if(up2 == player){ //P|PPP0
						retDouble += .10;
					}
				}
			}
			else if(down == player){ //PP|P
				if(down1 == '0'){//PPP|0
					retDouble += .3;
				}
				else if(down1 == player){ // PPP|P
					retDouble += .10;
				}
				if(up1 == '0'){ //0|PPP
					retDouble += .3;
				}
				else if(up1 == player){ // PPP|P
					retDouble += .10;
				}
			}
		}

		//DIAGONAL 1
		//parang ganun ulit except, up is upLeft, down = downRight
		if(upLeft == '0'){ // 0|P
			//check if open ung right
			if(downRight == '0'){ //0P|0
				//check if open ung left left or right right
				if(downRight1 == '0' || upLeft1 == '0'){ //0P0|0 or 0|0P0
					retDouble += .1;
				}
			}
			//if di open ung right, check if piece nya un 
			else if(downRight == player){ //0P|P
				//check if open ung left1
				if(upLeft1 == '0'){ //0|0PP
					retDouble += .2;
				}
				else if(downRight1 == '0'){//0PP|0
					retDouble += .2;
				}
				else if(downRight1 == player){//0PP|P
					retDouble += .3;
					if(downRight2 == '0'){//0PPP|0
						retDouble += .3;
					}
					else if(downRight2 == player){//0PPP|P
						retDouble += .10; //perfect 4 in a row
					}
				}
			}
			else{ //0P|sumth
				if(upLeft1 == '0' && upLeft2 == '0'){ //00|0P
					retDouble += .1;
				}
			}
		}
		else if(upLeft == player){ //P|P
			if(downRight == '0'){ //PP|0
				if(downRight1 == '0' || upLeft1 == '0'){ //0|PP0 or PP0|0
					retDouble += .2;
				}
				else if(upLeft1 == player){ //P|PP0
					retDouble += .3; 
					if(upLeft2 == '0'){ //0|PPP0
						retDouble += .3;
					}
					else if(upLeft2 == player){ //P|PPP0
						retDouble += .10;
					}
				}
			}
			else if(downRight == player){ //PP|P
				if(downRight1 == '0'){//PPP|0
					retDouble += .3;
				}
				else if(downRight1 == player){ // PPP|P
					retDouble += .10;
				}
				if(upLeft1 == '0'){ //0|PPP
					retDouble += .3;
				}
				else if(upLeft1 == player){ // PPP|P
					retDouble += .10;
				}
			}
		}

		//DIAGONAL 2
		//parang ganun ulit except, up is upRight, down = downLeft
		if(upRight == '0'){ // 0|P
			//check if open ung right
			if(downLeft == '0'){ //0P|0
				//check if open ung left left or right right
				if(downLeft1 == '0' || upRight1 == '0'){ //0P0|0 or 0|0P0
					retDouble += .1;
				}
			}
			//if di open ung right, check if piece nya un 
			else if(downLeft == player){ //0P|P
				//check if open ung left1
				if(upRight1 == '0'){ //0|0PP
					retDouble += .2;
				}
				else if(downLeft1 == '0'){//0PP|0
					retDouble += .2;
				}
				else if(downLeft1 == player){//0PP|P
					retDouble += .3;
					if(downLeft2 == '0'){//0PPP|0
						retDouble += .3;
					}
					else if(downLeft2 == player){//0PPP|P
						retDouble += .10; //perfect 4 in a row
					}
				}
			}
			else{ //0P|sumth
				if(upRight1 == '0' && upRight2 == '0'){ //00|0P
					retDouble += .1;
				}
			}
		}
		else if(upRight == player){ //P|P
			if(downLeft == '0'){ //PP|0
				if(downLeft1 == '0' || upRight1 == '0'){ //0|PP0 or PP0|0
					retDouble += .2;
				}
				else if(upRight1 == player){ //P|PP0
					retDouble += .3; 
					if(upRight2 == '0'){ //0|PPP0
						retDouble += .3;
					}
					else if(upRight2 == player){ //P|PPP0
						retDouble += .10;
					}
				}
			}
			else if(downLeft == player){ //PP|P
				if(downLeft1 == '0'){//PPP|0
					retDouble += .3;
				}
				else if(downLeft1 == player){ // PPP|P
					retDouble += .10;
				}
				if(upRight1 == '0'){ //0|PPP
					retDouble += .3;
				}
				else if(upRight1 == player){ // PPP|P
					retDouble += .10;
				}
			}
		}

		return retDouble;

	}

	private static char getLeft(int col, int row){
		char retChar = '\0'; //null
		if(col != 1) retChar = board.get(col-1).charAt(row);
		return retChar;
	}

	private static char getRight(int col, int row){
		char retChar = '\0';
		if(col != 7) retChar = board.get(col+1).charAt(row);
		return retChar;
	}

	private static char getUp(int col, int row){
		char retChar = '\0';
		if(row != 5 ) retChar = board.get(col).charAt(row+1);
		return retChar;
	}

	private static char getDown(int col, int row){
		char retChar = '\0';
		if(row != 0) retChar = board.get(col).charAt(row-1);
		return retChar;
	}

	private static char getUpLeft(int col, int row){
		char retChar = '\0';
		if(row != 5 && col != 1) retChar = board.get(col-1).charAt(row+1);
		return retChar;
	}

	private static char getUpRight(int col, int row){
		char retChar = '\0';
		if(row != 5 && col != 7) retChar = board.get(col+1).charAt(row+1);
		return retChar;
	}

	private static char getDownLeft(int col, int row){
		char retChar = '\0';
		if(row != 0 && col != 1) retChar = board.get(col-1).charAt(row-1);
		return retChar;
	}

	private static char getDownRight(int col, int row){
		char retChar = '\0';
		if(row != 0 && col != 7) retChar = board.get(col+1).charAt(row-1);
		return retChar;
	}

	
	private static int MinMax(int col, int row, char player) {
	/*
	int MinMax(int col, int row, char player) {
		Tree mainTree = new Tree();
		mainTree.setRootNode(new Node(board, 0));
		Node root = mainTree.returnRoot();
		mainTree.addChild(new Node(root.move(1), getStateScores));
		mainTree.addChild(new Node(root.move(2), getStateScores));
		mainTree.addChild(new Node(root.move(3), getStateScores));
		mainTree.addChild(new Node(root.move(4), getStateScores));
		mainTree.addChild(new Node(root.move(5), getStateScores));
		mainTree.addChild(new Node(root.move(6), getStateScores));
		mainTree.addChild(new Node(root.move(7), getStateScores));
		return MaxMove (col, row, player, mainTree);
	}
	 
	int MinMaxIterative(int col, int row, char player){
		boolean isMax = true;
		while(!hasWinner(col, row, player)){
			if(isMax){
				
				isMax=!isMax;
			}else{
				
				isMax=!isMax;
			}
		}

	}*/
	private static int MaxMove(int col, int row, char player, Tree tree) {
	/*
	int MaxMove(int col, int row, char player, Tree tree) {
		if (hasWinner(col, row, player)) {
			return 9;//EvalGameState(config);
		}
		else {
			Node bestMove = tree.getChild(0);//initialize best move
			Node currMove = tree.getChild(1);
			Node root = currMove;
			Tree currTree = new Tree();
			currTree.addChild(new Node(root.move(1), getStateScores));
			currTree.addChild(new Node(root.move(2), getStateScores));
			currTree.addChild(new Node(root.move(3), getStateScores));
			currTree.addChild(new Node(root.move(4), getStateScores));
			currTree.addChild(new Node(root.move(5), getStateScores));
			currTree.addChild(new Node(root.move(6), getStateScores));
			currTree.addChild(new Node(root.move(7), getStateScores));
			for(int i = 0; i <= tree.children.size()-1; i++) {//iterate on each move
				if (getStateScores('2', row, tree.children.indexOf(MinMove(col, row, '2', currTree))) > getStateScores('2', row, tree.children.indexOf(bestMove))) 
					bestMove = currMove;
			}
			return tree.children.indexOf(bestMove)+1;
		}
	}
	 
	private static int MinMove(int col, int row, char player, Tree tree) {
		Node bestMove = tree.getChild(1);
		Node currMove = tree.getChild(0);//initialize best move
		Node root = currMove;
		Tree currTree = new Tree();
		currTree.addChild(new Node(root.move(1), getStateScores));
		currTree.addChild(new Node(root.move(2), getStateScores));
		currTree.addChild(new Node(root.move(3), getStateScores));
		currTree.addChild(new Node(root.move(4), getStateScores));
		currTree.addChild(new Node(root.move(5), getStateScores));
		currTree.addChild(new Node(root.move(6), getStateScores));
		currTree.addChild(new Node(root.move(7), getStateScores));
		for(int i = 0; i <= tree.children.size()-1; i++) {//iterate on all moves
			if (getStateScores('2', row, tree.children.indexOf(MaxMove(col, row, '1', currTree))) > getStateScores('2', row, tree.children.indexOf(bestMove))) 
				bestMove = currMove;
		}
		return tree.children.indexOf(bestMove)+1;
	}*/
	
	int MinMax(Node root){
		int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;//Step 1
		ArrayList<Node> children = expand(root);
		for(int i = 0;  i <= 9; i++){
			Node firstChild = children.get(i);
			children = expand(firstChild);
		}
		
		
		return 0;
	}
	
	private ArrayList<Node> expand(Node root) {
		//root.
		return null;
	}
}