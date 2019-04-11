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
	static final int CONSTANT_G = 2000;
	
	public Universe(OrbitSim sim) {
		main = sim;
		bodies.add(new Body(10, 200, 200));
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
			ArrayList<Vector> vecs = new ArrayList<Vector>();
			for (Body a:bodies) {
				if (a != i) {
					vecs.add(new Vector(0, 0));
				}
			}
			
			Vector finalVec = new Vector(5,0);
			for (Vector b : vecs) {
				finalVec = Vector.addVec(finalVec, b);
			}
			
			i.setX(i.getX() + (int)finalVec.getX());
			i.setY(i.getY() + (int)finalVec.getY());
			
			Line l = new Line(i.getOldX(), i.getOldY(), i.getX(), i.getY());
			l.setStroke(Color.WHITESMOKE);
			main.getCanvas().getChildren().add(l);
			
			timelineManager.getTimeline().getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO, new KeyValue(i.getCircle().centerXProperty(), i.getOldX()), new KeyValue(i.getCircle().centerYProperty(), i.getOldY())),
					new KeyFrame(Duration.millis(OrbitSim.timeScale), timelineManager, new KeyValue(i.getCircle().centerXProperty(), i.getX()), new KeyValue(i.getCircle().centerYProperty(), i.getY()))
				);
		}
		
		timelineManager.getTimeline().play();
	}
}
