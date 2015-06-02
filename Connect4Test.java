import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Connect4Test{
	private static HashMap<Integer, String> board = new HashMap<Integer, String>(7);
	private static boolean endGame = false;
	private static UI gameUI;
	private static Random rand = new Random();
	private static int col;
	private final static char PLAYER = '2';
	private final static char AI = '1';

	private static NodeTest root2;

	private static boolean isFirstMoveAI = true;
	private static boolean isFirstMoveOpp = true;

	protected static int whichAI;
	public Connect4Test(){
		initializeBoard();
	}

	public static void moveAI(){

		//dito ata tatawagin ung minimax
		//so board lang meron dito
	//	System.out.println("Called");
	//	if(!isFirstMoveAI){
			if(isFirstMoveOpp){ //base case na ito
				isFirstMoveOpp = false;
				root2 = new NodeTest(null);
				root2.player = '2'; //si 2 ung huli kasing gumalaw
				//root.row = 1;
				//root.col = 4;
				root2.myMaterial = .1;
				root2.oppMaterial = .1;
				root2.score = 0;
				root2.config = (HashMap<Integer, String>)board.clone();

				//System.out.println("" + root2.config.size());
			}
			else{
				updateRootOpp();
			}
			int randomCol = rand.nextInt(7);
			int move = Connect4Test.MinMax();
			col = move;
			updateRootAI();
			System.out.println("move: add to column " + move);
			gameUI.addToken(move, 2);

//		}
	//	else if(isFirstMoveAI){
	//		firstMove();
	//		isFirstMoveAI = false;
			// root = new Node(null); //base case, assuming first player si AI lagi, and sya si 1
			// root.player = '1';
			// root.row = 1;
			// root.col = 4;
			// root.myMaterial = .1;
			// root.oppMaterial = .1;
//		}

	}
	public static void firstMove(){
		gameUI.addToken(4, 1);
	}
	public static int addPiece(int column, int row, int player){
		if(player == 2) col = column;
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
		System.out.println("Player: " + player);
		newCol = currentCol.replaceFirst("0", tokenString);
		board.put(column, newCol);
		if(hasWinner(column, row, token)){
			System.out.println("winner player " + player);
			endGame = true;
			if(player == 1) return 4;
			else return 3;
		}
		else if(isDraw()){
			return 5;
		}
		if(player == 1) return 2;
		else return 1;
	}

	private static boolean isDraw(){
		for(int i = 1; i <= 7; i++){
			if(board.get(i).indexOf("0") != -1) return false;
		}
		return true;
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
		//moveAI();
	}
	// Heuristic 1?
	// Count number of possible 4 in a rows that each player can still make in the current state
	
	// Heuristic 2?
	// Score = My Material - Opponent's Material
	// Material = count of all contiguous lines of pieces that a player has, WEIGHTED, so 1 in a row, .1 points, etc.
	// Consider only ung may open positions pa

	//Dapat may magssave ng stateScores ni player 1 and 2 doon sa currentState

	// sa tree, computation ng statescore ay iincrement lang from parent, in favor of AI agent pa rin 
	public static double getStateScoreForAI1(double myMaterial, double oppMaterial){
		double retDouble = myMaterial - oppMaterial;
		return retDouble;
	}

	public static double getStateScoreForAI2(double myMaterial, double oppMaterial){
		double retDouble = myMaterial - oppMaterial;
		return retDouble;
	}

	protected static double updateMaterial(char player, int row, int col, double prevMaterial, HashMap<Integer, String> board){
		double retDouble = prevMaterial;
		retDouble += checkHowManyInARow(player, row, col, board);
		return retDouble;
	}
	//do I count ung rows na wala naman na pagasa makabuo pa ng 4? this implementation, NO
	//cinocount ko rin ung doubles, IE 1 in a row sya, pero pwede ring 2 in a row pala etc DUPLICATES, which makes sense since higher chances of winning naman talaga if maraming possible successes sa move na un

	//try: pag nag combi ie 2, + .2 - .1 since ung previous one in a row void na dahil 2 in a row na?, so if nag3, +.3 -.2
	//so parang essentially + .1 lang -_- TRY 
	//big score if 3 in a row tapos one move na lang panalo na
		private static double checkHowManyInARow(char player, int row, int col, HashMap<Integer, String> board){
		double retDouble = 0;

		char left, right, up, down, upRight, upLeft, downRight, downLeft;
		char left1, right1, up1, down1, upRight1, upLeft1, downRight1, downLeft1;
		char left2, right2, up2, down2, upRight2, upLeft2, downRight2, downLeft2;
		
		left = getLeft(col, row, board);
		//System.out.println("row" + row);
		right = getRight(col, row, board);
		up = getUp(col, row, board);
		down = getDown(col, row, board);
		upRight = getUpRight(col, row, board);
		upLeft = getUpLeft(col, row, board);
		downRight = getDownRight(col, row, board);
		downLeft = getDownLeft(col, row, board);

		//get left nung nasa taas
		left1 = getLeft(col-1, row, board);
		right1 = getRight(col+1, row, board);
		up1 = getUp(col, row+1, board);
		down1 = getDown(col, row-1, board);
		upRight1 = getUpRight(col+1, row+1, board);
		upLeft1 = getUpLeft(col-1, row+1, board);
		downRight1 = getDownRight(col+1, row-1, board);
		downLeft1 = getDownLeft(col-1, row-1, board);

		left2 = getLeft(col-2, row, board);
		right2 = getRight(col+2, row, board);
		up2 = getUp(col, row+2, board);
		down2 = getDown(col, row-2, board);
		upRight2 = getUpRight(col+2, row+2, board);
		upLeft2 = getUpLeft(col-2, row+2, board);
		downRight2 = getDownRight(col+2, row-2, board);
		downLeft2 = getDownLeft(col-2, row-2, board);

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
				if(right1 == '0'){//0PP|0
					retDouble += .2;
				}
				else if(right1 == player){//0PP|P
					retDouble += .5;
					if(right2 == '0'){//0PPP|0
						retDouble += .5;
					}
					else if(right2 == player){//0PPP|P
						retDouble += 10; //perfect 4 in a row
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
				if(left1 == player){ //P|PP0
					retDouble += .5; 
					if(left2 == '0'){ //0|PPP0
						retDouble += .5;
					}
					else if(left2 == player){ //P|PPP0
						retDouble += 10;
					}
				}
			}
			else if(right == player){ //PP|P
				if(right1 == '0'){//PPP|0
					retDouble += .5;
				}
				else if(right1 == player){ // PPP|P
					retDouble += 2;
				}
				if(left1 == '0'){ //0|PPP
					retDouble += .5;
				}
				else if(left1 == player){ // PPP|P
					retDouble += 10;
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
				if(down1 == '0'){//0PP|0
					retDouble += .2;
				}
				else if(down1 == player){//0PP|P
					retDouble += .1;
					if(down2 == '0'){//0PPP|0
						retDouble += .1;
					}
					else if(down2 == player){//0PPP|P
						retDouble += 10; //perfect 4 in a row
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
				if(up1 == player){ //P|PP0
					retDouble += .5; 
					if(up2 == '0'){ //0|PPP0
						retDouble += .5;
					}
					else if(up2 == player){ //P|PPP0
						retDouble += 10;
					}
				}
			}
			else if(down == player){ //PP|P
				if(down1 == '0'){//PPP|0
					retDouble += .5;
				}
				else if(down1 == player){ // PPP|P
					retDouble += 2;
				}
				if(up1 == '0'){ //0|PPP
					retDouble += .5;
				}
				else if(up1 == player){ // PPP|P
					retDouble += 10;
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
				if(downRight1 == '0'){//0PP|0
					retDouble += .1;
				}
				else if(downRight1 == player){//0PP|P
					retDouble += .5;
					if(downRight2 == '0'){//0PPP|0
						retDouble += .5;
					}
					else if(downRight2 == player){//0PPP|P
						retDouble += 10; //perfect 4 in a row
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
				if(upLeft1 == player){ //P|PP0
					retDouble += .5; 
					if(upLeft2 == '0'){ //0|PPP0
						retDouble += .5;
					}
					else if(upLeft2 == player){ //P|PPP0
						retDouble += 10;
					}
				}
			}
			else if(downRight == player){ //PP|P
				if(downRight1 == '0'){//PPP|0
					retDouble += .5;
				}
				else if(downRight1 == player){ // PPP|P
					retDouble += 2;
				}
				if(upLeft1 == '0'){ //0|PPP
					retDouble += .5;
				}
				else if(upLeft1 == player){ // PPP|P
					retDouble += 10;
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
				if(downLeft1 == '0'){//0PP|0
					retDouble += .1;
				}
				else if(downLeft1 == player){//0PP|P
					retDouble += .5;
					if(downLeft2 == '0'){//0PPP|0
						retDouble += .5;
					}
					else if(downLeft2 == player){//0PPP|P
						retDouble += 10; //perfect 4 in a row
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
					retDouble += .5; 
					if(upRight2 == '0'){ //0|PPP0
						retDouble += .5;
					}
					else if(upRight2 == player){ //P|PPP0
						retDouble += 10;
					}
				}
			}
			else if(downLeft == player){ //PP|P
				if(downLeft1 == '0'){//PPP|0
					retDouble += .5;
				}
				else if(downLeft1 == player){ // PPP|P
					retDouble += 2;
				}
				if(upRight1 == '0'){ //0|PPP
					retDouble += .5;
				}
				else if(upRight1 == player){ // PPP|P
					retDouble += 10;
				}
			}
		}

		return retDouble;

	}

	private static char getLeft(int col, int row, HashMap<Integer, String> board){
		char retChar = '\0'; //null
		if(col > 1) retChar = board.get(col-1).charAt(row);
		return retChar;
	}

	private static char getRight(int col, int row, HashMap<Integer, String> board){
		char retChar = '\0';
		//System.out.println(row);
		if(col < 7) retChar = board.get(col+1).charAt(row);
		return retChar;
	}

	private static char getUp(int col, int row, HashMap<Integer, String> board){
		char retChar = '\0';
		if(row < 5 ) retChar = board.get(col).charAt(row+1);
		return retChar;
	}

	private static char getDown(int col, int row, HashMap<Integer, String> board){
		char retChar = '\0';
		if(row > 0) retChar = board.get(col).charAt(row-1);
		return retChar;
	}

	private static char getUpLeft(int col, int row, HashMap<Integer, String> board){
		char retChar = '\0';
		if(row < 5 && col > 1) retChar = board.get(col-1).charAt(row+1);
		return retChar;
	}

	private static char getUpRight(int col, int row, HashMap<Integer, String> board){
		char retChar = '\0';
		if(row < 5 && col < 7) retChar = board.get(col+1).charAt(row+1);
		return retChar;
	}

	private static char getDownLeft(int col, int row, HashMap<Integer, String> board){
		char retChar = '\0';
		if(row > 0 && col > 1) retChar = board.get(col-1).charAt(row-1);
		return retChar;
	}

	private static char getDownRight(int col, int row, HashMap<Integer, String> board){
		char retChar = '\0';
		if(row > 0 && col < 7) retChar = board.get(col+1).charAt(row-1);
		return retChar;
	}

	
//	private static int MinMax(int col, int row, char player) {
	
	static int MinMax() {
		//Tree mainTree = new Tree();
		//mainTree.setRootNode(new Node(board, 0));
		//Node root = mainTree.returnRoot();
		System.out.println("Player: " + root2.player + " Score: " + root2.score + " AI: " + root2.myMaterial + " Opp: " + root2.oppMaterial);
		ArrayList<NodeTest> children = expand(root2);
		children = root2.children;

		NodeTest max = MaxMove (root2, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
		int index = children.indexOf(max);
		int col = children.get(index).col;
		System.out.println(index + " " + col);
		return (max.col);
	}

	static NodeTest MaxMove(NodeTest root2, int depth, double alpha, double beta) {
		if(depth>7){
			//System.out.println("END");
			return root2;
		}
		NodeTest bestMove;
		try{
			expand(root2);
		}catch(StringIndexOutOfBoundsException e){
			e.printStackTrace();
		}finally{
			//System.out.println("depth" + depth);
			ArrayList<NodeTest> children = root2.children;
			bestMove = children.get(0);//initialize best move
			NodeTest currMove = children.get(1);
			NodeTest dummy;
			for(int i = 0; i <= children.size()-1; i++) {//iterate on each move
				currMove = children.get(i);
				alpha=Math.max(alpha,bestMove.score);
				
				depth=depth+1;
				dummy = MinMove(currMove, depth, alpha, beta); 
				if (dummy.score > (bestMove).score)
					bestMove = currMove;	
				if( dummy.score>=beta)
					return dummy;
			}
			//System.out.println(children.get(children.indexOf(bestMove)));
		}
		//System.out.println("bestMove????????????//");
		return bestMove;
	}
	
	private static NodeTest MinMove(NodeTest root2, int depth, double alpha, double beta) {
		if(depth>=7){
			//System.out.println("END");
			return root2;
		}
		NodeTest bestMove;
		try{
			expand(root2);
		}catch(StringIndexOutOfBoundsException e){
			e.printStackTrace();
		}finally{
			//System.out.println("depth" + depth);
			ArrayList<NodeTest> children = root2.children;
			bestMove = children.get(0);
			NodeTest currMove = children.get(1);//initialize best move
			NodeTest dummy = currMove;
			for(int i = 0; i <= children.size()-1; i++) {//iterate on all moves
				beta=Math.min(beta, bestMove.score);
				depth = depth+1;
				dummy = MaxMove(currMove, depth++, alpha, beta);
				if (dummy.score > (bestMove).score) 
					bestMove = currMove;
				if(dummy.score<=alpha)
					return dummy;
				//beta=Math.min(beta, bestMove.score);
			}
			//System.out.println(children.get(children.indexOf(bestMove)));
		}
		return bestMove;
	}
	/*
	private static int MinMax(){
		root.setAlpha(Integer.MIN_VALUE);
		root.setBeta(Integer.MAX_VALUE);
		int retval = 1, loop2 =0;
		//int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;//Step 1
		ArrayList<NodeTest> children = new ArrayList<Node>();
		expand(root);
		children = root.children;
		int index = 0;
		System.out.println(children.size());
		Node firstChild = children.get(0), currNode = firstChild, rootNode;
		for(int i = 0;  i <= 3; i++){
			System.out.println(i+"iiiii");
			firstChild = children.get(0);
			firstChild.setAlpha(root.alpha);
			firstChild.setBeta(root.beta);
			System.out.println("root: " + root.alpha);
			System.out.println("root: " + root.beta);
			children = expand(firstChild);
			children = firstChild.children;
		}
		rootNode = firstChild;
		int loop = 0;
		System.out.println("yyyy" + children.size());
		currNode = children.get(0);
		double score = 0;
		//questionable ang seconde statement sa while loop
		while(!rootNode.equals(root)&&loop!=6){//habang di pa bumabalik sa root or habang less than the number of possible children palang ang naeexplore
			children = rootNode.children;
			System.out.println("running" + children.size());
			for(int i = 0; i <= children.size()-1; i++){//loop on all children of firstChild
				System.out.println("heeeer" + children.size());
				currNode = children.get(i);
				score = currNode.score;
				System.out.println(score + "scooore");
				if(rootNode.player==PLAYER)//Kapag player ibig sabihin nagmiminimize ka
					rootNode.setBeta(Math.min(rootNode.beta, score));
				else
					rootNode.setAlpha(Math.max(rootNode.alpha, score));
				System.out.println(rootNode.beta + "beta");
				System.out.println(rootNode.alpha + "alpha");
				if(rootNode.alpha>=rootNode.beta){
//					rootNode = (currNode.parent).parent;
//					currNode.parent.value = currNode.value;
//					children = rootNode.children;
					System.out.print(currNode.alpha + " " + currNode.beta);
					break;
				}
			}
//			System.out.println("rootnode: "+ rootNode.player);
			if(rootNode.player==PLAYER)
				rootNode.value = rootNode.beta;
			else
				rootNode.value = rootNode.alpha;
			rootNode = rootNode.parent;
			System.out.println("rootnode: "+ rootNode.player);
			
			children = rootNode.children;
			if(rootNode.player==AI)
				rootNode.setAlpha(Math.max(currNode.parent.value, rootNode.alpha));
			else
				rootNode.setBeta(Math.min(currNode.parent.value, rootNode.beta));
			System.out.println("children.indexOf(currNode.parent)+1: "+ children.indexOf(currNode.parent)+1);
			
			if(children.size()>=children.indexOf(currNode.parent)+1&&loop2<6){
				rootNode = children.get(children.indexOf(currNode.parent)+1); //go to next kapatid
			//	retval = children.indexOf(currNode.parent)+1;
				System.out.println("go to next kapatid");
				rootNode.setAlpha(rootNode.parent.alpha);
				rootNode.setBeta(rootNode.parent.beta);
				System.out.println("alpha: " + rootNode.parent.alpha);
				System.out.println("beta: " + rootNode.parent.beta);
				loop2++;
			}else{
				System.out.println("elsee");
				if(rootNode.player==PLAYER)
					rootNode.value = rootNode.beta;
				else
					rootNode.value = rootNode.alpha;
				System.out.println(rootNode.value + "1value");
				
				rootNode = rootNode.parent;
				if(rootNode.player==AI){
					System.out.println(index+"shet indezx");
					rootNode.setAlpha(Math.max(rootNode.children.get(index).value, rootNode.alpha));//posibleng mali
				}else
					rootNode.setBeta(Math.min(rootNode.children.get(index).value, rootNode.beta));//posibleng mali
				if(rootNode.player==PLAYER)
					rootNode.value = rootNode.beta;
				else
					rootNode.value = rootNode.alpha;
				System.out.println(rootNode.value + "value");
				children = rootNode.children;
				if(children.size()-1>=index+1&&index<5){
					index++;
					firstChild = children.get(index);
					System.out.println(index + "index");
					currNode = firstChild;
					for(int i = 0;  i <= 3; i++){
						firstChild = children.get(0);
						firstChild.setAlpha(root.alpha);
						firstChild.setBeta(root.beta);
						expand(firstChild);
						children = firstChild.children;
					}
					rootNode = firstChild;
					currNode = children.get(0);		
				}else{
					retval = 1;
					for(int i = 0; i <= 5; i++){
						System.out.println(rootNode.children.get(i).value + " "+ rootNode.children.get(retval).value);
						if(rootNode.children.get(i).value>rootNode.children.get(retval).value){
							retval = i+1;		
						}System.out.println("huhuhu" + i);
					}
					System.out.println("huhuhu" + retval);
					return retval;
				}
			}
		}

		loop++;
		System.out.println(retval);
		return retval;
	}
*/
	private static void updateRootAI(){
		root2.children.clear();
		expand(root2);
		NodeTest temp;
		for(int i = 0; i < root2.children.size(); i++){
			temp = root2.children.get(i);
			if(temp.col == col){
				root2 = temp;
				break;
			}
		}
	}

	private static void updateRootOpp(){
		root2.children.clear();
		expand(root2);
		NodeTest temp; 
		for(int i = 0; i < root2.children.size(); i++){
			temp = root2.children.get(i);
			if(temp.col == col){
				root2 = temp;
				break;
			}
		}	
	}
	private static ArrayList<NodeTest> expand(NodeTest parent) {
		
		parent.children.clear();
		for(int i = 1; i < 8; i++){
			NodeTest node = new NodeTest(parent);
			node.setPlayer();
			//System.out.println("adding child " + i);
			if(node.setConfig(i)){
			//	System.out.println("Here");
				node.setScores();
				parent.children.add(node);
			}
			// System.out.println("Parent score: " + parent.score);
			// System.out.println("Node score: " + node.score);
		}
		return null;
	}
}