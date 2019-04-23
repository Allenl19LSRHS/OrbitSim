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
	int cyclesSinceAnim;
	int cyclesPerAnim;
	
	//TODO: Make a way for the cycle() method to be run again after the first run, not through TimelineManager
	//		(since the timeline isn't created every cycle)
	//TODO: remove reference to Orbitsim in constuctor? (Not necessary?)
	
	public Universe(OrbitSim sim) {
		main = sim;
		bodies.add(new Body(1, 700, 600, 0, -0.75));
		bodies.add(new Body(100, 600, 600, 0, 0));
		
		cyclesPerAnim = OrbitSim.animScale/OrbitSim.universeTick;
		cyclesSinceAnim = cyclesPerAnim;
	}

	public ArrayList<Body> getBodies() {
		return bodies;
	}
	
	public void setTLMgr(TimelineManager tl) {
		timelineManager = tl;
	}
	
	public void cycle() {
		cyclesSinceAnim += 1;
		
		for (Body i : bodies) {
			
			// Draw line for previous step
			//TODO: Maybe animate it too so it draws along with the body moving animation?
			Line l = new Line(i.getOldX(), i.getOldY(), i.getX(), i.getY());
			l.setStroke(Color.WHITESMOKE);
			main.getCanvas().getChildren().add(l);
			
			ArrayList<Vector> vecs = new ArrayList<Vector>();
			for (Body a:bodies) {
				if (a != i) {
					
					/*
					 * Calculated by splitting into x and y, might not work?
					 * when values are less than one apart, force in that dir is calc to be huge
					// G*M*M/r^2
					double Fx = (OrbitSim.G * i.getMass() * a.getMass()) / Math.pow((i.getX()-a.getX()),2);
					double Fy = (OrbitSim.G * i.getMass() * a.getMass()) / Math.pow((i.getY()-a.getY()),2);
					if (i.getX() - a.getX() > 0) {
						Fx *= -1;
					}
					if (i.getY() - a.getY() > 0) {
						Fy *= -1;
					}
					if (i.getX() - a.getX() == 0) {
						Fx = 0;
					}
					if (i.getY() - a.getY() == 0) {
						Fy = 0;
					}
					System.out.println(Fx + " " + Fy);
					*/
					
					// Method for calculating based on total distance then splitting it to a vector
					double dx = i.getX() - a.getX();
					double dy = i.getY() - a.getY();
					double theta = Math.atan(dy/dx);
					double distsq = Math.pow(dx, 2) + Math.pow(dy, 2);
					double f = (Universe.CONSTANT_G * i.getMass() * a.getMass()) / distsq;
					double fx = f * Math.cos(theta);
					double fy = f * Math.sin(theta);
					
					if (i.getX() - a.getX() > 0) {
						fx *= -1;
					}
					if (i.getY() - a.getY() > 0) {
						fy *= -1;
					}
					if (i.getX() - a.getX() == 0) {
						fx = 0;
					}
					if (i.getY() - a.getY() == 0) {
						fy = 0;
					}
					//System.out.println(fx + " " + fy);

					// Calculation to calculate force vector is here
					vecs.add(new Vector(fx, fy));
				}
			}
			
			// Will be 0,0 so that it doesn't effect the rest
			Vector finalVec = new Vector(0,0);
			
			// Basically just sum vectors
			for (Vector b : vecs) {
				finalVec = Vector.addVec(finalVec, b);
			}
			
			// Set the velocity by adding components of previous vel with vel from force vector
			i.setVelX(i.getVelX() + (finalVec.getX()/i.getMass()));
			i.setVelY(i.getVelY() + (finalVec.getY()/i.getMass()));
			
			// Set the x position based on the new velocity
			// NOTE: multiplied by .5 to slow it down, maybe make this adjustable?
			i.setX(i.getX() + (int)(0.5*i.getVelX() * (1000/OrbitSim.universeTick)));
			i.setY(i.getY() + (int)(0.5*i.getVelY() * (1000/OrbitSim.universeTick)));
			
			// Add keyframes based on old and new positions
		}
		
		if (cyclesSinceAnim == cyclesPerAnim) {
			System.out.println("Animation created");
			for (Body i : bodies) {
				timelineManager.getTimeline().getKeyFrames().addAll(
						new KeyFrame(Duration.ZERO, new KeyValue(i.getCircle().centerXProperty(), i.getOldX()), new KeyValue(i.getCircle().centerYProperty(), i.getOldY())),
						new KeyFrame(Duration.millis(OrbitSim.animScale), timelineManager, new KeyValue(i.getCircle().centerXProperty(), i.getX()), new KeyValue(i.getCircle().centerYProperty(), i.getY()))
					);
				i.resetOldPos();
			}
				
			cyclesSinceAnim = 0;
			timelineManager.getTimeline().play();
		}
	}
}
