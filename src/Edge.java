
public class Edge {
	Event start;
	Event end;
	Event helper;
	
	public Event getStart() {
		return start;
	}
	
	public Event getEnd() {
		return end;
	}
	
	public Edge(Event start, Event end) {
		// TODO Auto-generated constructor stub
		this.start = start;
		this.end = end;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = start + " " + end;
		return s;
	}

}
