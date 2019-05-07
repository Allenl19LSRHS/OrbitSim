package orbitsim.display;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class BodyControlManager {
	private Group root;
	private GridPane pane;
	int bodies = 2;
	
	
	public BodyControlManager(Group g) {
		root = g;
	}
	
	public void recreateGrid(int a) {
		bodies = a;
		pane = new GridPane();
		pane.add(new Label("Mass:"), 1, 0);
		pane.add(new Label("X Pos:"), 2, 0);
		pane.add(new Label("Y Pos:"), 3, 0);
		pane.add(new Label("X Vel:"), 4, 0);
		pane.add(new Label("Y Vel:"), 5, 0);

		
		for (int i = 0; i < bodies; i++) {
			pane.add(new Label("Body 1:"), 0, i+1);
			pane.add(new TextField(), 1, i+1);
			pane.add(new TextField(), 2, i+1);
			pane.add(new TextField(), 3, i+1);
			pane.add(new TextField(), 4, i+1);
			pane.add(new TextField(), 5, i+1);
		}
		
		for (Node i : pane.getChildren()) {
			if (i instanceof Label) {
				Label b = (Label) i;
				b.setTextFill(Color.WHITE);
			}
			
		}
		
		root.getChildren().add(pane);
	}
	
	Node getNodeByGrid(int row, int column) {
		for (Node i : pane.getChildren()) {
			if (GridPane.getRowIndex(i) == row && GridPane.getColumnIndex(i) == column) {
				return i;
			}
		}
		
		return null;
	}
	
	public double getBodyMass(int n) {
		TextField t = (TextField) getNodeByGrid(n + 1, 1);
		return Integer.getInteger(t.getText());
	}
	
	public double getBodyX(int n) {
		TextField t = (TextField) getNodeByGrid(n + 1, 2);
		return Integer.getInteger(t.getText());
	}
	
	public double getBodyY(int n) {
		TextField t = (TextField) getNodeByGrid(n + 1, 3);
		return Integer.getInteger(t.getText());
	}
	
	public double getBodyVelX(int n) {
		TextField t = (TextField) getNodeByGrid(n + 1, 4);
		return Integer.getInteger(t.getText());
	}
	
	public double getBodyVelY(int n) {
		TextField t = (TextField) getNodeByGrid(n + 1, 5);
		return Integer.getInteger(t.getText());
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
