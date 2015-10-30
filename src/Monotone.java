import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Monotone {
	int V;
	ArrayList<Event> events = new ArrayList<>();
	ArrayList<Edge> edges = new ArrayList<>();
	
	public Monotone() throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		File file = new File("input.txt");
		Scanner input = new Scanner(file);
		V = input.nextInt();
		for (int i=0;i<V;i++) {
			events.add(new Event(input.nextDouble(), input.nextDouble()));
		}
		for (int i=V-1;i>=0;i--) {
			if (i==0) edges.add(new Edge(events.get(i),events.get(V-1)));
			else edges.add(new Edge(events.get(i),events.get(i-1)));
		}
		Collections.sort(events, new CustomComparator());
		input.close();
		testPrint();
	}
	
	public void makeMonotone() {
		for (int i=0;i<V;i++) {
			Event v = events.get(i);
			
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
