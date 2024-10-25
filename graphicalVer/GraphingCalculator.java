import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import java.lang.Math;
import java.util.*;
 
public class GraphingCalculator extends Application {
	private Calculator calc = new Calculator();
	Scene evaluationScene = null;
	Scene graphingScene = null;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CS3141 Team Software Project: Graphing Calculator");
		
		// EVALUATION SCENE
		
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
		Button evalToGraphButton = new Button();
		evalToGraphButton.setText("GRAPHING MODE");
        evalToGraphButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				primaryStage.setScene(graphingScene);
            }
        });
        
        VBox evaluationRoot = new VBox(8);
		evaluationRoot.setAlignment(Pos.CENTER);
		evaluationRoot.getChildren().add(evaluateText);
        evaluationRoot.getChildren().add(evaluateButton);
		evaluationRoot.getChildren().add(evaluateLabel);
		evaluationRoot.getChildren().add(evalToGraphButton);
		//evaluationRoot.setHalignment(child, HPos.CENTER);
		evaluationScene = new Scene(evaluationRoot, 600, 500);
		
		// GRAPHING SCENE
		
		VBox graphingRoot = new VBox(8);
		graphingRoot.setAlignment(Pos.CENTER);
		Button graphToEvalButton = new Button();
		graphToEvalButton.setText("EVALUATION MODE");
        graphToEvalButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				evaluateLabel.setText("NO CURRENT EVALUATION");
				evaluateText.setText("");
				primaryStage.setScene(evaluationScene);
				
            }
        });
		graphingRoot.getChildren().add(graphToEvalButton);
		graphingScene = new Scene(graphingRoot, 600, 500);
		
		// WINDOW DRESSING
		
        primaryStage.setScene(evaluationScene);
        primaryStage.show();
    }
}