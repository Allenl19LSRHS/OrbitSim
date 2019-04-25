package orbitsim.engine;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import orbitsim.display.OrbitSim;
import orbitsim.display.TimelineManager;

public class Universe {
	ArrayList<Body> bodies = new ArrayList<Body>();
	TimelineManager timelineManager;
	OrbitSim main;
	static final double CONSTANT_G = 100;
	int cyclesPerAnim;
	
	//TODO: Make a way for the cycle() method to be run again after the first run, not through TimelineManager
	//		(since the timeline isn't created every cycle)
	//TODO: remove reference to Orbitsim in constuctor? (Not necessary?)
	
	public Universe(OrbitSim sim) {
		main = sim;
		bodies.add(new Body(1, 700, 200, 4, 3));
		bodies.add(new Body(200, 600, 400, 0, 0));
		
		cyclesPerAnim = OrbitSim.animScale/OrbitSim.universeTick;
		System.out.println(cyclesPerAnim + " Cycles per animation");
	}

	public ArrayList<Body> getBodies() {
		return bodies;
	}
	
	public void setTLMgr(TimelineManager tl) {
		timelineManager = tl;
	}
	
	public void cycle() {
		for (int b = 1; b <= cyclesPerAnim; b++) {
			for (Body i : bodies) {
				
				// Draw line for previous step
				//TODO: Maybe animate it too so it draws along with the body moving animation
				
				ArrayList<Vector> vecs = new ArrayList<Vector>();
				for (Body a:bodies) {
					if (a != i) {
						// Method for calculating based on total distance then splitting it to a vector
						
						//IMPORTANT: coordinate transforms? Atan2 might not give me correct angles...
						double dx = i.getX() - a.getX();
						double dy = i.getY() - a.getY();
						double theta = Math.atan2(dy, dx);
						double distsq = Math.pow(2*dx, 2) + Math.pow(2*dy, 2);
						double f = (Universe.CONSTANT_G * i.getMass() * a.getMass()) / distsq;
						double fx = f * Math.cos(theta);
						double fy = f * Math.sin(theta);
						//System.out.println("Force: " + fx + " " + fy);
	
						// Calculation to calculate force vector is here
						vecs.add(new Vector(fx, fy));
					}
				}
				
				// Will be 0,0 so that it doesn't effect the rest
				Vector finalVec = new Vector(0,0);
				
				// Basically just sum vectors
				for (Vector c : vecs) {
					finalVec = Vector.addVec(finalVec, c);
				}
				
				System.out.println("Final Force: <" + finalVec.getX() + ", " + finalVec.getY() + ">");
				
				// Set the velocity by adding components of previous vel with vel from force vector
				i.setVelX(i.getVelX() + (finalVec.getX()/i.getMass()));
				i.setVelY(i.getVelY() + (finalVec.getY()/i.getMass()));
				System.out.println("Velocity: " + i.getVelX() + ", " + i.getVelY());
				
				// Set the x position based on the new velocity
				// NOTE: multiplied by .5 to slow it down, maybe make this adjustable?
				i.setX(i.getX() + (int)(i.getVelX()));
				i.setY(i.getY() + (int)(i.getVelY()));
				System.out.println("Pos: " + i.getX() + ", " + i.getY());
				
				// Add keyframes based on old and new positions
			}
		}
		System.out.println("Animation created");
		for (Body i : bodies) {
			timelineManager.getTimeline().getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO, new KeyValue(i.getCircle().centerXProperty(), i.getOldX()), new KeyValue(i.getCircle().centerYProperty(), i.getOldY())),
					new KeyFrame(Duration.millis(OrbitSim.animScale), timelineManager, new KeyValue(i.getCircle().centerXProperty(), i.getX()), new KeyValue(i.getCircle().centerYProperty(), i.getY()))
				);
			Line l = new Line(i.getOldX(), i.getOldY(), i.getX(), i.getY());
			l.setStroke(Color.WHITESMOKE);
			main.getCanvas().getChildren().add(l);
			i.resetOldPos();
		}
		timelineManager.getTimeline().play();
	}
}
