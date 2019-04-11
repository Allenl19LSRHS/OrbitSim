package orbitsim.engine;

public class Vector {
	double xComp;
	double yComp;
	
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
