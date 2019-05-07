package orbitsim.display;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import orbitsim.engine.Body;
import orbitsim.engine.Universe;


//Questions to answer:
// Slider for timescale? Faster = less accurate, slower = more accurate
// Add buttons and text fields to change stats around bodies
// Eventually, display velocity vectors, maybe force vectors

public class OrbitSim extends Application {
	// Anim scale is number of ms between animations
	// universeTick is number of ms per physics calculation
	// Must be divisible by 5 currently
	public static double animScale = 30;
	public static final double universeTick = animScale/60;
	private Group canvas = new Group();
	private Universe universe = new Universe(this);
	private BodyControlManager bodyControlManager;
	
	// Launcher for javaFX application so it can run from IDE
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
		
        // Root is base group for whole screen
		Group root = new Group();
		
		// Add background black box
		root.getChildren().add(new Rectangle(bounds.getWidth(), bounds.getHeight(), Color.BLACK));
		
		// canvas is display area for actual simulation
		root.getChildren().add(canvas);
		
		// BCM is panel for setting body stats and starting/stopping simulation
		bodyControlManager = new BodyControlManager(root);
		
		bodyControlManager.recreateGrid(3);
		updateGUI(universe.getBodies());
		
		stage.setScene(new Scene(root, 1200, 800));
		
		universe.cycle();
		
		stage.show();
	}
	
	public Group getCanvas() {
		return canvas;
	}
	
	public void removeCircle(Circle a) {
		canvas.getChildren().remove(a);
	}
	
	public void addCircle(Circle a) {
		canvas.getChildren().add(a);
	}
	
	public void updateGUI(ArrayList<Body> a) {
		for (int i = 0; i < a.size(); i++) {
			Body b = a.get(i);
			bodyControlManager.setBodyMass(i, b.getMass());
			bodyControlManager.setBodyX(i, b.getX());
			bodyControlManager.setBodyY(i, b.getY());
			bodyControlManager.setBodyVelX(i, b.getVelX());
			bodyControlManager.setBodyVelY(i, b.getVelY());
		}
	}
}
