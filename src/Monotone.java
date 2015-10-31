import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeSet;

public class Monotone {
	int V;
	ArrayList<Event> events = new ArrayList<>();
	ArrayList<Edge> edges = new ArrayList<>();
	TreeSet<Edge> T = new TreeSet<>();
	
	public Monotone() throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		File file = new File("input.txt");
		Scanner input = new Scanner(file);
		V = input.nextInt();
		for (int i=0;i<V;i++) {
			events.add(new Event(input.nextDouble(), input.nextDouble()));
		}
		for (int i=0;i<V;i++) {
			edges.add(new Edge(events.get(i),events.get((i+1)%V)));
		}
		Collections.sort(events, new CustomComparator());
		input.close();
		testPrint();
		System.out.println("Monotone");
		makeMonotone();
	}
	
	boolean eventCompare(Event e1, Event e2) {
		return ((e1.getX() == e2.getX()) && (e1.getY() == e2.getY()));
	}
	
	double getAngle(Edge first, Edge second) {
		double angle = 0;
		double a1 = first.end.getX() - first.start.getX();
		double b1 = first.end.getY() - first.start.getY();
		double a2 = second.end.getX() - second.start.getX();
		double b2 = second.end.getY() - second.start.getY();
		double f = Math.sqrt(a1*a1+b1*b1);
		double s = Math.sqrt(a2*a2+b2*b2);
		angle = Math.acos((a1*a2+b1*b2)/(f*s));
		return angle;
	}
	
	int getVertexType(Event e) {
		Edge first = null;
		Edge second = null;
		for (int i=0;i<V;i++) {
			Edge temp = edges.get(i);
			if (eventCompare(temp.end,e)) first = temp;
			else if (eventCompare(temp.start,e)) second = temp;
		}
		double angle = Math.toDegrees(getAngle(first, second));
		System.out.println(angle);
		return 1;
	}
	
	void handleStartVertex(Event e) {
		
	}
	
	void handleEndVertex(Event e) {
		
	}
	
	void handleSplitVertex(Event e) {
		
	}
	
	void handleMergeVertex(Event e) {
		
	}
	
	void handleRegularVertex(Event e) {
		
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
		}
	}
	
	void testPrint() {
		for(int i=0;i<V;i++) {
			System.out.println(events.get(i));
		}
		for(int i=0;i<V;i++) {
			System.out.println(edges.get(i));
		}
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
}
