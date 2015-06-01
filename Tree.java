import java.util.ArrayList;

class Tree{
	ArrayList<Node> children = new ArrayList<Node>();
	Node root;
	String minOrMax;
	public void setRootNode(Node node){
		root = node;
	}
	public void addChild(Node node){
		children.add(node);
	}
	public void removeChild(int index){
		children.remove(index);
	}
	public Node getRoot(){
		return root;
	}
	public ArrayList<Node> getChildren(){
		return children;
	}
	public Node getChild(int index){
		return (children.get(index));
	}
}