package orbitsim.display;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import orbitsim.engine.Body;

public class OrbitSim extends Application {
	ArrayList<Body> bodies = new ArrayList<Body>();

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {
		Group canvas = new Group();
		
		// New bodies are added to the ArrayList
		bodies.add(new Body(10, 20, 20));
		bodies.add(new Body(20, 100, 100));
		
		// Display engine displays them at the beginning. Will probably want it to add the body's circle to the canvas any time one is created.
		for (int i = 0; i < bodies.size(); i++) {
			canvas.getChildren().add(bodies.get(i).getCircle());
		}
		//mainPane.getChildren().add(new Circle(250, 250, 50, Color.BLACK));
		
		canvas.setEffect(new BoxBlur(2, 2, 1));
		stage.setScene(new Scene(canvas, 900, 800));
		stage.show();
		
		// How to update the positions of the circles
		bodies.get(0).getCircle().setCenterX(250);
	}

}
