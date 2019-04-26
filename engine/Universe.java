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
	static final double CONSTANT_G = 500;
	int cyclesPerAnim;
	
	public Universe(OrbitSim sim) {
		main = sim;
		bodies.add(new Body(0.01, 700, 400, 100, 100));
		bodies.add(new Body(100, 600, 400, 0, 0));
		
		// Calculate how many integrations occur between each animation creation
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
		// cycle through calculations until an animation
		for (int b = 1; b <= cyclesPerAnim; b++) {
			for (Body i : bodies) {
				
				ArrayList<Vector> vecs = new ArrayList<Vector>();
				for (Body a:bodies) {
					if (a != i) {
						// Method for calculating based on total distance then splitting it to a vector
						double dx = i.getX() - a.getX();
						double dy = i.getY() - a.getY();
						double theta = Math.atan2(dy, dx);
						double distsq = Math.pow(2*dx, 2) + Math.pow(2*dy, 2);
						
						// g = -Gmm/r^2
						double f = -(Universe.CONSTANT_G * i.getMass() * a.getMass()) / distsq;
						double fx = f * Math.cos(theta);
						double fy = f * Math.sin(theta);
							
						// Calculation to calculate force vector is here
						vecs.add(new Vector(fx, fy));
					}
				}
				
				Vector finalVec = new Vector(0,0);
				
				// Basically just sum vectors
				for (Vector c : vecs) {
					finalVec = Vector.addVec(finalVec, c);
				}
							
				// queue velocity changes by adding components of previous vel with vel from force vector over mass (a = f/m)
				i.queueVel(i.getVelX() + (finalVec.getX()/i.getMass()), i.getVelY() + (finalVec.getY()/i.getMass()));
				
				// queue the position changes based on the new velocity (v*t)
				i.queuePos(i.getX() + (i.getVelX()*(OrbitSim.universeTick/1000.0)), i.getY() + (i.getVelY()*(OrbitSim.universeTick/1000.0)));
				
			}
			
			// tell all bodies to push the queued changes to their actual data
			for (Body i: bodies) {
				i.updateQueuedData();
			}
		}
		
		// Once required number of cycles have run, animation needs to be created
		for (Body i : bodies) {
			// Add keyframes based on old and new positions (old pos is updated when new pos is created)

			timelineManager.getTimeline().getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO, new KeyValue(i.getCircle().centerXProperty(), i.getOldX()), new KeyValue(i.getCircle().centerYProperty(), i.getOldY())),
					new KeyFrame(Duration.millis(OrbitSim.animScale), timelineManager, new KeyValue(i.getCircle().centerXProperty(), i.getX()), new KeyValue(i.getCircle().centerYProperty(), i.getY()))
				);
			
			// Draw line for previous step
			//TODO: Maybe animate it too so it draws along with the body moving animation
			// (not really necessary with such small calculation distances it's not noticable)
			
			Line l = new Line(i.getOldX(), i.getOldY(), i.getX(), i.getY());
			l.setStroke(Color.WHITESMOKE);
			main.getCanvas().getChildren().add(l);
			i.resetOldPos();
		}
		// Play the timeline
		timelineManager.getTimeline().play();
	}
}
