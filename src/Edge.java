
public class Edge {
	int id;
	Event start;
	Event end;
	Event helper;
	
	public Event getStart() {
		return start;
	}
	
	public Event getEnd() {
		return end;
	}
	
	public Event getHelper() {
		return helper;
	}
	
	public int getId() {
		return id;
	}
	
	// return left most vertex of edge according to X coordinate
	public double getLeft() {
		if (start.getX() <= end.getX()) return start.getX();
		else return end.getX();
	}
	
	public void setHelper(Event helper) {
		this.helper = helper;
	}
	
	public Edge(Event start, Event end, int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.start = start;
		this.end = end;
	}
	
	public Edge(Event start, Event end) {
		// TODO Auto-generated constructor stub
		this.start = start;
		this.end = end;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = "e" + id + " " + start + " " + end + " h " + helper;
		return s;
	}

}
