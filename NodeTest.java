import java.util.ArrayList;
import java.util.HashMap;

class NodeTest{
	HashMap<Integer, String> config;
	NodeTest parent;
	double score, myMaterial, oppMaterial, alpha, beta, value;
	ArrayList<NodeTest> children = new ArrayList<NodeTest>();
	char player; //kung sinong nagplay ng move na yon

	Node root;
	int row, col;
	// public Node(HashMap<Integer, String> newConfig, double newScore, char player){
	// 	config = newConfig;
	// 	score = newScore;
	// 	this.player = player;
	// }
	public NodeTest(NodeTest parent){
		this.parent = parent;
		alpha = Integer.MIN_VALUE;
		beta = Integer.MAX_VALUE;
	}
	public HashMap<Integer, String> getConfig(){
		return config;
	}
	public double getScore(){
		return score;
	}
	public void setAlpha(double alpha2){
		this.alpha = alpha2;
	}
	public void setBeta(double beta){
		this.beta = beta;
	}
	public NodeTest getParent(){
		return this.parent;
	}
	public void setPlayer(){
		if(parent.player == '1'){ 
			this.player = '2'; //ibig sabihin, si 2 ung gumalaw
		}
		else if(parent.player == '2'){
			this.player = '1'; //ibig sabihin, si 1 ung gumalaw
		}
	}
	public boolean setConfig(int col){
		HashMap<Integer, String> moved = (HashMap<Integer, String>) parent.config.clone();
		String currConfig = moved.get(col);
		boolean retVal = true;
		//System.out.println("before: " + currConfig);
		if(currConfig.lastIndexOf(this.player) < 5){
			currConfig = currConfig.replaceFirst("0", ""+ this.player);
			this.row = currConfig.lastIndexOf(this.player);
			this.col = col;
			//System.out.println("after: " + currConfig);
			moved.replace(col, currConfig);
			this.config = moved;
		}
		else{
			retVal = false; //ibig sabihin puno na
		}
		return retVal;
	}
	public void setScores(){	
		if(player == '1'){ //si 1 ung gumalaw
			this.oppMaterial = parent.oppMaterial;
			this.myMaterial = Connect4Test.updateMaterial(this.player, this.row, this.col, this.myMaterial, this.config); 
		}
		else if(player == '2'){ //si 2 ung gumalaw
			this.myMaterial = parent.myMaterial;
			this.oppMaterial = Connect4Test.updateMaterial(this.player, this.row, this.col, this.oppMaterial, this.config);
		}
		if(Connect4Test.whichAI == 1) this.score = Connect4Test.getStateScoreForAI1(this.myMaterial, this.oppMaterial);
		else this.score = Connect4Test.getStateScoreForAI2(this.myMaterial, this.oppMaterial);
	}
	public void addChild(NodeTest node){
		children.add(node);
	}
	public void removeChild(int index){
		children.remove(index);
	}
	public ArrayList<NodeTest> getChildren(){
		return children;
	}
	public NodeTest getChild(int index){
		return (children.get(index));
	}
	
	
}