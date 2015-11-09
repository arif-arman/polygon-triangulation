
public class Edge {
	int id;
	Event start;
	Event end;
	Event helper;
	Event top;
	Event bot;
	
	Event left;
	
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
	
	public double getLeft() {
		return left.getX();
	}
	
	public void setLeft() {
		if (start.getX() <= end.getX()) left = start;
		else left = end;
	}
	
	// return top most vertex of edge according to Y coordinate
	void setTop() {
		if (start.getY() >= end.getY()) {
			top = start;
			bot = end;
		}
		else {
			top = end;
			bot = start;
		}
			
	}
	public double getTop() {
		return top.getY();
	}
	
	public void setHelper(Event helper) {
		this.helper = helper;
	}
	
	public Edge(Event start, Event end, int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.start = start;
		this.end = end;
		setTop();
		setLeft();
	}
	
	public Edge(Event start, Event end) {
		// TODO Auto-generated constructor stub
		this.start = start;
		this.end = end;
		setTop();
		setLeft();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = "e" + id + " " + start + " " + end + " h " + helper;
		return s;
	}

}
