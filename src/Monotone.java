import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Monotone {
	int V;
	ArrayList<Event> events = new ArrayList<>();
	ArrayList<Edge> edges = new ArrayList<>();
	ArrayList<Edge> T = new ArrayList<>();
	ArrayList<Edge> D = new ArrayList<>();
	PrintWriter out;
	
	public Monotone() throws IOException {
		// TODO Auto-generated constructor stub
		File file = new File("input.txt");
		Scanner input = new Scanner(file);
		V = input.nextInt();
		for (int i=0;i<V;i++) {
			events.add(new Event(input.nextDouble(), input.nextDouble(),i+1));
		}
		for (int i=0;i<V;i++) {
			edges.add(new Edge(events.get(i),events.get((i+1)%V),(i+1)%(V+1)));
		}
		out = new PrintWriter(new BufferedWriter(new FileWriter("output.txt", false)));
		Collections.sort(events, new CustomComparator());
		input.close();
		//testPrint();
		System.out.println("Monotone");
		makeMonotone();
		fileOut();
		out.close();
		
	}
	
	boolean eventCompare(Event e1, Event e2) {
		return ((e1.getX() == e2.getX()) && (e1.getY() == e2.getY()));
	}
	
	double ccw(Event e1, Event e2, Event e3) {
		return ((e2.getX() - e1.getX()) * (e3.getY() - e2.getY()) - (e2.getY() - e1.getY()) * (e3.getX() - e2.getX()));
	}
	
	int getVertexType(Event e) {
		Edge first = null;
		Edge second = null;
		for (int i=0;i<V;i++) {
			Edge temp = edges.get(i);
			if (eventCompare(temp.end,e)) first = temp;
			else if (eventCompare(temp.start,e)) second = temp;
		}
		
		// conditions for start and split vertex
		if (e.getY() > first.start.getY() && e.getY() > second.end.getY()) {
			if (ccw(first.start, first.end, second.end) > 0) return 1;
			else return 3;				
		}
		// conditions for end and merge vertex
		else if (e.getY() < first.start.getY() && e.getY() < second.end.getY()) {
			if (ccw(first.start, first.end, second.end) > 0) return 2;
			else return 4;
		}
		// regular otherwise
		else return 5;
	}
	
	// unfinished
	int getLeftEdge(Event e) {
		Collections.sort(T, new CustomComparator1());
		int size = T.size();
		Edge left = null;
		for (int i=0;i<size;i++) {
			Edge temp = T.get(i);
			//System.out.println(temp + " " + temp.getLeft());
			if (left == null) left = temp;
			else if ((temp.getLeft() < e.getX()) && (temp.getLeft() > left.getLeft()) 
					&& (temp.start.getY() >= e.getY()) && (temp.end.getY() <= e.getY()))
				left = temp;
		}
		return left.getId();
	}
	
	void handleStartVertex(Event e) {
		System.out.println(e + " -- start" );
		edges.get(e.getId()-1).setHelper(e);
		T.add(edges.get(e.getId()-1));
		
		//System.out.println(edges.get(e.getId()-1));
	}
	
	void handleEndVertex(Event e) {
		System.out.println(e + " -- end" );
		// get previous edge
		Edge temp;
		if (e.getId() == 1) temp = edges.get(V-1); 
		else temp = edges.get(e.getId()-2);
		//System.out.println("Prev edge : " + temp);
		// check if helper of prev edge is merge vertex
		if (temp.getHelper() != null && getVertexType(temp.getHelper()) == 4) {
			D.add(new Edge(e, temp.getHelper()));
		}
		// remove previous edge from T
		for (int i=0;i<T.size();i++) {
			if (T.get(i).getId() == e.getId()-1) {
				T.remove(i);
				break;
			}
		}
		
	}
	
	void handleSplitVertex(Event e) {
		System.out.println(e + " -- split" );
		// get left edge	
		int leftID = getLeftEdge(e);
		//System.out.println("Left edge: e"+leftID);
		D.add(new Edge(e, edges.get(leftID-1).getHelper()));
		edges.get(leftID-1).setHelper(e);
		// helper of ei is vi
		edges.get(e.getId()-1).setHelper(e);
		T.add(edges.get(e.getId()-1));
		
		
	}
	
	void handleMergeVertex(Event e) {
		System.out.println(e + " -- merge" );
		// get previous edge
		Edge temp;
		if (e.getId() == 1) temp = edges.get(V-1); 
		else temp = edges.get(e.getId()-2);
		//System.out.println("Prev edge : " + temp);
		// check if helper of prev edge is merge vertex
		if (temp.getHelper() != null && getVertexType(temp.getHelper()) == 4) {
			D.add(new Edge(e, temp.getHelper()));
		}
		// remove previous edge from T
		for (int i=0;i<T.size();i++) {
			if (T.get(i).getId() == e.getId()-1) {
				T.remove(i);
				break;
			}
		}
		// get left edge
		int leftID = getLeftEdge(e);
		//System.out.println("e"+leftID);
		// if helper of left edge is a merge vertex
		if (getVertexType(edges.get(leftID-1).getHelper()) == 4) 
			D.add(new Edge(e, edges.get(leftID-1).getHelper()));
		edges.get(leftID-1).setHelper(e);
		
	}
	
	void handleRegularVertex(Event e) {
		System.out.println(e + " -- regular" );
		// check if left regular
		int nextID = e.getId() + 1, prevID = e.getId() - 1;
		if (prevID == 0) prevID = V;
		else if (nextID == V+1) nextID = 1;
		double prevY=0, nextY=0;
		for (int i=0;i<V;i++) {
			if (events.get(i).getId() == prevID) prevY = events.get(i).getY();
			else if (events.get(i).getId() == nextID) nextY = events.get(i).getY();
		}
		//System.out.println(prevID + " " + prevY + " " + nextID + " " + nextY);
		if (prevY >= nextY) {
			// get previous edge
			Edge temp;
			if (e.getId() == 1) temp = edges.get(V-1); 
			else temp = edges.get(e.getId()-2);
			//System.out.println("Prev edge : " + temp);
			// check if helper of prev edge is merge vertex
			if (temp.getHelper() != null && getVertexType(temp.getHelper()) == 4) {
				D.add(new Edge(e, temp.getHelper()));
			}
			// remove previous edge from T
			for (int i=0;i<T.size();i++) {
				if (T.get(i).getId() == e.getId()-1) {
					T.remove(i);
					break;
				}
			}
			edges.get(e.getId()-1).setHelper(e);
			T.add(edges.get(e.getId()-1));
		}
		else {
			// get left edge
			int leftID = getLeftEdge(e);
			//System.out.println("e"+leftID);
			// if helper of left edge is a merge vertex
			if (getVertexType(edges.get(leftID-1).getHelper()) == 4) 
				D.add(new Edge(e, edges.get(leftID-1).getHelper()));
			edges.get(leftID-1).setHelper(e);
			
		}
		
	}
	
	public void makeMonotone() {
		for (int i=0;i<V;i++) {
			Event e = events.get(i);
			int s = getVertexType(e);
			switch (s) {
			case 1:
				handleStartVertex(e);
				break;
			case 2:
				handleEndVertex(e);
				break;
			case 3:
				handleSplitVertex(e);
				break;
			case 4:
				handleMergeVertex(e);
				break;
			case 5:
				handleRegularVertex(e);
				break;
			}
			//testPrint();
		}
		testPrint();
	}
	
	void fileOut() {
		out.println(D.size());
		for (int i=0;i<D.size();i++) 
			out.println(D.get(i).start.getX() + " " + D.get(i).start.getY() + " " + D.get(i).end.getX() + " " + D.get(i).end.getY());
	}
	
	void testPrint() {
		/*
		for(int i=0;i<V;i++) {
			System.out.println(events.get(i));
		}
		for(int i=0;i<V;i++) {
			System.out.println(edges.get(i));
		}
		
		// print T
		System.out.println("--- Tree ---");
		for (int i=0;i<T.size();i++) System.out.println(T.get(i));
		System.out.println("--- Tree End ---");
		*/
		// print D
		System.out.println("--- Diagonals ---");
		for (int i=0;i<D.size();i++) System.out.println(D.get(i));
		System.out.println("--- Diagonals End ---");
	}
	
	class CustomComparator implements Comparator<Event> {
	    @Override
	    public int compare(Event o1, Event o2) {
	    	if (o1.getY() == o2.getY()) {
	    		if (o1.getX() > o2.getX()) return 1;
	    		else if (o1.getX() < o2.getX()) return -1;
	    		return 0;
	    	}
	        return Double.compare(o2.getY(), o1.getY());
	    }
	}
	class CustomComparator1 implements Comparator<Edge> {
	    @Override
	    public int compare(Edge o1, Edge o2) {
	    	return Double.compare(o1.getLeft(), o2.getLeft());
	    }
	}
}
