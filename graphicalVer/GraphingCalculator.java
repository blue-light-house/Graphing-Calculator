import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.lang.Math;
import java.util.*;
 
public class GraphingCalculator extends Application {
	private Calculator calc = new Calculator();
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CS3141 Team Software Project: Graphing Calculator");
		TextField evaluateText = new TextField();
        Button evaluateButton = new Button();
		Label evaluateLabel = new Label("NO CURRENT EVALUATION");
        evaluateButton.setText("Evaluate");
        evaluateButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				String boxText = evaluateText.getText();
				if (!boxText.equals("")) {
					String[] toEvaluate = calc.recognizer(boxText);
					try {
						evaluateLabel.setText("Result: " + calc.evaluate(toEvaluate, 0, toEvaluate.length));
					} catch (IllegalArgumentException e) {
						evaluateLabel.setText("Invalid argument! Source: " + e.getMessage());
					}
				} else {
					evaluateLabel.setText("You must provide an expression!");
				}
            }
        });
        
        TilePane root = new TilePane();
		root.getChildren().add(evaluateText);
        root.getChildren().add(evaluateButton);
		root.getChildren().add(evaluateLabel);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}