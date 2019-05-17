package engine;


// Simple class to represent an x and y vector, with static methods for operations
public class Vector {
	private double xComp, yComp;
	
	public Vector(double x, double y) {
		xComp = x;
		yComp = y;
	}
	
	public double getX() {
		return xComp;
	}
	
	public double getY() {
		return yComp;
	}
	
	public static Vector addVec(Vector a, Vector b) {
		return new Vector(a.getX()+b.getX(), a.getY()+b.getY());
	}
}
