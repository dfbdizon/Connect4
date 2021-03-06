import java.util.ArrayList;
import java.util.HashMap;

class Node{
	HashMap<Integer, String> config;
	Node parent;
	double score, myMaterial, oppMaterial, alpha, beta, value;
	ArrayList<Node> children = new ArrayList<Node>();
	char player; //kung sinong nagplay ng move na yon

	Node root;
	int row, col;
	// public Node(HashMap<Integer, String> newConfig, double newScore, char player){
	// 	config = newConfig;
	// 	score = newScore;
	// 	this.player = player;
	// }
	public Node(Node parent){
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
	public Node getParent(){
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
		//System.out.println("Called");
		if(currConfig.lastIndexOf("0") <= 5 && currConfig.lastIndexOf("0") >= 0){
			//System.out.println("EH");
			currConfig = currConfig.replaceFirst("0", ""+ this.player);
			//System.out.println("after" + currConfig);
			this.row = currConfig.lastIndexOf(this.player);
			//System.out.println("Row is" + row);
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
		//System.out.println("Player" + player);	
		if(player == '1'){ //si 1 ung gumalaw
			this.oppMaterial = parent.oppMaterial;
			this.myMaterial = Connect4.updateMaterial(this.player, this.row, this.col, parent.myMaterial, this.config); 
		}
		else if(player == '2'){ //si 2 ung gumalaw
			this.myMaterial = parent.myMaterial;
			this.oppMaterial = Connect4.updateMaterial(this.player, this.row, this.col, parent.oppMaterial, this.config);
		}
		this.score = Connect4.getStateScore(this.myMaterial, this.oppMaterial);
	}
	public void addChild(Node node){
		children.add(node);
	}
	public void removeChild(int index){
		children.remove(index);
	}
	public ArrayList<Node> getChildren(){
		return children;
	}
	public Node getChild(int index){
		return (children.get(index));
	}
	
	
}