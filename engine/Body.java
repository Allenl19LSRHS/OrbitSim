package orbitsim.engine;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import orbitsim.display.TimelineManager;

public class Body {
	int mass;
	int posX;
	int posY;
	int posYOld;
	int posXOld;
	int velX;
	int velY;
	Circle circle;
	TimelineManager timelineManager;
	public boolean cycled = false;
	
	public int cycle = 0;
	
	// Upon creation, sets the given values, and creates the circle for the given starting position
	public Body(int m, int x, int y, TimelineManager tlmgr) {
		mass = m;
		posX = x;
		posY = y;
		circle = new Circle(posX, posY, mass, Color.WHITESMOKE);
		timelineManager = tlmgr;
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
	
	public void setOldX(int x) {
		posXOld = x;
	}
	
	public void setOldY(int y) {
		posYOld = y;
	}
	
	public void setY(int y) {
		posY = y;
	}
	
	public void setX(int x) {
		posX = x;
	}
}
