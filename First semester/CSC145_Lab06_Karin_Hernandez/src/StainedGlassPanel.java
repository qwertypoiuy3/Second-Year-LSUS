import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
/*
 * File: Main.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 10/08/2020
 * Class description: Lab 06
 */
public class StainedGlassPanel extends Application
{
    public void start(Stage primaryStage)
    {
        Rectangle corner1 = new Rectangle(1, 2, 50, 50);
        corner1.setFill(Color.RED);
        corner1.setStrokeWidth(3);
        corner1.setStroke(Color.BLACK);
        
        Rectangle corner2 = new Rectangle(449, 2, 50, 50);
        corner2.setFill(Color.RED);
        corner2.setStrokeWidth(3);
        corner2.setStroke(Color.BLACK);
        
        Rectangle corner3 = new Rectangle(1, 700, 50, 50);
        corner3.setFill(Color.RED);
        corner3.setStrokeWidth(3);
        corner3.setStroke(Color.BLACK);
        
        Rectangle corner4 = new Rectangle(449, 700, 50, 50);
        corner4.setFill(Color.RED);
        corner4.setStrokeWidth(3);
        corner4.setStroke(Color.BLACK);
        
        Rectangle top = new Rectangle(50, 2, 400, 50);
        top.setFill(Color.BLUE);
        top.setStrokeWidth(3);
        top.setStroke(Color.BLACK);
        
        Rectangle right = new Rectangle(449, 50, 50, 650);
        right.setFill(Color.BLUE);
        right.setStrokeWidth(3);
        right.setStroke(Color.BLACK);
        
        Rectangle left = new Rectangle(1, 51, 50, 650);
        left.setFill(Color.BLUE);
        left.setStrokeWidth(3);
        left.setStroke(Color.BLACK);
        
        Rectangle bottom = new Rectangle(110, 700, 280, 50);
        bottom.setFill(Color.BLUE);
        bottom.setStrokeWidth(3);
        bottom.setStroke(Color.BLACK);
        
        Rectangle bottomL = new Rectangle(50, 700, 60, 50);
        bottomL.setFill(Color.ORANGE);
        bottomL.setStrokeWidth(3);
        bottomL.setStroke(Color.BLACK);
        
        Rectangle bottomLG = new Rectangle(50, 300, 60, 400);
        bottomLG.setFill(Color.GREEN);
        bottomLG.setStrokeWidth(3);
        bottomLG.setStroke(Color.BLACK);
        
        Rectangle bottomR = new Rectangle(390, 700, 60, 50);
        bottomR.setFill(Color.ORANGE);
        bottomR.setStrokeWidth(3);
        bottomR.setStroke(Color.BLACK);
        
        Rectangle bottomRG = new Rectangle(390, 300, 60, 400);
        bottomRG.setFill(Color.GREEN);
        bottomRG.setStrokeWidth(3);
        bottomRG.setStroke(Color.BLACK);
        
        Rectangle rhombus = new Rectangle(75, 80, 350, 350);
        rhombus.setFill(null);
        rhombus.setStrokeWidth(3);
        rhombus.setStroke(Color.BLACK);
        rhombus.setRotate(45);
        
        Polygon topRhomB = new Polygon();
        topRhomB.getPoints().addAll(new Double[]{
            376.0, 134.0,
            328.0, 183.0,
            250.0, 104.0,
            176.0, 181.0,
            125.0, 134.0,
            250.0, 10.0
        });
        topRhomB.setFill(Color.AQUA);
        topRhomB.setStrokeWidth(3);
        topRhomB.setStroke(Color.BLACK);
        
        Rectangle topRhomOR = new Rectangle(341, 157, 89, 69);
        topRhomOR.setRotate(45);
        topRhomOR.setFill(Color.ORANGE);
        topRhomOR.setStrokeWidth(3);
        topRhomOR.setStroke(Color.BLACK);
        
        Rectangle topRhomOL = new Rectangle(75, 155, 89, 69);
        topRhomOL.setRotate(-45);
        topRhomOL.setFill(Color.ORANGE);
        topRhomOL.setStrokeWidth(3);
        topRhomOL.setStroke(Color.BLACK);
        
        Polygon redR = new Polygon();
        redR.getPoints().addAll(new Double[]{
            440.0, 199.0,
            495.0, 253.0,
            449.0, 301.0,
            390.0, 301.0,
            390.0, 245.0
        });
        redR.setFill(Color.RED);
        redR.setStrokeWidth(3);
        redR.setStroke(Color.BLACK);
        
        Polygon leftR = new Polygon();
        leftR.getPoints().addAll(new Double[]{
            62.0, 198.0,
            2.0, 253.0,
            50.0, 301.0,
            110.0, 301.0,
            112.0, 245.0,
        });
        leftR.setFill(Color.RED);
        leftR.setStrokeWidth(3);
        leftR.setStroke(Color.BLACK);
        
        Rectangle bottomRhomO = new Rectangle(176, 361, 150, 65);
        bottomRhomO.setFill(Color.ORANGE);
        bottomRhomO.setStrokeWidth(3);
        bottomRhomO.setStroke(Color.BLACK);
        
        Rectangle bottomRhomG = new Rectangle(176, 300, 150, 60);
        bottomRhomG.setFill(Color.GREEN);
        bottomRhomG.setStrokeWidth(3);
        bottomRhomG.setStroke(Color.BLACK);
        
        Polygon blueL = new Polygon();
        blueL.getPoints().addAll(new Double[]{
            110.0, 301.0,
            176.0, 301.0,
            176.0, 181.0,
            112.0, 245.0
        });
        blueL.setFill(Color.BLUE);
        blueL.setStrokeWidth(3);
        blueL.setStroke(Color.BLACK);
        
        Polygon blueR = new Polygon();
        blueR.getPoints().addAll(new Double[]{
            328.0, 183.0,
            390.0, 245.0,
            390.0, 301.0,
            328.0, 300.0
        });
        blueR.setFill(Color.BLUE);
        blueR.setStrokeWidth(3);
        blueR.setStroke(Color.BLACK);
        
        Polygon leftO = new Polygon();
        leftO.getPoints().addAll(new Double[]{
            50.0, 301.0,
            110.0, 361.0,
            110.0, 301.0,
        });
        leftO.setFill(Color.ORANGE);
        leftO.setStrokeWidth(3);
        leftO.setStroke(Color.BLACK);
        
        Polygon rightO = new Polygon();
        rightO.getPoints().addAll(new Double[]{
            449.0, 301.0,
            390.0, 301.0,
            390.0, 361.0
        });
        rightO.setFill(Color.ORANGE);
        rightO.setStrokeWidth(3);
        rightO.setStroke(Color.BLACK);
        
        Polygon leftTri = new Polygon();
        leftTri.getPoints().addAll(new Double[]{
            110.0, 361.0,
            175.0, 425.0,
            175.0, 361.0
        });
        leftTri.setFill(Color.RED);
        leftTri.setStrokeWidth(3);
        leftTri.setStroke(Color.BLACK);
        
        Polygon rightTri = new Polygon();
        rightTri.getPoints().addAll(new Double[]{
            326.0, 361.0,
            326.0, 425.0,
            390.0, 361.0
        });
        rightTri.setFill(Color.RED);
        rightTri.setStrokeWidth(3);
        rightTri.setStroke(Color.BLACK);
        
        Polygon bottomTri = new Polygon();
        bottomTri.getPoints().addAll(new Double[]{
            175.0, 425.0,
            326.0, 425.0,
            250.0, 500.0
        });
        bottomTri.setFill(Color.BLUE);
        bottomTri.setStrokeWidth(3);
        bottomTri.setStroke(Color.BLACK);
        
        Line middle = new Line(250, 0, 250, 750);
        middle.setStrokeWidth(4);
        
        Group root = new Group(corner1, corner2, corner3, corner4, top, right,
                left, bottom, bottomL, bottomLG, bottomR, bottomRG, rhombus, 
                topRhomB, topRhomOR, topRhomOL, redR, leftR, bottomRhomO, 
                bottomRhomG, blueL, blueR, leftO, rightO, leftTri, rightTri, 
                bottomTri, middle);
        
        Scene scene = new Scene(root, 500, 750);
        
        primaryStage.setTitle("Stained Glass Panel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}
