package orbitsim.engine;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Body {
	double mass;
	int posX;
	int posY;
	int posYOld;
	int posXOld;
	double velX = 0;
	double velY = 0;
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
		if (mass > 3) {
			circle = new Circle(posX, posY, mass, Color.WHITESMOKE);
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
		if (mass < 3) {
			circle = new Circle(posX, posY, 3, Color.WHITESMOKE);
		} else if (mass > 20) {
			circle = new Circle(posX, posY, 20, Color.WHITESMOKE);
		} else {
			circle = new Circle(posX, posY, mass, Color.WHITESMOKE);
		}
	}
	
	public double getMass() {
		return mass;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public int getOldX() {
		return posXOld;
	}
	
	public int getOldY() {
		return posYOld;
	}
	
	public void resetOldPos() {
		posYOld = posY;
		posXOld = posX;
	}
	
	public Circle getCircle() {
		return circle;
	}
	
	public void setY(int y) {
		posY = y;
	}
	
	public void setX(int x) {
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
}
