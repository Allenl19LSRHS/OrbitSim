package orbitsim.engine;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import orbitsim.display.OrbitSim;
import orbitsim.display.TimelineManager;

public class Universe {
	ArrayList<Body> bodies = new ArrayList<Body>();
	TimelineManager timelineManager;
	OrbitSim main;
	
	public Universe(OrbitSim sim) {
		main = sim;
		bodies.add(new Body(10, 20, 20));
		bodies.add(new Body(20, 100, 100));
	}

	public ArrayList<Body> getBodies() {
		return bodies;
	}
	
	public void setTLMgr(TimelineManager tl) {
		timelineManager = tl;
	}
	
	public void cycle() {
		for (Body i : bodies) {
			Line l = new Line(i.getOldX(), i.getOldY(), i.getX(), i.getY());
			l.setStroke(Color.WHITESMOKE);
			main.getCanvas().getChildren().add(l);
			
			timelineManager.getTimeline().getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO, new KeyValue(i.getCircle().centerXProperty(), i.getX()), new KeyValue(i.getCircle().centerYProperty(), i.getY())),
					new KeyFrame(Duration.millis(OrbitSim.timeScale), timelineManager, new KeyValue(i.getCircle().centerXProperty(), i.getX() + 25 - i.cycle), new KeyValue(i.getCircle().centerYProperty(), i.getY() + 5))
				);
		}
		
		timelineManager.getTimeline().play();
	}
}
