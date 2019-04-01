package orbitsim.engine;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import orbitsim.display.TimelineManager;

public class Body {
	int mass;
	int posX;
	int posY;
	int velX;
	int velY;
	Circle circle;
	Timeline timeline;
	TimelineManager timelineManager;
	
	// Upon creation, sets the given values, and creates the circle for the given starting position
	public Body(int m, int x, int y, Timeline tl, TimelineManager tlmgr) {
		mass = m;
		posX = x;
		posY = y;
		circle = new Circle(posX, posY, mass, Color.WHITESMOKE);
		timeline = tl;
		timelineManager = tlmgr;
	}
	
	
	public void addToTimeline() {
		timeline.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, new KeyValue(getCircle().translateXProperty(), posX), new KeyValue(getCircle().translateYProperty(), posY)),
				new KeyFrame(new Duration(250), timelineManager, new KeyValue(getCircle().translateXProperty(), posX + 10), new KeyValue(getCircle().translateYProperty(), posY + 10))
			);
		posX += 10;
		posY += 10;
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
