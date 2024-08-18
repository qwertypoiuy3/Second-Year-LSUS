/**
 * File: MyEllipse.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 09/09/2020
 * Class description: Lab 03
 */
public class MyEllipse implements Calculable
{
    private double sideA;
    private double sideB;
    
//Constructor
    public MyEllipse(double sideA, double sideB)
    {
        this.sideA = sideA;
        this.sideB = sideB;
    }
    
//Method that returns the area
    public double getArea()
    {
        double area = Math.PI * sideA * sideB;
        return area;
    }
    
//Method that returns the perimeter
    public double getPerimeter()
    {
        double peri = 2 * Math.PI * Math.sqrt((sideA * 2 + sideB * 2) / 2);
        return peri;
    }
    
//Returns the dimensions of the ellipse
    public String toString()
    {
        return "Ellipse" + "\nSide A: " + sideA + " units" + "\nSide B: " + 
                sideB + " units";
    }
}
