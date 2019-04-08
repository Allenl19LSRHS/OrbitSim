package orbitsim.display;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import orbitsim.engine.Body;


//Questions to answer:
// Slider for timescale? Faster = less accurate, slower = more accurate
// Add buttons and text fields to change stats around bodies
// Eventually, display velocity vectors, maybe force vectors, and history path for objects


// Movement will be with a new KeyFrame for every single movement, with duration set based on timescale slider
// Each "tick" (every time the current animation is done) each body calculates where it should be next "tick"
// all of those positions are sent to the timeline via KeyFrames, and then the timeline is run until animation is completed
public class OrbitSim extends Application {
	ArrayList<Body> bodies = new ArrayList<Body>();
	TimelineManager timelineManager = new TimelineManager(this);
	public static int timeScale = 60;
	Group canvas = new Group();

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {
		// make the application fill the screen
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
		
		Group root = new Group();
		// Add background black box
		root.getChildren().add(new Rectangle(bounds.getWidth(), bounds.getHeight(), Color.BLACK));
		
		root.getChildren().add(canvas);
		
		//BodyControlManager bodyControlManager = new BodyControlManager(root);
		
		//bodyControlManager.recreateGrid(2);
		
		bodies.add(new Body(10, 20, 20, timelineManager));
		bodies.add(new Body(20, 100, 100, timelineManager));
		
		for (int i = 0; i < bodies.size(); i++) {
			canvas.getChildren().add(bodies.get(i).getCircle());
		}
		
		stage.setScene(new Scene(root, 1200, 800));
		
		timelineManager.handle(new ActionEvent());
		
		timelineManager.getTimeline().play();
		
		stage.show();
	}
	
	void cycle() {
		for (Body i : bodies) {
			Line l = new Line(i.getOldX(), i.getOldY(), i.getX(), i.getY());
			l.setStroke(Color.WHITE);
			canvas.getChildren().add(l);
			
			i.setOldX(i.getX());
			i.setOldY(i.getY());
			i.setX(i.getX() + 25-i.cycle);
			i.setY(i.getY() + 5);
			if (i.cycle <= 25 && i.cycled == false) {
				i.cycle++; 
			} else {
				i.cycled = true;
				i.cycle--;
				if (i.cycle < 1) {
					i.cycled = false;
				}
			}
			timelineManager.getTimeline().getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO, new KeyValue(i.getCircle().centerXProperty(), i.getX()), new KeyValue(i.getCircle().centerYProperty(), i.getY())),
					new KeyFrame(Duration.millis(OrbitSim.timeScale), timelineManager, new KeyValue(i.getCircle().centerXProperty(), i.getX() + 25 - i.cycle), new KeyValue(i.getCircle().centerYProperty(), i.getY() + 5))
				);
		}
		
		timelineManager.getTimeline().play();
	}
}
