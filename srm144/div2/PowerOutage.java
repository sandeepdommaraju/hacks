import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PowerOutage {
	
	public int estimateTimeOut(int[] fromJunction, int[] toJunction, int[] ductLength){
		int traversalTime = 0;
		int maxNodePosition = this.getMaximum(fromJunction);
		maxNodePosition = Math.max(maxNodePosition, this.getMaximum(toJunction));
		List<Node> nodesList = this.createNodes(maxNodePosition);
		this.createTree(fromJunction, toJunction, ductLength, nodesList);
		Collections.sort(nodesList, new Comparator<Node>(){
				public int compare(Node n1, Node n2){
					return n1.nodePosition - n2.nodePosition;
				}
		});
		traversalTime = this.computeTraversalTime(nodesList.get(0));
		return traversalTime;
	}
	
	public class Node{
		int nodePosition = 0;
		int fromPosition = 0;
		List<Node> childNodes = null;
		int length = 0;
		int traversalTime = 0;
		boolean lastTraversed = false;
		
		public Node(int nodePosition){
			this.nodePosition = nodePosition;
		}
	}
	
	public void createTree(int[] fromJunction, int[] toJunction, int[] ductLength, List<Node> nodesList){
		for(int i=0;i<fromJunction.length;i++){
			Node node = this.getNodeFromNodesList(fromJunction[i], nodesList);
			if(node.childNodes==null) node.childNodes = new ArrayList<Node>();
			Node childNode = this.getNodeFromNodesList(toJunction[i], nodesList);
			childNode.fromPosition = node.nodePosition;
			childNode.length = ductLength[i];
			node.childNodes.add(childNode);
		}
	}
	
	public Node getNodeFromNodesList(int fromJunction, List<Node> nodesList){
		for(Node node: nodesList){
			if(node.nodePosition==fromJunction)
				return node;
		}
		return null;
	}
	
	private List<Node> createNodes(int maxNodePosition){
		List<Node> nodesList = new ArrayList<Node>();
		for(int i=0;i<=maxNodePosition;i++){
			Node node = new Node(i);
			nodesList.add(node);
		}
		return nodesList;
	}
	
	private int getMaximum(int[] junction){
		int maximum = -1;
		for(int i=0;i<junction.length;i++){
			if(junction[i]>maximum)
				maximum = junction[i];
		}
		return maximum;
	}
	
	private int computeTraversalTime(Node node){
		List<Node> childNodes = node.childNodes;
		if(childNodes==null) return 0;
		for(Node childNode: childNodes)
			this.computeTraversalTime(childNode);
		Collections.sort(childNodes, new Comparator<Node>(){
			public int compare(Node c1, Node c2){
				return c1.traversalTime + 2*c1.length - c2.traversalTime - 2*c2.length;
			}
		});
		for(int i=0; i<childNodes.size(); i++){
			Node childNode = childNodes.get(i);
			node.traversalTime += childNode.traversalTime + 2*childNode.length;
			if(i==childNodes.size()-1)
				childNode.lastTraversed = true;
		}
		
		if(node.nodePosition==0){
			this.reduceLastTraversal(node, node);
		}
		
		return node.traversalTime;
	}
	
	private void reduceLastTraversal(Node rootNode, Node node){
		List<Node> childNodes = node.childNodes;
		if(childNodes==null) return;
		Node lastNode = this.getLastTraversedChildNode(node);
		if(lastNode==null) return;
		this.reduceLastTraversal(rootNode, lastNode);
		rootNode.traversalTime -= lastNode.length;
	}
	
	private Node getLastTraversedChildNode(Node node){
		List<Node> childNodes = node.childNodes;
		if(childNodes==null) return null;
		for(Node childNode: childNodes)
			if(childNode.lastTraversed)
				return childNode;
		return null;
	}
}