package shortest_paths;
/**
 * Written by Christina Alexander
 * Student ID W1672077
 * 
 * This code is written for the Algorithms: Theory, Design and Implementation
 * course at the University of Westminster for the Spring semester of 2018.
 */

public class Node {
	public final int x,y,cost;
	private double g,h,totalCost;
	private Node Parent;
	
	Node(int xCurr,int yCurr,int num){
		cost = num;
		x = xCurr;
		y = yCurr;
		g = -1;
		Parent = null;
	}
	
	public void setG(double g) {
		this.g = g;
	}
	
	public double getG() {
		return this.g;
	}
	
	public double getTotal() {
		return this.totalCost;
	}
	
	public void setTotal(double cost) {
		this.totalCost = cost;
	}
	
	public void setParent(Node parent) {
		this.Parent = parent;
	}
	
	public Node getParent() {
		return this.Parent;
	}

	public void setH(Node curr, Node goal, String type) {
		if (type.equals("Manhattan")) {
			double dx = Math.abs((double) curr.x - goal.x);
			double dy = Math.abs((double) curr.y - goal.y);
			h = dx + dy;
		}
		else if (type.equals("Euclidean")) {
			double dxSquare = Math.pow(goal.x - curr.x, 2);
			double dySquare = Math.pow(goal.y - curr.y, 2);
			h = Math.sqrt(dxSquare + dySquare);
		}
		if (type.equals("Chebyshev")) {
			double dx = Math.abs((double) curr.x - goal.x);
			double dy = Math.abs((double) curr.y - goal.y);
			h = dx + dy + (-1) * Math.min(dx, dy);
		}
	}
	
	public double getH() {
		return this.h;
	}


	public String coordsString() {
		return "[" + x + "," + y + "]";
	}
}
