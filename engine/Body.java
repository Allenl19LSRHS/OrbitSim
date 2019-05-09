package orbitsim.engine;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Body {
	private double mass, posX, posY, posYOld, posXOld, velX = 0, velY = 0, queuedVelX = 0,
			queuedVelY = 0, queuedX = 0, queuedY = 0;
	private Circle circle;
	private Color color;
	
	// Upon creation, sets the given values, and creates the circle for the given starting position
	
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
	
	void setColor(int i) {
		switch(i) {
		case 0:
			color = Color.WHITESMOKE;
			break;
		case 1:
			color = Color.BLUE;
			break;
		case 2:
			color = Color.YELLOW;
			break;
		case 3:
			color = Color.RED;
			break;
		case 4:
			color = Color.ORANGE;
			break;
		case 5:
			color = Color.GREEN;
			break;
		}
		
		circle.setFill(color);
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
	
	public Color getColor() {
		return color;
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
