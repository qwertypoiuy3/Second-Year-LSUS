import java.util.ArrayList;
import java.util.Random;
/**
 * File: Shuffle.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 08/26/2020
 * Class description: Lab 01
 */

public class Shuffle {
    
    public static void main(String[] args) {
    //Creates ArrayList and Random objects
        ArrayList<Integer> list = new ArrayList<Integer>();
        Random numbers = new Random();
    //Second array list for problem 9
        ArrayList second = new ArrayList();
        
    //Generates 10 random numbers between 0 and 99 and adds them to the list
        for(int i = 0; i < 10; i++)
        {
            int a = numbers.nextInt(99);
            list.add(a);
            System.out.println(list.get(i));
        }
        
        System.out.println("");
        
    //Uses the list.size method for adding and printing the list values
        for(int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i));
        }
        
        System.out.println("");
        
    //Loops over the list 10 times removing a random integer each time, while
    //adding the removed integer to the second array list for problem 9
        System.out.println(list);
        for(int i = 0; i < 9; i++)
        {
            int a = list.get(numbers.nextInt(list.size()-1));
            int pos = list.indexOf(a);
            list.remove(pos);
            second.add(a);
            
            System.out.println(list + "\t\tRemoved int: " + a);
        }
        
    //Prints the second array list with the randomized values
        System.out.println("\n" + second);
    }
/*8. I get the same result on the first two operations, once it gets to the
last one is when it starts to vary
*/
}
