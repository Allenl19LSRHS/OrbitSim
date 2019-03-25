package orbitsim.display;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import orbitsim.engine.Body;


//Questions to answer:
// How to do the movement? Update position and just redraw (which means manually creating a framerate?)
// 		or figure out how to use JavaFX animation?
// Add buttons and text fields to change stats around bodies
// Eventually, display velocity vectors, maybe force vectors, and history path for objects

public class OrbitSim extends Application {
	ArrayList<Body> bodies = new ArrayList<Body>();

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
		
		Group root = new Group();
		root.getChildren().add(new Rectangle(bounds.getWidth(), bounds.getHeight(), Color.BLACK));
		
		Group canvas = new Group();
		root.getChildren().add(canvas);
		
		// New bodies are added to the ArrayList
		bodies.add(new Body(10, 20, 20));
		bodies.add(new Body(20, 100, 100));
		
		// Display engine displays them at the beginning. Will probably want it to add the body's circle to the canvas any time one is created.
		for (int i = 0; i < bodies.size(); i++) {
			canvas.getChildren().add(bodies.get(i).getCircle());
		}
		//mainPane.getChildren().add(new Circle(250, 250, 50, Color.BLACK));
		
		canvas.setEffect(new BoxBlur(1, 1, 1));
		stage.setScene(new Scene(root, 1200, 800));
		stage.show();
		
		// How to update the positions of the circles
		bodies.get(0).getCircle().setCenterX(250);
		bodies.get(0).getCircle().setCenterY(700);
	}

}
