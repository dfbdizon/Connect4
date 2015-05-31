class Node{
	HashMap<Integer, String> config;
	int score;
	public Node(HashMap<Integer, String> newConfig, int newScore){
		config = newConfig;
		score = newScore;
	}
	public HashMap<Integer, String> getConfig(){
		return config;
	}
	public int getScore(){
		return score;
	}
}