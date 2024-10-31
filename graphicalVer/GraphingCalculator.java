import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.shape.*;
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
	Scene evaluationXScene = null;
	
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
						evaluateLabel.setText("Result: " + calc.evaluate(toEvaluate));
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
		
		Button evalToEvalXButton = new Button();
		evalToEvalXButton.setText("EVALAUTION AT X MODE");
        
        VBox evaluationRoot = new VBox(8);
		evaluationRoot.setAlignment(Pos.CENTER);
		evaluationRoot.getChildren().add(evaluateText);
        evaluationRoot.getChildren().add(evaluateButton);
		evaluationRoot.getChildren().add(evaluateLabel);
		evaluationRoot.getChildren().add(evalToGraphButton);
		evaluationRoot.getChildren().add(evalToEvalXButton);
		//evaluationRoot.setHalignment(child, HPos.CENTER);
		evaluationScene = new Scene(evaluationRoot, 600, 500);
		
		// EVALUATE OVER X SCENE
		
		TextField evaluateXText = new TextField();
		TextField evaluateXVal = new TextField();
        Button evaluateXButton = new Button();
		Label evaluateXLabel = new Label("NO CURRENT EVALUATION");
		Label evaluateXInstructions = new Label("Give your expression with a capital X as a variable.");
        evaluateXButton.setText("Evaluate");
        evaluateXButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
				String boxText = evaluateXText.getText();
				String xText = evaluateXVal.getText();
				if (!boxText.equals("")) {
					if (!xText.equals("")) {
						String[] toEvaluate = calc.recognizer(boxText);
						try {
							try {
								evaluateXLabel.setText("Result: " + calc.evaluateAtX(toEvaluate, xText));
							} catch (IllegalArgumentException e) {
								evaluateXLabel.setText("Invalid argument! Source: " + e.getMessage());
							}
						} catch (NumberFormatException e) {
							evaluateXLabel.setText("You must provide a valid X value!");
						}
					} else {
						evaluateXLabel.setText("You must provide an X value!");
					}
				} else {
					evaluateXLabel.setText("You must provide an expression!");
				}
            }
        });
		
		Button evalXToGraphButton = new Button();
		evalXToGraphButton.setText("GRAPHING MODE");
		
		Button evalXToEvalButton = new Button();
		evalXToEvalButton.setText("EVALUATION MODE");
        
        VBox evaluationXRoot = new VBox(8);
		evaluationXRoot.setAlignment(Pos.CENTER);
		evaluationXRoot.getChildren().add(evaluateXInstructions);
		evaluationXRoot.getChildren().add(evaluateXText);
		evaluationXRoot.getChildren().add(evaluateXVal);
        evaluationXRoot.getChildren().add(evaluateXButton);
		evaluationXRoot.getChildren().add(evaluateXLabel);
		evaluationXRoot.getChildren().add(evalXToGraphButton);
		evaluationXRoot.getChildren().add(evalXToEvalButton);
		//evaluationRoot.setHalignment(child, HPos.CENTER);
		evaluationXScene = new Scene(evaluationXRoot, 600, 500);
		
		// GRAPHING SCENE
		
		GridPane graphingRoot = new GridPane();
		graphingRoot.setAlignment(Pos.CENTER);
		Pane graphingCanvas = new Pane();
		Button updateGraphButton = new Button();
		updateGraphButton.setText("UPDATE GRAPH");
        updateGraphButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				updateGraphingCanvas(graphingCanvas, primaryStage);
            }
        });
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
		
		Button graphToEvalXButton = new Button();
		graphToEvalXButton.setText("EVALUATION AT X MODE");
        graphToEvalXButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				evaluateXLabel.setText("NO CURRENT EVALUATION");
				evaluateXText.setText("");
				evaluateXVal.setText("");
				primaryStage.setScene(evaluationXScene);
				
            }
        });
		
		graphingRoot.getChildren().add(graphingCanvas);
		graphingRoot.setRowIndex(graphingCanvas, 0);
		graphingRoot.setColumnIndex(graphingCanvas, 0);
		graphingRoot.setAlignment(Pos.CENTER);
		
		graphingRoot.getChildren().add(updateGraphButton);
		graphingRoot.setRowIndex(updateGraphButton, 1);
		graphingRoot.setColumnIndex(updateGraphButton, 0);
		
		graphingRoot.getChildren().add(graphToEvalButton);
		graphingRoot.setRowIndex(graphToEvalButton, 2);
		graphingRoot.setColumnIndex(graphToEvalButton, 0);
		
		graphingRoot.getChildren().add(graphToEvalXButton);
		graphingRoot.setRowIndex(graphToEvalXButton, 2);
		graphingRoot.setColumnIndex(graphToEvalXButton, 1);
		
		graphingScene = new Scene(graphingRoot, 600, 500);
		
		evalToGraphButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				graphingCanvas.getChildren().clear();
				addXYPlane(graphingCanvas, primaryStage);
				primaryStage.setScene(graphingScene);
            }
        });
		
		evalXToGraphButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				graphingCanvas.getChildren().clear();
				addXYPlane(graphingCanvas, primaryStage);
				primaryStage.setScene(graphingScene);
            }
        });
		
		evalToEvalXButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				evaluateXLabel.setText("NO CURRENT EVALUATION");
				evaluateXText.setText("");
				evaluateXVal.setText("");
				primaryStage.setScene(evaluationXScene);
				
            }
        });
		
		evalXToEvalButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				evaluateLabel.setText("NO CURRENT EVALUATION");
				evaluateText.setText("");
				primaryStage.setScene(evaluationScene);
				
            }
        });
		
		// WINDOW DRESSING
		
        primaryStage.setScene(graphingScene);
        primaryStage.show();
		
		addXYPlane(graphingCanvas, primaryStage);
	}
	
	private void updateGraphingCanvas(Pane canvas, Stage stage) {
		canvas.getChildren().clear();
		addXYPlane(canvas, stage);
		Line line = new Line(0, 0, 100, 100);
		canvas.getChildren().add(line);
	}
	
	private void addXYPlane(Pane canvas, Stage stage) {
		canvas.getChildren().clear();
		double windowX = stage.getX();
		double windowY = stage.getY();
		Line xAxis = new Line(0, windowY / 2, windowX, windowY / 2);
		Line yAxis = new Line(windowX / 2, 0, windowX / 2, windowY);
		/*
		xAxis.startXProperty().bind(widthProperty().divide(2));
		xAxis.startYProperty().bind(0);
		xAxis.endXProperty().bind(widthProperty().divide(2));
		xAxis.endYProperty().bind(heightProperty().divide(2));
		
		yAxis.startXProperty().bind(0);
		yAxis.startYProperty().bind(heightProperty().divide(2));
		yAxis.endXProperty().bind(widthProperty().divide(2));
		yAxis.endYProperty().bind(heightProperty().divide(2));
		*/
		
		canvas.getChildren().add(xAxis);
		canvas.getChildren().add(yAxis);
	}
}