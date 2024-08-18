import java.util.Scanner;
/*
 * File: Main.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 09/29/2020
 * Class description: Lab 05
 */
public class RandomNumbers {
    public static void main(String[] args)
    {
    //Scanner class
        Scanner scan = new Scanner(System.in);
        
    //Creates all variables to hold int values
        int n,r,a,b,c,d;
        
    //Asks for values a and b to create a random value, and assign it to n
        System.out.println("Enter a value for a: ");
        a = scan.nextInt();
        System.out.println("Enter a value for b: ");
        b = scan.nextInt();
        
        if(a > b)
        {
            n = (int)(Math.random() * (a - b + 1) + b);
        }
        else
        {
            n = (int)(Math.random() * (b - a + 1) + a);
        }
        
    //Asks for values c and d to create a random value r
        System.out.println("\nEnter a value for c: ");
        c = scan.nextInt();
        System.out.println("Enter a value for d: ");
        d = scan.nextInt();
        
        if(c > d)
        {
        //If n is negative it counts down from n to 0
            if(n < 0)
            {
                for(int i = n; i <= 0; i++)
                {
                    r = (int)(Math.random() * (c - d + 1) + d);
                    System.out.println("[" + i + "]" + r);
                }
            }
        //Creates a list of all random numbers r
            for(int i = 0; i < n; i++)
            {
                r = (int)(Math.random() * (c - d + 1) + d);
                System.out.println("[" + i + "]" + r);
            }
        }
        else
        {
            if(n < 0)
            {
                for(int i = n; i <= 0; i++)
                {
                    r = (int)(Math.random() * (d - c + 1) + c);
                    System.out.println("[" + i + "]" + r);
                }
            }
            for(int i = 0; i < n; i++)
            {
                r = (int)(Math.random() * (d - c + 1) + c);
                System.out.println("[" + i + "]" + r);
            }
        }
    }
}
