package lab07;

import java.util.ArrayList;

/**
 *
 * @author Karin Galicia  (Software Development concentration? [Yes]
 */
public class Lab07Driver {
  /**
   * @param args the command line arguments
   */

  public static void main(String[] args) {
    Lab07Master am1 = new Lab07Master(0);

    // ============================================================================================
    // ===== Displays your name and concentration
    // ============================================================================================

    System.out.println("(0pts but required in order to be graded):" + am1.getName());


    // ============================================================================================
    // ===== One-dimensional array part
    // ============================================================================================

    int[] array1Da = am1.get1DArray(3, 5, 10);
    int[] array1Db = am1.get1DArray(3, 5, 10);

    System.out.println("(10pts) Step 1:");
    System.out.println("Array a: " + am1.getArrayString(array1Da));
    System.out.println("Array b: " + am1.getArrayString(array1Db));

    System.out.println("(10pts) Step 2: Arrays a and b are the same: " +
                       am1.equal(array1Da, array1Db));
    
    System.out.println("(10pts) Step 3: Arrays a and a are the same: " +
                       am1.equal(array1Da, array1Da));

    System.out.println("(10pts) Step 4a: Sum of array a: " + am1.sumOfValues(array1Da));
    System.out.println("        Step 4b: Sum of array b: " + am1.sumOfValues(array1Db));

    System.out.println("(10pts) Step 5: Reversed arrays");
    System.out.println(am1.getArrayString(array1Da));
    System.out.println(am1.getArrayString(am1.reverse(array1Da)));


    // ============================================================================================
    // ===== Two-dimensional array part
    // ============================================================================================

    int[][] array2Da = am1.get2DArray(2, 7, 3, 5);

    System.out.println("(10pts) Step 6:\n" + am1.getArrayString(array2Da));
    System.out.println("(10pts) Step 7: Sum of a 2D array = " + am1.sumOfValues(array2Da));


    // ============================================================================================
    // ===== ArrayList part
    // ============================================================================================

    ArrayList al = am1.getArrayListOfValues(1, 9, 15);
    System.out.println("(10pts) Step 8:\n" + am1.getArrayListString(al));
    System.out.println("(10pts) Step 9: Sum of the ArrayList above = " + am1.sumOfValues(al));


    // ============================================================================================
    // ============================================================================================
    // ===== Verify your results before submitting your work.
    // ============================================================================================
    // ============================================================================================
  }
}
