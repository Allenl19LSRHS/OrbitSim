package orbitsim.engine;

import javafx.scene.shape.Circle;

public class Body {
	int mass;
	int posX;
	int posY;
	int velX;
	int velY;
	Circle circle;
	
	// Upon creation, sets the given values, and creates the circle for the given starting position
	public Body(int m, int x, int y) {
		mass = m;
		posX = x;
		posY = y;
		circle = new Circle(posX, posY, mass);
	}
	
	
	
	
	public int getMass() {
		return mass;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public Circle getCircle() {
		return circle;
	}
}
