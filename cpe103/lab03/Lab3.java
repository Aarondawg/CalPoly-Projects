import java.util.*;
import mycs1.*;

public class Lab3
{
   private static int passed = 0;
   private static int failed = 0;
   private static int totalPoints = 0;

   public static void main(String[] args)
   {
      try  // Used to catch unexpected exceptions and report partial grade
      {
      System.out.println();
      System.out.println("BEGIN TESTING: Number in parentheses represents the point value");
      System.out.println("of the test case.");
      System.out.println();

      int[] x = new int[] {1,5,6,11,14,15,17,20};
      int[] y = new int[] {2,3,4,7,8,9,10,12,13,16,18,19};
      int[] z = union(x,y);
      IntArray.sort(x); // Just in case
      IntArray.sort(y); // Just in case
      IntArray.sort(z);

      reportTest("1",5,IntArray.equals(z,merge(x,y)));
      reportTest("2",3,IntArray.equals(z,merge(y,x)));

      x = new int[] {1,2,3,4,5,6,7};
      y = new int[] {8,9,10,11,12,13,14,15,16};
      z = union(x,y);
      IntArray.sort(x); // Just in case
      IntArray.sort(y); // Just in case
      IntArray.sort(z);

      reportTest("3",5,IntArray.equals(z,merge(x,y)));
      reportTest("4",3,IntArray.equals(z,merge(y,x)));

      x = new int[] {1};
      y = new int[] {2};
      z = union(x,y);
      IntArray.sort(x); // Just in case
      IntArray.sort(y); // Just in case
      IntArray.sort(z);
      reportTest("5",1,IntArray.equals(z,merge(x,y)));
      reportTest("6",1,IntArray.equals(z,merge(y,x)));

      x = new int[] {1};
      y = new int[0];
      z = new int[] {1};
      reportTest("7",1,IntArray.equals(z,merge(x,y)));
      reportTest("8",1,IntArray.equals(z,merge(y,x)));

      x = new int[0];
      y = new int[0];
      z = new int[0];
      reportTest("9",1,IntArray.equals(z,merge(x,y)));
      reportTest("10",1,IntArray.equals(z,merge(y,x)));

      Random g = new Random(1234567);
      x = IntArray.makeRandomArray(1000,g);
      y = IntArray.makeRandomArray(500,g);
      z = union(x,y);
      IntArray.sort(x);
      IntArray.sort(y);
      IntArray.sort(z);
      reportTest("11",10,IntArray.equals(z,merge(x,y)));
      reportTest("12",5,IntArray.equals(z,merge(y,x)));

      }  // End of wrapping try block for all of main

      catch (Exception e)
      {
         System.out.println();
         System.out.println(
            "ERROR: Testing is being aborted due to the following unexpected exception.");
       
         // NOTE: The following information is written to System.err and will not be
         // seen if Standard.out is piped to more.

         e.printStackTrace();
         System.out.println();
         System.out.println("INCOMPLETE GRADE");
         System.out.println("   Points Passed.......... " + passed);
         System.exit(1);
      }

      printSummary();
   }

   // Precondition: Arrays x and y are already sorted.
   
   private static int[] merge(int[] x,int[] y)
   {
     int index1 = 0;
     int index2 = 0;
     int index3 = 0;
     int[] z = new int[x.length + y.length];


     while(index1 < x.length && index2 < y.length)
      {
         if(x[index1] < y[index2])
         {
            z[index3] = x[index1];
            index1++;
            index3++;

         }
         else 
         {
            z[index3] = y[index2];
            index2++;
            index3++;

         }

      }

      while(index1 < x.length)
      {
         z[index3] = x[index1];
         index1++;
         index3++;
      }

      while(index2 < y.length)
      {
         z[index3] = y[index2];
         index2++;
         index3++;
      }
      return z;
   }

   private static void reportTest(String testID,int weight,boolean passedTest)
   {
      totalPoints += weight;
      if (passedTest)
      {
         passed += weight;
         System.out.println("   Passed test case " + testID + " (" + weight + ")");
      }
      else
      {
         failed += weight;
         System.out.println("   FAILED test case " + testID + " (" + weight + ")");
      }
   }

   private static void printSummary()
   {
      double pct = 100.0*passed/totalPoints;
      int points = (int)Math.round(pct/100.0*25);

      System.out.println();
      System.out.println("TESTING SUMMARY");
      System.out.println("   Total Points...... " + totalPoints);
      System.out.println("   Passed............ " + passed);
      System.out.println("   Failed............ " + failed);
      System.out.println("   Grade............. " + Math.round(pct) + "% = " + 
                                                    points + "/25");
   }

   private static int[] union(int[] x,int[] y)
   {
      int[] z = new int[x.length+y.length];
      for(int i=0;i<x.length;++i)
         z[i] = x[i];
      for(int i=0;i<y.length;++i)
         z[i+x.length] = y[i];
      return z;
   }
}
