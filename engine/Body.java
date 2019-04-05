package orbitsim.engine;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import orbitsim.display.OrbitSim;
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
	boolean cycled = false;
	
	int cycle = 0;
	
	// Upon creation, sets the given values, and creates the circle for the given starting position
	public Body(int m, int x, int y, TimelineManager tlmgr) {
		mass = m;
		posX = x;
		posY = y;
		posXOld = x;
		posYOld = y;
		circle = new Circle(posX, posY, mass, Color.WHITESMOKE);
		timelineManager = tlmgr;
	}
	
	public void addToTimeline() {
		posXOld = posX;
		posYOld = posY;
		posX += 25-cycle;
		posY += 5;
		if (cycle <= 25 && cycled == false) {
			cycle++; 
		} else {
			cycled = true;
			cycle--;
			if (cycle < 1) {
				cycled = false;
			}
		}
		timelineManager.getTimeline().getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, new KeyValue(getCircle().centerXProperty(), posX), new KeyValue(getCircle().centerYProperty(), posY)),
				new KeyFrame(Duration.millis(OrbitSim.timeScale), timelineManager, new KeyValue(getCircle().centerXProperty(), posX + 25 - cycle), new KeyValue(getCircle().centerYProperty(), posY + 5))
			);
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
}
