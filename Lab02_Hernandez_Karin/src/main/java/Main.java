/**
 * File: Main.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 09/08/2020
 * Class description: Lab 02
 */
public class Main {
    
    public static void main(String[] args) {
        
    //Creates three MyString objects and gets the value on a String variable
        MyString a = new MyString("how");
        String a1 = a.getString();
        
        MyString b = new MyString("are");
        String b1 = b.getString();
        
        MyString c = new MyString("you");
        String c1 = c.getString();
        
    //Problems 1, 2, and 3 respectively
        String first = a.multiConcat(a1, 4);
        System.out.println(first + "\n");
        
        String second = b.multiConcat(b1);
        System.out.println(second + "\n");
        
        String third = a.multiConcat(a1, b1, c1, 3, 2, 4, 2);
        System.out.println(third);
    }
}
