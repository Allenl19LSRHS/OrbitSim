package display;

import engine.Universe;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TimelineManager implements EventHandler<ActionEvent> {
	private Universe universe;
	private Timeline timeline = new Timeline();
	
	public TimelineManager(Universe uvs) {
		universe = uvs;
	}
	
	public Timeline getTimeline() {
		return timeline;
	}
	
	// Reset the timeline when the animations are complete and rerun
	public void handle(ActionEvent event) {
		timeline.stop();
		timeline.getKeyFrames().clear();
		if (OrbitSim.run)
			universe.cycle();
	}
}
