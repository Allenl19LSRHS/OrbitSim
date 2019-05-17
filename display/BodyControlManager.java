package display;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

//TODO:
//	lock body stats while sim is running


public class BodyControlManager {
	private Group root;
	private GridPane pane;
	private OrbitSim main;
	ArrayList<Button> controls = new ArrayList<Button>();
	GridPane controlsPane = new GridPane();
	int bodies;
	
	
	public BodyControlManager(Group g, OrbitSim a) {
		root = g;
		main = a;
	}
	
	public void recreateGrid(int a) {
		root.getChildren().remove(pane);
		root.getChildren().remove(controlsPane);
		bodies = a;
		pane = new GridPane();
		controlsPane = new GridPane();
		
		// create top row labels
		pane.add(new Label("Mass:"), 1, 0);
		pane.add(new Label("X Pos:"), 2, 0);
		pane.add(new Label("Y Pos:"), 3, 0);
		pane.add(new Label("X Vel:"), 4, 0);
		pane.add(new Label("Y Vel:"), 5, 0);

		// for as many bodies as there are, create new rows each
		for (int i = 0; i < bodies; i++) {
			pane.add(new Label("Body " + (i+1) + ":"), 0, i+1);
			pane.add(new TextField(), 1, i+1);
			pane.add(new TextField(), 2, i+1);
			pane.add(new TextField(), 3, i+1);
			pane.add(new TextField(), 4, i+1);
			pane.add(new TextField(), 5, i+1);
		}
		
		// Add/remove body buttons
		Button buttonA = new Button("Add body");
		Button buttonB = new Button("Remove body");
		
		pane.add(buttonA, 6, 1);
		pane.add(buttonB, 6, 2);
		
		buttonA.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				main.increaseBodies();
			}
		});
		
		buttonB.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				main.decreaseBodies();
			}
		});
		
		for (Node i : pane.getChildren()) {
			// set the labels to white text
			if (i instanceof Label) {
				Label b = (Label) i;
				b.setTextFill(Color.WHITE);
			}
			
			// set the text fields to the corresponding body color based on row
			if (i instanceof TextField) {
				if (GridPane.getRowIndex(i) == 1) {
					((TextField)i).setStyle("-fx-control-inner-background: white;");
				} else if (GridPane.getRowIndex(i) == 2) {
					((TextField)i).setStyle("-fx-control-inner-background: blue;");
				} else if (GridPane.getRowIndex(i) == 3) {
					((TextField)i).setStyle("-fx-control-inner-background: yellow;");
				} else if (GridPane.getRowIndex(i) == 4) {
					((TextField)i).setStyle("-fx-control-inner-background: red;");
				} else if (GridPane.getRowIndex(i) == 5) {
					((TextField)i).setStyle("-fx-control-inner-background: orange;");
				} else if (GridPane.getRowIndex(i) == 6) {
					((TextField)i).setStyle("-fx-control-inner-background: green;");
				}
			}
			
			
			
			controlsPane.setLayoutX(root.getScene().getWidth() - 125);
			controlsPane.setLayoutY(0);
			
			
			// idk why it does it but when just creating them in the controls.add command,
			// adding them to the controlsPane gives an InvocationTargetException
			// So instead I have to do it the dumb way
			Button button1 = new Button("Start/Resume");
			Button button2 = new Button("Pause");
			Button button3 = new Button("Reset/Update Values");
			
			controls.add(button1);
			controls.add(button2);
			controls.add(button3);
			
			controlsPane.add(button1, 0, 0);
			controlsPane.add(button2, 0, 1);
			controlsPane.add(button3, 0, 2);
			
			// Set the button width so they are all the same size
			for (Button c: controls) {
				if (c instanceof Button) {
					c.setPrefWidth(125);
					c.setMinWidth(Button.USE_PREF_SIZE);
				}
			}
			
			// Event handlers for the 3 buttons
			button1.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					main.resume();
				}
			});
			button2.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					main.pause();
				}
			});
			button3.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					main.reset();
				}
			});
		}
		
		// add all boxes etc to array
		root.getChildren().add(pane);
		root.getChildren().add(controlsPane);
	}
	
	// get a specific box is based on its row and column
	Node getNodeByGrid(int row, int column) {
		for (Node i : pane.getChildren()) {
			if (GridPane.getRowIndex(i) == row && GridPane.getColumnIndex(i) == column) {
				return i;
			}
		}
		
		return null;
	}
	
	public int getBodyCount() {
		return bodies;
	}
	
	// Bunch of getters and setters for the body stats boxes int is the body number, and on setters second num is value to set
	public double getBodyMass(int n) {
		TextField t = (TextField) getNodeByGrid(n + 1, 1);
		return Double.parseDouble(t.getText());
	}
	
	public double getBodyX(int n) {
		TextField t = (TextField) getNodeByGrid(n + 1, 2);
		return Double.parseDouble(t.getText());
	}
	
	public double getBodyY(int n) {
		TextField t = (TextField) getNodeByGrid(n + 1, 3);
		return Double.parseDouble(t.getText());
	}
	
	public double getBodyVelX(int n) {
		TextField t = (TextField) getNodeByGrid(n + 1, 4);
		return Double.parseDouble(t.getText());
	}
	
	public double getBodyVelY(int n) {
		TextField t = (TextField) getNodeByGrid(n + 1, 5);
		return Double.parseDouble(t.getText());
	}
	
	public void setBodyMass(int n, double m) {
		TextField t = (TextField) getNodeByGrid(n + 1, 1);
		t.setText(Double.toString(m));
	}
	
	public void setBodyX(int n, double x) {
		TextField t = (TextField) getNodeByGrid(n + 1, 2);
		t.setText(Double.toString(x));
	}
	
	public void setBodyY(int n, double y) {
		TextField t = (TextField) getNodeByGrid(n + 1, 3);
		t.setText(Double.toString(y));
	}
	
	public void setBodyVelX(int n, double vx) {
		TextField t = (TextField) getNodeByGrid(n + 1, 4);
		t.setText(Double.toString(vx));
	}
	
	public void setBodyVelY(int n, double vy) {
		TextField t = (TextField) getNodeByGrid(n + 1, 5);
		t.setText(Double.toString(vy));
	}
}
