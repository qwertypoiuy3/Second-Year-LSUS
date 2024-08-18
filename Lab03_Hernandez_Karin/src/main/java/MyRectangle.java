/**
 * File: MyRectangle.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 09/09/2020
 * Class description: Lab 03
 */
public class MyRectangle implements Calculable
{
    private double width;
    private double height;
    
//Constructor
    public MyRectangle(double width, double height)
    {
        this.width = width;
        this.height = height;
    }
    
//Method that returns the area
    public double getArea()
    {
        double area = width * height;
        return area;
    }
    
//Method that returns the perimeter
    public double getPerimeter()
    {
        double peri = (width * 2) + (height * 2);
        return peri;
    }
    
//Returns the width and height of the rectangle
    public String toString()
    {
        return "Rectangle" + "\nWidth: " + width + " units" + "\nHeight: " + 
                height + " units";
    }
}
