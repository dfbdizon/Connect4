import java.util.ArrayList;

class Tree{
	ArrayList<Node> children = new ArrayList<Node>();
	Node root;
	Tree(int col, int row, char player, Node root){
		children.add(new Node(root.move(1), Connect4.getStateScore2(player, col, row)));
		children.add(new Node(root.move(2), Connect4.getStateScore2(player, col, row)));
		children.add(new Node(root.move(3), Connect4.getStateScore2(player, col, row)));
		children.add(new Node(root.move(4), Connect4.getStateScore2(player, col, row)));
		children.add(new Node(root.move(5), Connect4.getStateScore2(player, col, row)));
		children.add(new Node(root.move(6), Connect4.getStateScore2(player, col, row)));
		children.add(new Node(root.move(7), Connect4.getStateScore2(player, col, row)));
		this.root = root;
	}
	public void setRootNode(Node node){
		root = node;
	}
	public void addChild(Node node){
		children.add(node);
	}
	public void removeChild(int index){
		children.remove(index);
	}
	public Node returnRoot(){
		return root;
	}
	public ArrayList<Node> getChildren(){
		return children;
	}
	public Node getChild(int index){
		return (children.get(index));
	}
}