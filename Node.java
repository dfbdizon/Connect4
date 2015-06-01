import java.util.ArrayList;
import java.util.HashMap;

class Node{
	HashMap<Integer, String> config;
	double score, myMaterial, oppMaterial;
	ArrayList<Node> children = new ArrayList<Node>();
	char player; //kung sinong nagplay ng move na yon
	double alpha;
	double beta;

	Node root;
	int row, col;
	public Node(HashMap<Integer, String> newConfig, double newScore){
		config = newConfig;
		score = newScore;
	}
	public HashMap<Integer, String> getConfig(){
		return config;
	}
	public double getScore(){
		return score;
	}
	public void setRoot(Node root){
		this.root = root;
	}
	public void addChild(Node node){
		if(player=='1')
			node.player='2';
		if(player=='2')
			node.player='1';
		node.oppMaterial=this.oppMaterial;
		//node.myMaterial=Connect4.updateMaterial(node.player, this.row, this.col, this.myMaterial, node.config);
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
	public HashMap<Integer, String> move(int col){
		HashMap<Integer, String> moved = config;
		String currConfig = config.get(col);
		System.out.println("before: " + currConfig);
		currConfig = currConfig.replaceFirst("0", ""+this.player);
		row = currConfig.lastIndexOf(this.player);
		System.out.println("after: " + currConfig);
		moved.replace(col, currConfig);
		return moved;
	}
	
}