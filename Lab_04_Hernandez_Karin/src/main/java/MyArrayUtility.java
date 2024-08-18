/*
 * File: Main.java
 * Author: KARIN HERNANDEZ
 * Concentration: Software Development
 * Date: 09/16/2020
 * Class description: Lab 04
 */
public class MyArrayUtility {
    
    public static int[] getArrayInt(int minVal, int maxVal, int numValues)
    {
        int[] arr = new int[numValues];
        int min = minVal;
        int max = maxVal;
        
        if(minVal > maxVal)
        {
            maxVal = min;
            minVal = max;
        }
        
        for(int index = 0; index < numValues; index++)
        {
            int random = (int)(Math.random() * (maxVal - minVal + 1) + minVal);
            arr[index] = random;
        }
        return arr;
    }
    
    public static String toString(int[] arr, String strLeft, String strRight)
    {
        String content = "";
        
        for(int index = 0; index < arr.length; index++)
        {
            content += strLeft + arr[index] + strRight;
        }
        return content;
    }
    
    public static int getArraySum(int[] arr)
    {
        int sum = 0;
        
        for(int i = 0; i < arr.length; i++)
        {
            sum = sum + arr[i];
        }
        return sum;
    }
}
