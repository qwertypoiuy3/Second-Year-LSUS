/**
 * File: Main.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 09/09/2020
 * Class description: Lab 03
 */
public class Main 
{    
    public static void main(String[] args)
    {
    //Creares a MyRectangle and MyEllipse objects
        MyRectangle mr = new MyRectangle(5.123, 7.3541);
        MyEllipse me = new MyEllipse(12.1, 4.761111);
        
    //Prints data for the rectangle object
        System.out.println(mr + "\nArea: " + mr.getArea() + " square units" + 
                "\nPerimeter: " + mr.getPerimeter() + " units");
        
        System.out.println("");
        
    //Prints data for the ellipse object
        System.out.println(me + "\nArea: " + me.getArea() + " square units" + 
                "\nPerimter: " + me.getPerimeter() + " units");
    }
}
