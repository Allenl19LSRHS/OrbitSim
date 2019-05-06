package orbitsim.display;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import orbitsim.engine.Universe;

public class TimelineManager implements EventHandler<ActionEvent> {
	private Universe main;
	private Timeline timeline = new Timeline();
	
	public TimelineManager(Universe uvs) {
		main = uvs;
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
