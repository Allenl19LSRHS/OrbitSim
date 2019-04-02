package orbitsim.display;

import java.util.ArrayList;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import orbitsim.engine.Body;

public class TimelineManager implements EventHandler<ActionEvent> {
	
	ArrayList<Body> bodies;
	Group canvas;
	
	public TimelineManager(ArrayList<Body> bodyList) {
		bodies = bodyList;
	}
	
	Timeline timeline = new Timeline();
	
	public Timeline getTimeline() {
		return timeline;
	}
	
	public void sendCanvas(Group c) {
		canvas = c;
	}
	
	public void handle(ActionEvent event) {
		timeline.stop();
		timeline.getKeyFrames().clear();
		

		for (Body i : bodies) {
			i.addToTimeline();
		}
		
		timeline.play();
	}
}
