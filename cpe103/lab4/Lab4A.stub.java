import java.text.DecimalFormat;
import java.util.*;
import mycs1.*;
   
public class Lab4A
{
   private static int comparisons;
   private static DecimalFormat fmt = new DecimalFormat("#,###,###,###,###,###,##0");
   private static boolean shouldBeNegative = true;

   public static void main(String[] args)
   {
      int[] data0 = new int[] {-5,-4,-3,-2,-1};
      int[] data1 = new int[] {6,4,7,1,2,0,9,8,3,5};
      int[] data2 = new int[] {6,4,7};
      int[] data3 = new int[] {6,4};
      int[] data4 = new int[] {6};
      int[] data5 = new int[] {10,1,9,2,8,3,7,4,6,5};
      Random g = new Random(923781921);
      int[] data6 = IntArray.makeRandomArray(5000,1,10000,g);
 
      testCase(data0,true);
      shouldBeNegative = false;
      testCase(data1,true);
      testCase(data2,true);
      testCase(data3,true);
      testCase(data4,true);
      testCase(data5,true);
      testCase(data6,false);
   }

   // The following method is provided ONLY for the purpose of isolating the 
   // counting of comparisons into a single location.  Any method below that
   // requires such counting can simply perform the comparison using this 
   // method and the counter will automatically be updated.  Of course this 
   // method would not be used in a production-quality implementation.
   // Although this method uses the same logic as compareTo (returning -1, 0, 
   // or 1), this concept has nothing to do with the Comparable interface.

   // PRECONDITION:  left and right must be elements of the array being sorted.
   // There is no easy way that violations of this condition can be detected in 
   // this method.  My use of shouldBeNegative is rather contrived, and you really
   // don't need to know what it's doing.

   private static int compare(int left, int right) 
   {
      if (shouldBeNegative && (left>=0 || right>=0))
      {
         System.out.println();
         System.out.println("ERROR: The parameters to \"compare\" are not data items.");
         System.exit(1);
      }
      ++comparisons;
      if (left < right)
         return -1;
      else if (left > right)
         return 1;
      else
         return 0;
   }

   // The value returned by this method must be the index of the array
   // element that contains the pivot after the partition is complete.

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

   // This method is used for grading purposes.  The given array is partitioned
   // about the 0'th element, and the result is checked to verify that the array
   // is correctly partitioned.

   private static void testCase(int[] data,boolean printFlag)
   {
      int pivot = data[0];
      comparisons = 0;
      int pos = partition(data,0,data.length-1);
      if (!isPartitioned(data,pos))
      {
         System.out.println();
         System.out.println("Partitioning failed");
         if (printFlag)
            IntArray.println(data);
         System.exit(1);
      }
      System.out.println();
      System.out.println("Partitioning is correct");
      if (printFlag)
         IntArray.println(data);
      System.out.println("   Pivot value " + pivot + " is in position " + pos);
      System.out.println("   Array size = " + fmt.format(data.length));
      System.out.println("   Comparisons = " + fmt.format(comparisons));
      if (comparisons < data.length-1)
      {
         System.out.println();
         System.out.println("ERROR: Number of comparisons is too small."); 
         System.out.println("You should know the correct answer.");
         System.exit(1);
      }
      else if (comparisons > data.length)
      {
         System.out.println();
         System.out.println("ERROR: Number of comparisons is too large.");
         System.out.println("You should know the correct answer.");
         System.exit(1);
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
}