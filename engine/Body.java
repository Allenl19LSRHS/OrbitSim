package orbitsim.engine;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Body {
	int mass;
	int posX;
	int posY;
	int posYOld;
	int posXOld;
	int velX;
	int velY;
	Circle circle;
	public boolean cycled = false;
	
	public int cycle = 0;
	
	// Upon creation, sets the given values, and creates the circle for the given starting position
	public Body(int m, int x, int y) {
		mass = m;
		posX = x;
		posY = y;
		circle = new Circle(posX, posY, mass, Color.WHITESMOKE);
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
	
	public int getOldX() {
		return posXOld;
	}
	
	public int getOldY() {
		return posYOld;
	}
	
	public Circle getCircle() {
		return circle;
	}
	
	public void setY(int y) {
		posYOld = posY;
		posY = y;
	}
	
	public void setX(int x) {
		posXOld = posX;
		posX = x;
	}
}
