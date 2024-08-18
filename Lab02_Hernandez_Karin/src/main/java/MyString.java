/**
 * File: Main.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 09/08/2020
 * Class description: Lab 02
 */
public class MyString {

//Creates the MyString constructor
    private String a;
    
    public MyString(String a)
    {
        this.a = a;
    }
    
//First multiConcat method
    public String multiConcat(String x, int y)
    {
    //Returns x by itself if y is less than 2
        if(y < 2)
        {
            return x;
        }
        
        String t = "";
        for(int i = 0; i < y; i++)
        {
            t = t.concat(x);
        }
        return t;
    }
    
//Second multiConcat method, returns xx if there is no int parameter
    public String multiConcat(String x)
    {
        return x.concat(x);
    }
    
//Third multiConcat method
    public String multiConcat(String s1, String s2, String s3, int n1, int n2, 
            int n3, int times)
    {
        String q = "";
        for(int i = 0; i < n1; i++)
        {
            q = q.concat(s1);
        }
        String w = "";
        for(int i = 0; i < n2; i++)
        {
            w = w.concat(s2);
        }
        String e = "";
        for(int i = 0; i < n3; i++)
        {
            e = e.concat(s3);
        }
    //Concatenates Strings s1, s2, s3
        String r = q + w + e;
        
        String t = "";
        for(int i = 0; i < times; i++)
        {
            t = t.concat(r);
        }
        return t;
    }
    
//Returns the String
    public String getString()
    {
        return a;
    }
}
