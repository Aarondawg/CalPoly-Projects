import java.text.DecimalFormat;
import mycs1.*;
import java.util.*;

public class Lab4B
{
   private static int comparisons;
   private static DecimalFormat fmt = new DecimalFormat("#,###,###,###,###,###,##0");
   private static boolean useMedianOfThree;
   private static int usedTwoComparisons;
   private static int simpleFailures = 0;

   public static void main(String[] args)
   {
      simpleTestCase(new int[] {1,2,3},new int[] {2,1,3});
      simpleTestCase(new int[] {1,3,2},new int[] {2,3,1});
      simpleTestCase(new int[] {2,1,3},new int[] {2,1,3});
      simpleTestCase(new int[] {2,3,1},new int[] {2,3,1});
      simpleTestCase(new int[] {3,1,2},new int[] {2,1,3});
      simpleTestCase(new int[] {3,2,1},new int[] {2,3,1});
      if (simpleFailures != 0)
      {
         System.out.println();
         System.out.println("FATAL ERROR");
         System.out.println
            ("   Failing the above simple test case(s) indicates a basic flaw in your");
         System.out.println
            ("   solution. Further testing is suppressed until you fix the problem.");
         System.out.println
            ("   Are you sorting the values rather than just finding the median?");
         System.exit(1);
      }

      int[] data1 = new int[] {6,4,7,1,2,0,9,8,3,5};
      int[] data2 = new int[] {6,4,7};
      int[] data3 = new int[] {6,4};
      int[] data4 = new int[] {6};
      int[] data5 = new int[] {10,1,9,2,8,3,7,4,6,5};
      Random g = new Random(735661237);
      int[] data6 = IntArray.makeRandomArray(5000,1,10000,g);
      int[] data7 = new int[1000];
      for(int i=0;i<data7.length;++i)
         data7[i] = i;
      int[] data6Copy = IntArray.copy(data6);
      int[] data7Copy = IntArray.copy(data7);

      testCase(data1,null);
      testCase(data2,null);
      testCase(data3,null);
      testCase(data4,null);
      testCase(data5,null);

      useMedianOfThree = false;
      System.out.println();
      System.out.println("Not using median-of-three pivot selection");
      testCase(data6,"RECORD THIS DATA ON YOUR LAB SHEET");
      testCase(data7,"RECORD THIS DATA ON YOUR LAB SHEET");

      useMedianOfThree = true;
      System.out.println();
      System.out.println("Using median-of-three pivot selection with same data");
      testCase(data6Copy,"RECORD THIS DATA ON YOUR LAB SHEET");
      testCase(data7Copy,"RECORD THIS DATA ON YOUR LAB SHEET");
   }

   // The following method is provided ONLY for the purpose of isolating the 
   // counting of comparisons into a single location.  Any method below that
   // requires such counting can simply perform the comparison using this 
   // method and the counter will automatically be updated.  Of course this 
   // method would not be used in a production-quality implementation.
   // Although this method uses the same logic as compareTo, this concept
   // has nothing to do with the Comparable interface.

   // PRECONDITION:  left and right must be elements of the array being sorted.
   // There is no easy way that violations of this condition can be detected in  
   // this method.

   private static int compare(int left, int right) 
   {
      ++comparisons;
      if (left < right)
         return -1;
      else if (left > right)
         return 1;
      else
         return 0;
   }

   // This is the usual recursive implementation of quickSort 

   private static void quickSort(int[] data,int first,int last)  
   {
      if (first >= last)
         return;
      if (useMedianOfThree)
         medianOfThree(data,first,last);
      int p = partition(data,first,last);
      quickSort(data,first,p-1); 
      quickSort(data,p+1,last);
   }

   // No action is taken if there are not at least three elements in the 
   // subarray. At the end of a call, the pivot value must be in data[first].  
   // You should swap, if necessary, to make this adjustment.  Otherwise, there 
   // should be no data movement in this method - DO NOT SORT.  In order to 
   // easily count comparisons, all comparisons should be done using calls 
   // to "compare".

