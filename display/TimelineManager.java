package orbitsim.display;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import orbitsim.engine.Universe;

public class TimelineManager implements EventHandler<ActionEvent> {
	Universe main;
	Timeline timeline = new Timeline();
	
	public TimelineManager(Universe uvs) {
		main = uvs;
		main.setTLMgr(this);
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
