package engine;

import java.util.ArrayList;

import display.OrbitSim;
import display.TimelineManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Universe {
	private ArrayList<Body> bodies = new ArrayList<Body>();
	private TimelineManager timelineManager = new TimelineManager(this);
	private OrbitSim main;
	static final double CONSTANT_G = 5000;
	private double cyclesPerAnim;
	private double[] stageSize;
	
	public Universe(OrbitSim sim) {
		main = sim;
		stageSize = main.getStageSize();
		createBody(0.01, 100, 50, 20, -70);
		createBody(200, 0, 0, 0, 0);
		createBody(1, -100, 200, 50, 5);
		
		// Calculate how many integrations occur between each animation creation
		cyclesPerAnim = OrbitSim.animScale/OrbitSim.universeTick;
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
						double distsq = Math.pow(dx, 2) + Math.pow(dy, 2);
						
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
				
				vecs.clear();

				//Basic euler integration for the new velocity and position of the body after one physics tick
				Vector eulerVel = new Vector(i.getVelX() + (finalVec.getX()/i.getMass())*(OrbitSim.universeTick/1000.0), i.getVelY() + (finalVec.getY()/i.getMass())*(OrbitSim.universeTick/1000.0));
				Vector eulerPos = new Vector(i.getX() + (eulerVel.getX()*(OrbitSim.universeTick/1000.0)), i.getY() + (eulerVel.getY()*(OrbitSim.universeTick/1000.0)));
				
				finalVec = new Vector(0,0);
				
				// Now calculate velocity again at the new position
				for (Body a:bodies) {
					if (a != i) {
						// Method for calculating based on total distance then splitting it to a vector
						double dx = eulerPos.getX() - a.getX();
						double dy = eulerPos.getY() - a.getY();
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
				for (Vector c : vecs) {
					finalVec = Vector.addVec(finalVec, c);
				}
				
				Vector secondVel = new Vector(eulerVel.getX() + (finalVec.getX()/i.getMass())*(OrbitSim.universeTick/1000.0), eulerVel.getY() + (finalVec.getY()/i.getMass())*(OrbitSim.universeTick/1000.0));
				
				// Take the average of the two velocities to get the velocity the body will actually have
				Vector avgVel = new Vector((eulerVel.getX()+secondVel.getX())/2, (eulerVel.getY()+secondVel.getY())/2);
				
				i.queueVel(avgVel.getX(), avgVel.getY());
				// queue the position changes based on the new velocity (v*t)
				i.queuePos(i.getX() + (avgVel.getX()*(OrbitSim.universeTick/1000.0)), i.getY() + (avgVel.getY()*(OrbitSim.universeTick/1000.0)));
			}
			
			// tell all bodies to push the queued changes to their actual data
			for (Body i: bodies) {
				i.updateQueuedData();
			}
			
			for (int i = 0; i < bodies.size(); i++) {
				for (int a = i; a < bodies.size(); a++) {
					if (bodies.get(a) != bodies.get(i) && checkIntersect(bodies.get(a).getCircle(),bodies.get(i).getCircle())) {
						handleCollision(bodies.get(a), bodies.get(i));
					}
				}
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
			l.setStroke(i.getColor());
			l.setTranslateX(stageSize[0]/2);
			l.setTranslateY(stageSize[1]/2);
			main.getCanvas().getChildren().add(l);
			i.resetOldPos();
		}
		// Play the timeline
		timelineManager.getTimeline().play();
	}
	
	// Checker if two circles are intersecting (checking if something has collided)
	private boolean checkIntersect(Circle a, Circle b) {
		return (Math.sqrt(Math.pow(a.getCenterX()-b.getCenterX(), 2) + Math.pow(a.getCenterY()-b.getCenterY(), 2)) < a.getRadius() + b.getRadius());
	}
	
	// Method to handle collisions, with conservation of momentum, combining bodies, etc
	private void handleCollision(Body a, Body b) {
		double combinedMass = a.getMass() + b.getMass();
		
		// Calculate new velocities with conservation of momentum
		double cVelX = (a.getMass() * a.getVelX() + b.getMass() * b.getVelX())/combinedMass;
		double cVelY = (a.getMass() * a.getVelY() + b.getMass() * b.getVelY())/combinedMass;
		
		// create new body with combined mass, at the center of mass of the two objects, with the new velocities
		createBody(combinedMass, (int)((a.getX()*a.getMass()+b.getX()*b.getMass())/(combinedMass)), (int)((a.getY()*a.getMass() +b.getY()*b.getMass())/combinedMass), cVelX, cVelY);
		
		// remove the old bodies
		removeBody(b);
		removeBody(a);
	}

	//Method to remove bodies from the sim
	public void removeBody(Body a) {
		// first remove from Universe's body list
		bodies.remove(a);
		// then remove the circle from the canvas
		main.removeCircle(a.getCircle());
	}
	
	// Method to return the list of bodies
	public ArrayList<Body> getBodies() {
		return bodies;
	}
	
	// Method to send the body which index of the bodies array it is (for setting color)
	void getBodyNum(Body b) {
		for (Body i : bodies) {
			if (i == b) {
				i.setColor(bodies.indexOf(i));
			}
		}
	}
	
	// Everything required to make a body. Create, add to array, add circle to display, set its color.
	public void createBody(double m, double x, double y, double vx, double vy) {
		Body b = new Body(m, x, y, vx, vy);
		b.addTranslation((int)(stageSize[0]/2), (int)(stageSize[1]/2));
		bodies.add(b);
		main.addCircle(b.getCircle());
		getBodyNum(b);
	}
	
	// Clear the whole screen and the array
	public void clearAll() {
		for (int i = 0; i < bodies.size(); i++) {
			removeBody(bodies.get(i));
		}
		bodies.clear();
	}
}
