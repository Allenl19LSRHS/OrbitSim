package orbitsim.display;

import java.util.ArrayList;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import orbitsim.engine.Body;

public class TimelineManager implements EventHandler<ActionEvent> {
	
	ArrayList<Body> bodies;
	Group canvas;
	OrbitSim main;
	
	public TimelineManager(ArrayList<Body> bodyList, OrbitSim sim) {
		bodies = bodyList;
		main = sim;
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
		main.cycle();
	}
}
