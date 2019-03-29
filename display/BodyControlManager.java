package orbitsim.display;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class BodyControlManager {
	private Group root;
	int bodies = 2;
	
	
	public BodyControlManager(Group g) {
		root = g;
	}
	
	public void recreateGrid(int a) {
		bodies = a;
		GridPane pane = new GridPane();
		pane.add(new Label("Mass:"), 0, 0);
		pane.add(new Label("X Pos:"), 1, 0);
		pane.add(new Label("Y Pos:"), 2, 0);
		pane.add(new Label("X Vel:"), 3, 0);
		pane.add(new Label("Y Vel:"), 4, 0);

		
		for (int i = 0; i < bodies; i++) {
			pane.add(new Label("Body 1:"), 0, i+1);
			pane.add(new TextField(), 1, i+1);
			pane.add(new TextField(), 2, i+1);
			pane.add(new TextField(), 3, i+1);
			pane.add(new TextField(), 4, i+1);
			pane.add(new TextField(), 5, i+1);
		}
		
		root.getChildren().add(pane);
	}
}
