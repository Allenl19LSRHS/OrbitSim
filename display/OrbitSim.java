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
// Work on overlap of bodies and lines etc (bodies need to be on top)
// Slider for timescale? Faster = less accurate, slower = more accurate
// Eventually, display velocity vectors, maybe force vectors

public class OrbitSim extends Application {
	// Anim scale is number of ms between animations
	// universeTick is number of ms per physics calculation
	// Must be divisible by 5 currently
	public static double animScale = 30;
	public static final double universeTick = animScale/60;
	public static boolean run = true;
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
		bodyControlManager = new BodyControlManager(root, this);
		

		stage.setScene(new Scene(root, bounds.getWidth(), bounds.getHeight()));
		
		bodyControlManager.recreateGrid(3);
		updateGUI(universe.getBodies());
		
		
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
	
	// Updates the body controls to populate with initial values of the bodies
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
	
	// Methods need to talk to Universe and control things
	public void resume() {
		run = true;
		universe.cycle();
		// Resume just continues/starts the simulation
	}
	
	public void pause() {
		run = false;
		// Pause just keeps the next calculation and timeline from being created
	}
	
	public void reset() {
		// Read the values from the text boxes, destroy all current bodies, and create new ones
		run = false;
		universe.clearAll();
		canvas.getChildren().clear();
		BodyControlManager bcm = bodyControlManager;
		for (int i = 0; i < bcm.getBodyCount(); i++) {
			universe.createBody(bcm.getBodyMass(i), bcm.getBodyX(i), bcm.getBodyY(i), bcm.getBodyVelX(i), bcm.getBodyVelY(i));
		}
	}
	
	// Adds another body to the simulation
	public void increaseBodies() {
		//maximum of 6 bodies
		if(!(bodyControlManager.getBodyCount() > 5)) {
			// first reset the sim so when the boxes are redrawn and repopulated
			// the values are the initial values not huge decimals
			reset();
			// Create the new body
			universe.createBody(0.1, 300, 300, 0, 0);
			// Remake the grid with another body
			bodyControlManager.recreateGrid(bodyControlManager.getBodyCount()+1);
			// Repopulate the grid
			updateGUI(universe.getBodies());
		}
	}
	
	//Remove a body from the sim
	public void decreaseBodies() {
		// Minimum of 2 bodies
		if (bodyControlManager.getBodyCount() > 2) {
			// first reset the sim so when the boxes are redrawn and repopulated
			// the values are the initial values not huge decimals
			reset();
			// Remove the last body in the list
			universe.removeBody(universe.getBodies().get(universe.getBodies().size()-1));
			// Recreate the grid with fewer bodies
			bodyControlManager.recreateGrid(bodyControlManager.getBodyCount()-1);
			// Repopulate the grid
			updateGUI(universe.getBodies());
		}
	}
}
