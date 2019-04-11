package orbitsim.display;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import orbitsim.engine.Universe;


//Questions to answer:
// Slider for timescale? Faster = less accurate, slower = more accurate
// Add buttons and text fields to change stats around bodies
// Eventually, display velocity vectors, maybe force vectors, and history path for objects


// Movement will be with a new KeyFrame for every single movement, with duration set based on timescale slider
// Each "tick" (every time the current animation is done) each body calculates where it should be next "tick"
// all of those positions are sent to the timeline via KeyFrames, and then the timeline is run until animation is completed
public class OrbitSim extends Application {
	public static int timeScale = 100;
	Group canvas = new Group();
	Universe universe = new Universe(this);
	TimelineManager timelineManager = new TimelineManager(universe);
	

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
		
		for (int i = 0; i < universe.getBodies().size(); i++) {
			canvas.getChildren().add(universe.getBodies().get(i).getCircle());
		}
		
		stage.setScene(new Scene(root, 1200, 800));
		
		timelineManager.handle(new ActionEvent());
		
		timelineManager.getTimeline().play();
		
		stage.show();
	}
	
	public Group getCanvas() {
		return canvas;
	}
}
