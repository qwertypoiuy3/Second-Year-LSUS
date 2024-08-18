/*
 * Name: Karin Galicia
 * Course: CSC 135-1
 * Project: Problem 3.1
 * File name: Mondrian.java
 */
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Mondrian extends Application
{
    public void start(Stage primaryStage)
    {
        //Lists of Mondrian colors.
        Color redM = Color.rgb(255,1,1);
        Color yellowM = Color.rgb(255, 240, 1);
        Color blueM = Color.rgb(1, 1, 253);
        Color blackM = Color.rgb(48,48,58);
        Color whiteM = Color.rgb(249,249,249);
        
        //Building of all rectangles.
        Rectangle first = new Rectangle(1, 1, 50, 50);
        first.setFill(redM);
        first.setStrokeWidth(2);
        first.setStroke(Color.BLACK);
        
        Rectangle second = new Rectangle(51, 1, 100, 110);
        second.setFill(blueM);
        second.setStrokeWidth(2);
        second.setStroke(Color.BLACK);
        
        Rectangle third = new Rectangle(151, 1, 20, 110);
        third.setFill(whiteM);
        third.setStrokeWidth(2);
        third.setStroke(Color.BLACK);
        
        Rectangle fourth = new Rectangle(171, 1, 228, 40);
        fourth.setFill(yellowM);
        fourth.setStrokeWidth(2);
        fourth.setStroke(Color.BLACK);
        
        Rectangle fifth = new Rectangle(1, 51, 50, 100);
        fifth.setFill(whiteM);
        fifth.setStrokeWidth(2);
        fifth.setStroke(Color.BLACK);
        
        Rectangle sixth = new Rectangle(51, 111, 180, 40);
        sixth.setFill(blackM);
        sixth.setStrokeWidth(2);
        sixth.setStroke(Color.BLACK);
        
        Rectangle seventh = new Rectangle(171, 41, 113, 70);
        seventh.setFill(whiteM);
        seventh.setStrokeWidth(2);
        seventh.setStroke(Color.BLACK);
        
        Rectangle eigth = new Rectangle(284, 41, 115, 70);
        eigth.setFill(blackM);
        eigth.setStrokeWidth(2);
        eigth.setStroke(Color.BLACK);
        
        Rectangle ninth = new Rectangle(231, 111, 168, 40);
        ninth.setFill(whiteM);
        ninth.setStrokeWidth(2);
        ninth.setStroke(Color.BLACK);
        
        //Building of the scene.
        Group root = new Group(first, second, third, fourth, fifth, sixth,
                seventh, eigth, ninth);
        Scene scene = new Scene(root, 400, 152);
        
        //Building of stage.
        primaryStage.setTitle("Mondrian");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
