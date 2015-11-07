
public class Event {
	int id;
	double x;
	double y;
		
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public int getId() {
		return id;
	}

	public Event(double x, double y, int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = "v" + id + " " + x + " " + y;
		return s;
	}

}
