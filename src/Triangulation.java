import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Stack;

public class Triangulation {
	
	ArrayList<ArrayList<Event>> monotones = new ArrayList<>();
	int d;
	
	public Triangulation() throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		File file = new File("input1.txt");
		Scanner input = new Scanner(file);
		d = input.nextInt();
		for (int i=0;i<d;i++) {
			int V = input.nextInt();
			ArrayList<Event> events = new ArrayList<>();
			for (int j=0;j<V;j++) {
				events.add(new Event(input.nextDouble(), input.nextDouble(),j+1));
			}
			monotones.add(events);
		}
		input.close();
		System.out.println("Triangulation");
		triangulate();
		//testPrint();
	}
	
	double ccw(Event e1, Event e2, Event e3) {
		return ((e2.getX() - e1.getX()) * (e3.getY() - e1.getY()) - (e2.getY() - e1.getY()) * (e3.getX() - e1.getX()));
	}
	
	void triangulate() {
		for (int i=0;i<d;i++) {
			System.out.println("--- Monotone " + i + "---");
			/*
			 * merge left and right chain, sorted
			 */
			ArrayList<Event> events = monotones.get(i);
			Collections.sort(events, new CustomComparator());
			//for (int j=0;j<events.size();j++) System.out.println(events.get(j));
			int size = events.size();
			Event top = events.get(0);
			//System.out.println(top);
			Event bot = events.get(size-1);
			//System.out.println(bot);
			int nextID = top.getId()%size + 1;
			while(nextID != bot.getId()) {
				for (int j=0;j<size;j++) {
					if (events.get(j).getId() == nextID) {
						events.get(j).setLeftChain(true);
						break;
					}
				}
				nextID = nextID%size + 1;
			}
			//for (int j=0;j<events.size();j++) System.out.println(events.get(j));
			// triangulation
			Stack<Event> stack = new Stack<>();
			ArrayList<Edge> D = new ArrayList<>();
			stack.push(events.get(0));
			stack.push(events.get(1));
			for (int j=2;j<size-1;j++) {
				Event e = events.get(j);
				System.out.println("this " + e);
				if (e.getLeftChain() != stack.peek().getLeftChain()) {
					Event s;
					while(true) {
						s = stack.pop();
						if (stack.isEmpty()) break;
						D.add(new Edge(e, s));
					}
					stack.push(events.get(j-1));
					stack.push(e);
				}
				else {
					Event last = stack.pop();
					Event s = null;
					while(true) {
						s = stack.peek();
						System.out.println("top " + s);
						Event next = null;
						for (int k=0;k<size;k++) {
							if (events.get(k).getId() == s.getId()%size + 1) {
								next = events.get(k);
								break;
							}
						}
						System.out.println("next " + next);
						if (ccw(s,next,e)>0) {
							D.add(new Edge(s, e));
							last = s;
							stack.pop();
						}
						else break;
						if (stack.isEmpty()) break;

					}
					stack.push(last);
					stack.push(e);
				}
			}
			// print D
			for (int k=0;k<D.size();k++) System.out.println(D.get(k));
			
		}
	}
	
	void testPrint() {
		for (int i=0;i<d;i++) {
			System.out.println(i);
			ArrayList<Event> events = monotones.get(i);
			Collections.sort(events, new CustomComparator());
			int size = events.size();
			for (int j=0;j<size;j++) System.out.println(events.get(j));
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
	
	public static void main(String[] args) throws IOException {
		Monotone m = new Monotone();
		Triangulation t = new Triangulation();
	}

}
