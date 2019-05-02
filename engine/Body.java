package orbitsim.engine;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Body {
	double mass;
	double posX;
	double posY;
	double posYOld;
	double posXOld;
	double velX = 0;
	double velY = 0;
	double queuedVelX = 0;
	double queuedVelY = 0;
	double queuedX = 0;
	double queuedY = 0;
	Circle circle;
	public boolean cycled = false;
	
	public int cycle = 0;
	
	// Upon creation, sets the given values, and creates the circle for the given starting position
	public Body(double m, int x, int y) {
		mass = m;
		posX = x;
		posXOld = x;
		posYOld = y;
		posY = y;
		if (Math.sqrt(2*mass) > 3) {
			circle = new Circle(posX, posY, Math.sqrt(mass*2), Color.WHITESMOKE);
		} else {
			circle = new Circle(posX, posY, 3, Color.WHITESMOKE);
		}
		
	}
	
	public Body(double m, int x, int y, double vx, double vy) {
		mass = m;
		posX = x;
		posXOld = x;
		posYOld = y;
		posY = y;
		velX = vx;
		velY = vy;
		if (Math.sqrt(1.5*mass) > 3) {
			circle = new Circle(posX, posY, Math.sqrt(mass*1.5), Color.WHITESMOKE);
		} else {
			circle = new Circle(posX, posY, 3, Color.WHITESMOKE);
		}
	}
	
	public double getMass() {
		return mass;
	}
	
	public double getX() {
		return posX;
	}
	
	public double getY() {
		return posY;
	}
	
	public double getOldX() {
		return posXOld;
	}
	
	public double getOldY() {
		return posYOld;
	}
	
	public void resetOldPos() {
		posYOld = posY;
		posXOld = posX;
	}
	
	public Circle getCircle() {
		return circle;
	}
	
	public void setY(double y) {
		posY = y;
	}
	
	public void setX(double x) {
		posX = x;
	}
	
	public double getVelX() {
		return velX;
	}
	
	public double getVelY() {
		return velY;
	}
	
	public void setVelX(double v) {
		velX = v;
	}
	
	public void setVelY(double v) {
		velY = v;
	}
	
	public void queuePos(double x, double y) {
		queuedX = x;
		queuedY = y;
	}
	
	public void queueVel(double x, double y) {
		queuedVelX = x;
		queuedVelY = y;
	}
	
	public void updateQueuedData() {
		posX = queuedX;
		posY = queuedY;
		velX = queuedVelX;
		velY = queuedVelY;
	}
}
