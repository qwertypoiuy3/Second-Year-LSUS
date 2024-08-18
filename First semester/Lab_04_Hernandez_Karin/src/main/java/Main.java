/*
 * File: Main.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 09/16/2020
 * Class description: Lab 04
 */

public class Main {
    public static void main(String[] args)
    {
        int[] arr = MyArrayUtility.getArrayInt(2, 8, 10);
        
        String returnStr = MyArrayUtility.toString(arr, "[", "]");
        System.out.println("The array of random values:");
        System.out.println(returnStr);
        
        int sum = MyArrayUtility.getArraySum(arr);
        System.out.println("The sum of random array values: " + sum);
        
        
        arr = MyArrayUtility.getArrayInt(8, 2, 10);
        returnStr = MyArrayUtility.toString(arr, "(", ") ");
        System.out.println("\nThe array of random values:");
        System.out.println(returnStr);
        
        arr = MyArrayUtility.getArrayInt(-8, -2, 5);
        returnStr = MyArrayUtility.toString(arr, "<", "> ");
        System.out.println("\nThe array of random values:");
        System.out.println(returnStr);
        
        arr = MyArrayUtility.getArrayInt(8, 2, 10);
        returnStr = MyArrayUtility.toString(arr, "[", "] ");
        System.out.println("\nThe array of random values:");
        System.out.println(returnStr);
        
        
        arr = MyArrayUtility.getArrayInt(-2, 7, 10);
        returnStr = MyArrayUtility.toString(arr, "(", ") ");
        System.out.println("\nThe array of random values:");
        System.out.println(returnStr);
        
        arr = MyArrayUtility.getArrayInt(-2, -8, 15);
        returnStr = MyArrayUtility.toString(arr, "(", ") ");
        System.out.println("\nThe array of random values:");
        System.out.println(returnStr);
    }
}
