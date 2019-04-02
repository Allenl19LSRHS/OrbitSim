package orbitsim.engine;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
	Timeline timeline;
	TimelineManager timelineManager;
	Group canvas;
	
	int cycle = 0;
	
	// Upon creation, sets the given values, and creates the circle for the given starting position
	public Body(int m, int x, int y, Group c, TimelineManager tlmgr) {
		mass = m;
		posX = x;
		posY = y;
		posXOld = x;
		posYOld = y;
		circle = new Circle(posX, posY, mass, Color.WHITESMOKE);
		timelineManager = tlmgr;
		timeline = timelineManager.getTimeline();
		canvas = c;
	}
	
	
	public void addToTimeline() {
		timeline.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, new KeyValue(getCircle().translateXProperty(), posX), new KeyValue(getCircle().translateYProperty(), posY)),
				new KeyFrame(new Duration(OrbitSim.timeScale), timelineManager, new KeyValue(getCircle().translateXProperty(), posX + 25 - cycle), new KeyValue(getCircle().translateYProperty(), posY + 5))
			);
		posYOld = posY;
		posXOld = posX;
		posX += 25-cycle;
		posY += 5;
		Line l = new Line(getOldX(), getOldY(), getX(), getY());
		l.setFill(Color.WHITE);
		l.setStroke(Color.WHITE);
		canvas.getChildren().add(l);
		cycle++;
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
