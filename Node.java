import java.util.ArrayList;
import java.util.HashMap;

class Node{
	HashMap<Integer, String> config;
	double score, myMaterial, oppMaterial, alpha, beta;
	ArrayList<Node> children = new ArrayList<Node>();
	char player; //kung sinong nagplay ng move na yon
	Node parent;
	int row, col, alpha, beta;
	
	// public Node(HashMap<Integer, String> newConfig, double newScore, char player){
	// 	config = newConfig;
	// 	score = newScore;
	// 	this.player = player;
	// }
	public Node(Node parent){
		this.parent = parent;
		alpha = Integer.MAX_VALUE;
		beta = Integer.MIN_VALUE;
	}
	public HashMap<Integer, String> getConfig(){
		return config;
	}
	public double getScore(){
		return score;
	}
	public void setAlpha(int alpha){
		this.alpha = alpha;
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
	public void setConfig(int col){
		HashMap<Integer, String> moved = (HashMap<Integer, String>) parent.config.clone();
		String currConfig = moved.get(col);
		System.out.println("before: " + currConfig);
		currConfig = currConfig.replaceFirst("0", ""+ this.player);
		this.row = currConfig.lastIndexOf(this.player);
		this.col = col;
		System.out.println("after: " + currConfig);
		moved.replace(col, currConfig);
		this.config = moved;
	}
	public void setScores(){	
		if(player == '1'){ //si 1 ung gumalaw
			this.oppMaterial = parent.oppMaterial;
			this.myMaterial = Connect4.updateMaterial(this.player, this.row, this.col, this.myMaterial, this.config); 
		}
		else if(player == '2'){ //si 2 ung gumalaw
			this.myMaterial = parent.myMaterial;
			this.oppMaterial = Connect4.updateMaterial(this.player, this.row, this.col, this.oppMaterial, this.config);
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