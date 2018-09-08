package shortest_paths;
/**
 * Written by Christina Alexander
 * Student ID W1672077
 * 
 * This code is written for the Algorithms: Theory, Design and Implementation
 * course at the University of Westminster for the Spring semester of 2018.
 */
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Christina Alexander
 * This program calculates the shortest path between two nodes using the heuristic based
 * A* algorithm
 *
 */

public class ShortestPath {
	private Node[][] Grid;
	private String metric;
	private double straightCost, diagCost;
	private Node start,end;
	
	public ShortestPath(Node[][] grid, String Metric) {
		this.Grid = grid;
		this.metric = Metric;
		switch (metric) {
			case "Manhattan":
				straightCost = 1;
				diagCost = 2;
				break;
			case "Euclidean":
				straightCost = 1;
				diagCost = Math.sqrt(2);
				break;
			case "Chebyshev":
				straightCost = 1;
				diagCost = 1;
				break;
		}			
	}
	
	public final List<Node> aStarAlgorithm(int startX,int startY, int endX,int endY){
		LinkedList<Node> open = new LinkedList<Node>(), closed = new LinkedList<Node>();
		if (startX == endX && startY == endY) {
			return open;
		}
		start = Grid[startX][startY];
		//System.out.println(startX + " " + startY);
		end = Grid[endX][endY];
		
		Node curr;
		
		closed.add(start);
		
		while (true) {
			//System.out.println("In the loop");
			curr = lowestF(closed);
			closed.remove(curr);
			open.add(curr);
			//System.out.println(curr.x + " " + curr.y);
			if (curr.x == end.x && curr.y == end.y) {
				//System.out.print("We found it!");
				return BuildPath();
			}
			
			List<Node> neighbors = getNeighbors(curr,open);
			for(Node neighbor : neighbors) {
				//System.out.println("Getting the neighbors");
				if (!closed.contains(neighbor)) {
					neighbor.setParent(curr);
					neighbor.setH(neighbor, end, metric);
					//System.out.println("Added a node!");
					closed.add(neighbor);
				}
				else if (neighbor.getG() > (curr.getG() + neighbor.cost)) {
					neighbor.setParent(curr);
					neighbor.setG(curr.getG() + neighbor.cost);
				}
			}
			if(closed.isEmpty()) {
				return new LinkedList<Node>();
			}
		}
	}
	
	private List<Node> BuildPath(){
		LinkedList<Node> path = new LinkedList<Node>();
		Node node = end;
		boolean done = false;
		path.addFirst(node);
		while(!done) {
			path.addFirst(node.getParent());
			node = node.getParent();
			if(node.equals(start)) {
				done = true;
			}
			
		}
		return path;
	}
	
	public List<Node> getNeighbors(Node curr, List<Node> open) {
		//System.out.println(curr.cost);
		List<Node> neighbors = new LinkedList<Node>();
		Node neighbor;
		if (curr.y - 1 >= 0) {
			
			neighbor = Grid[curr.x][curr.y-1];
				if (neighbor.cost != 0 && !open.contains(neighbor)) {
					//System.out.println("first");
					neighbor.setG(curr.getG() + (straightCost * neighbor.cost));
					neighbors.add(neighbor);
				}
		}
		if (curr.y+1 < 20) {
			
			neighbor = Grid[curr.x][curr.y+1];
			if (neighbor.cost != 0 && !open.contains(neighbor)) {
				//System.out.println("Second");
				neighbor.setG(curr.getG() + (straightCost * neighbor.cost));
				neighbors.add(neighbor);
			}
		}	
		if (curr.x-1 >= 0) {
			neighbor = Grid[curr.x-1][curr.y];
			if (neighbor.cost != 0 && !open.contains(neighbor)) {
				//System.out.println("Third");
				neighbor.setG(curr.getG() + (straightCost * neighbor.cost));
				neighbors.add(neighbor);
			}
			if(curr.y-1 >= 0) {
			neighbor = Grid[curr.x-1][curr.y-1];
				if (neighbor.cost != 0 && !open.contains(neighbor)) {
					//System.out.println("Fourth");
					neighbor.setG(curr.getG() + (diagCost * neighbor.cost));
					neighbors.add(neighbor);
				}
			}
			if (curr.y+1 < 20) {
				neighbor = Grid[curr.x-1][curr.y+1];
				if (neighbor.cost != 0 && !open.contains(neighbor)) {
					//System.out.println("Fifth");
					neighbor.setG(curr.getG() + (diagCost * neighbor.cost));
					neighbors.add(neighbor);
				}
			}
		}
		if (curr.x+1 < 20) {
			neighbor = Grid[curr.x+1][curr.y];
			if (neighbor.cost != 0 && !open.contains(neighbor)) {
				//System.out.println("Sixth");
				neighbor.setG((curr.getG() + (straightCost * neighbor.cost)));
				neighbors.add(neighbor);
			}
			if (curr.y+1 < 20) {
				neighbor = Grid[curr.x+1][curr.y+1];
				if (neighbor.cost != 0 && !open.contains(neighbor)) {
					//System.out.println("Seventh");
					neighbor.setG(curr.getG() + (diagCost * neighbor.cost));
					neighbors.add(neighbor);
				}
			}
			if (curr.y-1 >= 0) {
				neighbor = Grid[curr.x+1][curr.y-1];
				if (neighbor.cost != 0 && !open.contains(neighbor)) {
					//System.out.println("Eighth");
					neighbor.setG(curr.getG() + (diagCost * neighbor.cost));
					neighbors.add(neighbor);
				}
			}
		}
			
		//System.out.println("List Size: " + neighbors.size());
	return neighbors;
}
	
	private Node lowestF(List<Node> list) {
		Node theLowest = list.get(0);
		for (int i = 0; i < list.size(); ++i) {
			if (theLowest.getG() > list.get(i).getG())
				theLowest = list.get(i);
		}
		return theLowest;
	}
}
