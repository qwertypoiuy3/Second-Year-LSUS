import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
/*
 * File: Circles.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 11/08/2020
 * Class description: Circles
 */
public class Circles extends Application{
    public void start(Stage myStage)
    {
        TextInputDialog oneX = new TextInputDialog();
        oneX.setContentText("Enter the location x for circle one: ");
        oneX.setHeaderText(null);
        oneX.setTitle(null);
        Optional<String> oneXString = oneX.showAndWait();
        
        TextInputDialog oneY = new TextInputDialog();
        oneY.setContentText("Enter the location y for circle one: ");
        oneY.setHeaderText(null);
        oneY.setTitle(null);
        Optional<String> oneYString = oneY.showAndWait();
        
        TextInputDialog radiusOne = new TextInputDialog();
        radiusOne.setContentText("Enter the radius for circle one: ");
        radiusOne.setHeaderText(null);
        radiusOne.setTitle(null);
        Optional<String> radiusString = radiusOne.showAndWait();
        
        int x1 = Integer.parseInt(oneXString.get());
        int y1 = Integer.parseInt(oneYString.get());
        int radius1 = Integer.parseInt(radiusString.get());
        Circle one = new Circle(x1, y1, radius1);
        
        
        TextInputDialog twoX = new TextInputDialog();
        twoX.setContentText("Enter the location x for cirlce two: ");
        twoX.setHeaderText(null);
        twoX.setTitle(null);
        Optional<String> twoXString = twoX.showAndWait();
        
        TextInputDialog twoY = new TextInputDialog();
        twoY.setContentText("Enter the location y for circle two: ");
        twoY.setHeaderText(null);
        twoY.setTitle(null);
        Optional<String> twoYString = twoY.showAndWait();
        
        TextInputDialog radiusTwo = new TextInputDialog();
        radiusTwo.setContentText("Enter the radius for circle two: ");
        radiusTwo.setHeaderText(null);
        radiusTwo.setTitle(null);
        Optional<String> numStringTwo = radiusTwo.showAndWait();
        
        int x2 = Integer.parseInt(twoXString.get());
        int y2 = Integer.parseInt(twoYString.get());
        int radius2 = Integer.parseInt(numStringTwo.get());
        Circle two = new Circle(x2, y2, radius2);
        
        double numDistance = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
        int radiusLength = radius1 + radius2;
        boolean overlap;
        if(radiusLength > numDistance)
        {
            overlap = true;
        }
        else
        {
            overlap = false;
        }

        Alert distance = new Alert(AlertType.CONFIRMATION);
        distance.setContentText("The distance between the two circles is: " + 
                (int)numDistance + "\nDo circles overlap: " + overlap);
        distance.setHeaderText(null);
        distance.setTitle(null);
        distance.show();
        
        Group root = new Group(one, two);
        
        Scene myScene = new Scene(root, 600, 600);
        
        myStage.setScene(myScene);
        myStage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
