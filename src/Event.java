
public class Event {
	double x;
	double y;
		
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Event(double x, double y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = x + " " + y;
		return s;
	}

}
