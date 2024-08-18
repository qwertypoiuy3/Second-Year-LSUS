package lab07;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Karin Galicia (Software Development concentration? [Yes | No]
 */
public class Lab07Master {
  // ===================================================================================================================
  // ===================================================================================================================
  // ===== VARIABLES
  // ===================================================================================================================
  // ===================================================================================================================
  // --- Put your name and concentration Yes or No here
  String myName = "Karin Galicia (Software Development: [Yes])";
  
  // --- Creates a random number generator
  Random r = new Random();

  
  // ===================================================================================================================
  // ===================================================================================================================
  // ===== CONSTRUCTOR
  // ===================================================================================================================
  // ===================================================================================================================
  /**
   * Constructor
   * @param randomNumberGeneratorSeed A new random number generator seed value
   */
  public Lab07Master(long randomNumberGeneratorSeed) {
    // --- Seeds the random number generator with a value of choice
    r.setSeed(randomNumberGeneratorSeed);
  }


  // ===================================================================================================================
  // ===================================================================================================================
  // ===== PUBLIC METHODS
  // ===================================================================================================================
  // ===================================================================================================================
  public String getName() {
    return myName;
  }
  
  /**
   * Creates and returns a one-dimensional array of randomly-generated integers [minVal..maxVal]
   * @param minValue Min integer
   * @param maxValue Max integer
   * @param numValues Array size
   * @return Array of randomly-generated integers
   */
  public int[] get1DArray(int minValue, int maxValue, int numValues) {
    // --- Declares a one-dimensional array of integers
    int[] array = null;

    array = new int[numValues];

    int randomNumber = 0;

    for(int i = 0; i < array.length; i++) {
      // --- Creates a random number between minValue and MaxValue
      randomNumber = r.nextInt((maxValue - minValue) + 1) + minValue;

      // --- Stores the number in the array
      array[i] = randomNumber;
    }

    return array;
  }

  /**
   * Creates and returns a two-dimensional array of randomly-generated integers [minVal..maxVal]
   * @param minValue Min integer
   * @param maxValue Max integer
   * @param numRows Array size (rows)
   * @param numCols Array size (columns)
   * @return Array of randomly-generated integers
   */
  public int[][] get2DArray(int minValue,
                            int maxValue,
                            int numRows,
                            int numCols) {
    // --- Declares a two-dimensional array of integers
    int[][] array = null;

    array = new int[numRows][numCols];
    
    int randomNumber = 0;

    for(int row = 0; row < array.length; row++)
        for(int col = 0; col < array[row].length; col++)
            array[row][col] = r.nextInt((maxValue - minValue) + 1) + minValue;

    // --- Returns a two-dimensional array of integers
    return array;
  }

  /**
   * Creates and returns an ArrayList of randomly-generated integers [minVal..maxVal]
   * @param minValue
   * @param maxValue
   * @param numValues
   * @return
   */
  public ArrayList getArrayListOfValues(int minValue,
                                        int maxValue,
                                        int numValues) {
    // --- Creates an ArrayList
    ArrayList<Integer> al = new ArrayList<Integer>(numValues);

    int randomNumber = 0;

    for(int i = 0; i < numValues; i++)
    {
        randomNumber = r.nextInt((maxValue - minValue) + 1) + minValue;
        al.add(randomNumber);
    }

    return al;
  }

  /**
   * Calculates the sum of all values in a one-dimensional array
   * @param array One-dimensional array of integers
   * @return Sum of all values
   */
  public int sumOfValues(int[] array) {
    int sum = 0;

    for(int i = 0; i < array.length; i++)
    {
        int num = array[i];
        sum += num;
    }

    return sum;
  }

  /**
   * Calculates the sum of all values in a two-dimensional array, utilizing the sumOfValues(int[] array) method.
   * @param array Two-dimensional array of integers
   * @return Sum of all values
   */
  public int sumOfValues(int[][] array) {
    int sum = 0;

    for(int row = 0; row < array.length; row++)
        for(int col = 0; col < array[row].length; col++)
            sum += array[row][col];

    return sum;
  }

  /**
   * Calculates the sum of all values in an ArrayList
   * @param array ArrayList of Integers
   * @return Sum of all values
   */
  public int sumOfValues(ArrayList arrayList) {
    int sum = 0;

    ArrayList<Integer> a = new ArrayList<Integer>(arrayList);
    
    for(int i = 0; i < a.size(); i++)
    {
        int num = a.get(i);
        sum += num;
    }

    return sum;
  }

  /**
   * Checks whether array1 and array2 are equal - whether the elements are the same
   * @param array1 Array of integers 1
   * @param array2 Array of integers 2
   * @return
   */
  public boolean equal(int[] array1, int[] array2) {
    boolean result = false;

    String first = "";
    String second = "";
    
    for(int i = 0; i < array1.length; i++)
    {
        first += array1[i] + "";
    }
    
    for(int i = 0; i < array2.length; i++)
    {
        second += array2[i] + "";
    }
    
    if(first.equals(second))
    {
        result = true;
    }

    return result;
  }

  /**
   * Reverses the array of integers and returns a new reversed array
   * @param array Array of integers
   * @return New, reversed, array
   */
  public int[] reverse(int[] array) {
    int[] tempArray = new int[array.length];
    
    int num = 0;
    int a = 0;
    
    for(int i = array.length - 1; i >= 0; i--)
    {
        num = array[i];
        tempArray[a] = num;
        a++;
    }
    
    return tempArray;
  }

  /**
   * Converts a one-dimensional array of integers to a String
   * @param array Array of integers
   * @return A string representation of an array
   */
  public String getArrayString(int[] array) {
    String s = "";

    for(int i = 0; i < array.length; i++) {
      s += "[" + array[i] + "]";
    }

    s += "\n";

    return s;
  }

  /**
   * Converts a two-dimensional array of integers to a String
   * @param array a two-dimensional array of integers
   * @return A string representation of a two-dimensional array
   */
  public String getArrayString(int[][] array) {
    String s = "";

    for(int i = 0; i < array.length; i++) {
      // --- Makes use of the method above
      s += getArrayString(array[i]);
    }

    return s;
  }

  /**
   * Converts an ArrayList of Integers to a String
   * @param array ArrayList of Integers
   * @return A string representation of an ArrayList
   */
  public String getArrayListString(ArrayList al) {
    String s = "";
    
    ArrayList<Integer> n = new ArrayList<Integer>(al);
    
    for(int i = 0; i < al.size(); i++)
    {
        int a = n.get(i);
        s += "[" + a + "]";
    }

    return s;
  }


  /**
   * Converts an array of Objects to a String
   * @param array Array of Objects
   * @return String representation of an array
   */
  private String getArrayString(Object[] array) {
    String s = "";

    for(int i = 0; i < array.length; i++) {
      s += "[" + ((Integer)array[i]).intValue() + "]";
    }
      
    return s;
  }
}
