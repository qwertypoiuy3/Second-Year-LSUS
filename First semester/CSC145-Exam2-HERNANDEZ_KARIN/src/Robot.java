import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
/*
 * File: Main.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 11/01/2020
 * Class description: EXAM 2
 */
public class Robot extends Application{
    public void start(Stage primaryStage)
    {
        Color eye = Color.rgb(255, 69, 0);
        Circle leye = new Circle(340, 300, 25, eye);
        Circle reye = new Circle(460, 300, 25, eye);
        
        Ellipse face = new Ellipse(400, 350, 200, 140);
        face.setFill(Color.WHITE);
        face.setStroke(Color.BLACK);
        face.setStrokeWidth(12);
        
        Arc smile = new Arc(400, 400, 60, 30, 180, 180);
        smile.setFill(Color.WHITE);
        smile.setStroke(Color.BLACK);
        smile.setStrokeWidth(12);
        
        Line antenna = new Line(400, 202, 445, 100);
        antenna.setStroke(Color.BLACK);
        antenna.setStrokeWidth(12);
        
        Line antenna2 = new Line(445, 100, 520, 120);
        antenna2.setStroke(Color.BLACK);
        antenna2.setStrokeWidth(12);
        
        Circle antenna3 = new Circle(550, 130, 25, Color.WHITE);
        antenna3.setStroke(Color.BLACK);
        antenna3.setStrokeWidth(12);
        
        Arc lear = new Arc(241, 271, 50, 50, 53, 165);
        lear.setFill(Color.WHITE);
        lear.setStroke(Color.BLACK);
        lear.setStrokeWidth(12);
        
        Arc rear = new Arc(560, 272, 50, 50, -39, 165);
        rear.setFill(Color.WHITE);
        rear.setStroke(Color.BLACK);
        rear.setStrokeWidth(12);
        
        Group root = new Group(face, leye, reye, smile, antenna, antenna2, 
                antenna3, lear, rear);
        
        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.PLUM);
        
        primaryStage.setTitle("Reddit Robot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
