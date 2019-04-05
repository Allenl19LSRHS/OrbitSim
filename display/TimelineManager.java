package orbitsim.display;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TimelineManager implements EventHandler<ActionEvent> {
	OrbitSim main;
	Timeline timeline = new Timeline();
	
	public TimelineManager(OrbitSim sim) {
		main = sim;
	}
	
	public Timeline getTimeline() {
		return timeline;
	}
	
	public void handle(ActionEvent event) {
		timeline.stop();
		timeline.getKeyFrames().clear();
		main.cycle();
	}
}