   private static void medianOfThree(int[] data,int first,int last)  
   {
      int startComparisons = comparisons;
      if (last-first+1 >= 3)  // If there are at least 3 elements
      {
         // Begin student-supplied code

         // Fill in the details only within this "if" statement
         // Do NOT supply your own "return" statement.


         // End of student-supplied code

         int comparisonsUsed = comparisons - startComparisons;
         if (comparisonsUsed == 0)
         {
            System.out.println();
            System.out.println("ERROR: You apparently did not use \"compare\" to count " +
               "comparisons");
            System.out.println("in \"medianOfThree\".");
            System.exit(1);
         }
         else if (comparisonsUsed == 2)
            ++usedTwoComparisons;
         else if (comparisonsUsed > 3)
         {
            System.out.println();
            System.out.println("ERROR: Too many comparisons (" + comparisonsUsed +
               ") used to find median.");
            System.out.println("Either your logic is wrong or you used \"compare\" " +
               "to count non-data values.");
            System.exit(1);
         }
      }
   }

   // The value returned by this method must be the index of the array
   // element that contains the pivot after the partition is complete.
   // Comparisons are counted by the calls to "compare".

   private static int partition(int[] data,int first,int last)
   {
      // Fill in the details

      /*pivot is data[first] 
      initialize i = first+1;
      initialize j = last;
      loop  
      {
         while i<=j and data[i]<=pivot
         { increment i }
         while j>=i and data[j]>pivot
         { decrement j }
         if (i > j)
            break;
         swap data[i] and data[j]
         ++i;
         --j;
      }
      swap data[first] and data[j]
      return j*/
   }

   // This method is used for grading purposes.  The given array is sorted
   // and the result checked for correctness.

   private static void testCase(int[] data,String message)
   {
      comparisons = 0;
      usedTwoComparisons = 0;
      int numInversions = inversions(data);
      quickSort(data,0,data.length-1);
      if (!IntArray.isSorted(data))
      {
         System.out.println();
         System.out.println("Sorting failed");
         System.exit(1);
      }
      //System.out.println("Quicksort worked correctly");
      if (message != null)
      {
         System.out.println("   " + message);
         System.out.println("      Array size = " + fmt.format(data.length));
         System.out.println("      Number of inversions in original data = " + 
                                                     fmt.format(numInversions));
         System.out.println("      Comparisons = " + fmt.format(comparisons));

         if (useMedianOfThree)
            System.out.println("      Finding median required 2 comparisons = " +
               fmt.format(usedTwoComparisons));
      }
   }

   // This method is used for grading purposes just to unit test medianOfThree.

   private static void simpleTestCase(int[] data,int[] answer)
   {
      int[] copyOfData = IntArray.copy(data);
      comparisons = 0;
      medianOfThree(data,0,data.length-1);
      if (!IntArray.equals(data,answer))
      {
         System.out.println("FAILED simple median test case");
         System.out.print("   Original Data: ");
         IntArray.println(copyOfData);
         System.out.print("   Your answer: ");
         IntArray.println(data);
         System.out.print("   Correct answer: ");
         IntArray.println(answer);
         ++simpleFailures;
      }
   }

   // This method is provided just to verify that the array is actually 
   // partitioned about the given pivot.  I use it only to check your work.

   private static boolean isPartitioned(int[] data,int pivotPos)
   {
      for(int i=0;i<data.length;++i)
      {
         if (i<pivotPos && data[i]>data[pivotPos])
            return false;
         if (i>pivotPos && data[i]<data[pivotPos])
            return false;
      }
      return true;
   } 

   // This is the usual method for counting inversions.

   private static int inversions(int[] x)
   {
      int i,j,n = 0;
      for(i=0;i<x.length-1;++i)
         for(j=i+1;j<x.length;++j)
            if (x[i] > x[j])
               ++n;
      return n;
   }
}