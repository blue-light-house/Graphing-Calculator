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

/**
GraphingCalculator: provides a graphical frontend to evaluate and graph functions
@author blue-light-house
@version 0.8
Date Last Modfiied: 2024-11-08
*/
public class GraphingCalculator extends Application {
	// Creates a calculator object for use in evaluation
	private Calculator calc = new Calculator();
	Scene evaluationScene = null;
	Scene graphingScene = null;
	Scene evaluationXScene = null;
	
	/**
	* main: launches the program
	* @param args: an array of strings as options
	*/
    public static void main(String[] args) {
        launch(args);
    }
    
	/**
	* start: sets the stage and handles the main loop of the graphing calculator
	* @param primaryStage: the stage used throughout
	*/
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
		
		VBox graphingRoot = new VBox(8);
		graphingRoot.setAlignment(Pos.CENTER);
		Pane graphingCanvas = new Pane();
		Button updateGraphButton = new Button();
		TextField graphingExpression = new TextField();
		TextField graphingBounds = new TextField();
		
		Slider graphingSlider = new Slider(50, 1000, 500);
		graphingSlider.setShowTickMarks(true);
		graphingSlider.setShowTickLabels(true);
		graphingSlider.setMajorTickUnit(25);
		graphingSlider.setBlockIncrement(10);
		
		updateGraphButton.setText("UPDATE GRAPH");
        updateGraphButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				int coarseness = (int) graphingSlider.getValue();
				// String[] bounds = calc.recognizer(graphingBounds.getText());
				//updateGraphingCanvas(graphingCanvas, primaryStage, calc.recognizer(graphingExpression.getText()), Double.parseDouble(bounds[0]), Double.parseDouble(bounds[1]), Double.parseDouble(bounds[2]), Double.parseDouble(bounds[3]));
				updateGraphingCanvas(graphingCanvas, primaryStage, calc.recognizer(graphingExpression.getText()), -5, 5, -5, 5, coarseness);
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
		// graphingRoot.setRowIndex(graphingCanvas, 0);
		// graphingRoot.setColumnIndex(graphingCanvas, 0);
		graphingRoot.setAlignment(Pos.CENTER);
		
		graphingRoot.getChildren().add(graphingExpression);
		// graphingRoot.setRowIndex(updateGraphButton, 1);
		// graphingRoot.setColumnIndex(updateGraphButton, 0);
		
		graphingRoot.getChildren().add(graphingBounds);
		// graphingRoot.setRowIndex(updateGraphButton, 2);
		//graphingRoot.setColumnIndex(updateGraphButton, 0);
		
		graphingRoot.getChildren().add(graphingSlider);
		
		graphingRoot.getChildren().add(updateGraphButton);
		// graphingRoot.setRowIndex(updateGraphButton, 3);
		// graphingRoot.setColumnIndex(updateGraphButton, 0);
		
		graphingRoot.getChildren().add(graphToEvalButton);
		// graphingRoot.setRowIndex(graphToEvalButton, 4);
		// graphingRoot.setColumnIndex(graphToEvalButton, 0);
		
		graphingRoot.getChildren().add(graphToEvalXButton);
		// graphingRoot.setRowIndex(graphToEvalXButton, 4);
		// graphingRoot.setColumnIndex(graphToEvalXButton, 1);
		
		graphingScene = new Scene(graphingRoot, 600, 500);
		
		evalToGraphButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				graphingCanvas.getChildren().clear();
				addXYPlane(graphingCanvas, primaryStage);
				graphingSlider.setValue(500);
				graphingExpression.setText("");
				graphingBounds.setText("");
				primaryStage.setScene(graphingScene);
            }
        });
		
		evalXToGraphButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
				graphingCanvas.getChildren().clear();
				addXYPlane(graphingCanvas, primaryStage);
				graphingSlider.setValue(500);
				graphingExpression.setText("");
				graphingBounds.setText("");
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
	
	/**
	* updateGraphingCanvas: given a pane, graphs the given expression
	* @param canvas: the canvas to display the graph in
	* @param stage: the stage the canvas sits in
	* @param expression: an array of strings representing a mathematical expression
	* @param startX: a double value representing the left bound of the graph
	* @param endX: a double value representing the right bound of the graph
	* @param highY: a double value representing the upper bound of the graph
	* @param lowY: a double value representing the lower bound of the graph
	* @param coarseness: an integer value representing the number of steps in the graph
	*/
	private void updateGraphingCanvas(Pane canvas, Stage stage, String[] expression, double startX, double endX, double highY, double lowY, int coarseness) {
		canvas.getChildren().clear();
		double windowX = stage.getX();
		double windowY = stage.getY();
		
			// System.out.println("WINDOWX " + windowX + " WINDOWY " + windowY);
		addXYPlane(canvas, stage);
		// Steps coarseness times and draws a small dot at the corresponding evaluation
		for (double i = startX; i <= endX; i+=((endX-startX)/coarseness)) {
			double yTemp = calc.evaluateAtX(expression, Double.toString(i));
				// System.out.println("POINT AT (" + i + ", " + yTemp + ")");
			Circle circle = new Circle();
			canvas.getChildren().add(circle);
			
			double circleX = ((i - startX)/(endX - startX)) * windowX;
				//double circleY = (windowY / 2) + (yTemp >= 0 ? (yTemp / highY) : (yTemp / Math.abs(lowY)))*(windowY/2);
			double circleY = (windowY / 2) + (yTemp / highY)*(windowY/2);
			double circleRadius = (windowY) / 100;
			
				// System.out.println("CIRCLE X " + circleX + " CIRCLE Y " + circleY + " CIRCLE RADIUS " + circleRadius);
			circle.setCenterX(circleX);
			circle.setCenterY(circleY);
			circle.setRadius(circleRadius);
		}
	}
	
	/**
	* addXYPlane: adds a centered XY plane to the given canvas
	* @param canvas: the pane to add the plane in
	* @param stage: the stage the canvas sits in
	*/
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