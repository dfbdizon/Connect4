import java.util.HashMap;

class Node{
	HashMap<Integer, String> config;
	double score;
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
}