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
	static final int CONSTANT_G = 1000000;
	
	public Universe(OrbitSim sim) {
		main = sim;
		bodies.add(new Body(0.01, 700, 600, 5, 0));
		bodies.add(new Body(20, 600, 600, 0, 0));
	}

	public ArrayList<Body> getBodies() {
		return bodies;
	}
	
	public void setTLMgr(TimelineManager tl) {
		timelineManager = tl;
	}
	
	public void cycle() {
		for (Body i : bodies) {
			// Draw line for previous step
			//TODO: Maybe animate it too so it draws along with the body moving animation?
			Line l = new Line(i.getOldX(), i.getOldY(), i.getX(), i.getY());
			l.setStroke(Color.WHITESMOKE);
			main.getCanvas().getChildren().add(l);
			
			ArrayList<Vector> vecs = new ArrayList<Vector>();
			for (Body a:bodies) {
				if (a != i) {
					
					// G*M*M/r^2
					double Fx = -(OrbitSim.G * i.getMass() * a.getMass()) / Math.pow((i.getX()-a.getX()),2);
					double Fy = -(OrbitSim.G * i.getMass() * a.getMass()) / Math.pow((i.getY()-a.getY()),2);
					if (i.getX() - a.getX() < 0) {
						Fx *= -1;
					}
					if (i.getY() - a.getY() < 0) {
						Fy *= -1;
					}
					if (i.getX() - a.getX() == 0) {
						Fx = 0;
					}
					if (i.getY() - a.getY() == 0) {
						Fy = 0;
					}
					System.out.println(Fx + " " + Fy);
					// Calculation to calculate force vector is here
					vecs.add(new Vector(Fx, Fy));
				}
			}
			
			// Will be 0,0 so that it doesn't effect the rest
			Vector finalVec = new Vector(0,0);
			
			// Basically just sum vectors
			for (Vector b : vecs) {
				finalVec = Vector.addVec(finalVec, b);
			}
			
			// Set the velocity by adding components of previous vel with vel from force vector
			i.setVelX(i.getVelX() + finalVec.getX());
			i.setVelY(i.getVelY() + finalVec.getY());
			
			// Set the x position based on the new velocity
			// NOTE: multiplied by .5 to slow it down, maybe make this adjustable?
			i.setX(i.getX() + (int)(0.5*i.getVelX() * (1000/OrbitSim.timeScale)));
			i.setY(i.getY() + (int)(0.5*i.getVelY() * (1000/OrbitSim.timeScale)));
			
			// Add keyframes based on old and new positions
			timelineManager.getTimeline().getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO, new KeyValue(i.getCircle().centerXProperty(), i.getOldX()), new KeyValue(i.getCircle().centerYProperty(), i.getOldY())),
					new KeyFrame(Duration.millis(OrbitSim.timeScale), timelineManager, new KeyValue(i.getCircle().centerXProperty(), i.getX()), new KeyValue(i.getCircle().centerYProperty(), i.getY()))
				);
		}
		
		timelineManager.getTimeline().play();
	}
}
