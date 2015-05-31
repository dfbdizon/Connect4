import java.util.HashMap;

class Node{
	HashMap<Integer, String> config;
	double score;
	double myMaterial;
	double oppMaterial;
	char player; //kung sinong nagplay ng move na yon

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
	public HashMap<Integer, String> move(int col){
		HashMap<Integer, String> moved = config;
		String currConfig = config.get(col);
		System.out.println("before: " + currConfig);
		currConfig = currConfig.replaceFirst("0", "1");
		System.out.println("after: " + currConfig);
		moved.replace(col, currConfig);
		return moved;
	}
}