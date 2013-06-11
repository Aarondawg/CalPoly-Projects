import java.text.DecimalFormat;
import mycs1.*;
import java.util.*;

public class Lab4C
{
   private static int CUTOFF = 0;
   private static final boolean removeRecursion = false;
   private static int comparisons;
   private static int methodCalls;
   private static DecimalFormat fmt = new DecimalFormat("#,###,###,###,###,###,##0");

   public static void main(String[] args)
   {
      Random g = new Random(137765911);
      int[] data1 = new int[] {6,4,7,1,2,0,9,8,3,5};
      int[] data2 = new int[] {6,4,7};
      int[] data3 = new int[] {6,4};
      int[] data4 = new int[] {6};
      int[] data5 = new int[] {10,1,9,2,8,3,7,4,6,5};
      int[] data6 = IntArray.makeRandomArray(5000,1,10000,g);
      int[] data6A = IntArray.copy(data6);
      int[] data7 = new int[1000];
      for(int i=0;i<data7.length;++i)
         data7[i] = i;
      int[] data7A = IntArray.copy(data7);

      testCase(data1,null);
      testCase(data2,null);
      testCase(data3,null);
      testCase(data4,null);
      testCase(data5,null);
      testCase(data6,"VERIFY THIS DATA WITH THE RESULTS ON YOUR LAB SHEET");
      testCase(data7,"VERIFY THIS DATA WITH THE RESULTS ON YOUR LAB SHEET");
      CUTOFF = 20;
      testCase(data6A,"VERIFY THIS DATA WITH THE RESULTS ON YOUR LAB SHEET");
      testCase(data7A,"VERIFY THIS DATA WITH THE RESULTS ON YOUR LAB SHEET");
   }

   // See lab description for logic

   private static void nonrecursiveQuickSort(int[] data)  
   {
      ++methodCalls;

      // Fill in the details

   }

   // This is the usual recursive implementation of quickSort using 
   // median-of-three pivot selection and no other enhancements.

   private static void quickSort(int[] data,int first,int last)  
   {
      ++methodCalls;
      int size = last-first+1;
      if (size <= CUTOFF)
         binaryInsertionSort(data,first,last);
      else
      {
         medianOfThree(data,first,last);
         int p = partition(data,first,last);
         quickSort(data,first,p-1); 
         quickSort(data,p+1,last);
      }
   }

  // The following method is provided ONLY for the purpose of isolating the 
   // counting of comparisons into a single location.  Any method below that
   // requires such counting can simply perform the comparison using this 
   // method and the counter will automatically be updated.  Of course this 
   // method would not be used in a production-quality implementation.
   // Although this method uses the same logic as compareTo, this concept
   // has nothing to do with the Comparable interface.

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

   // No action is taken if there are not at least three elements in the 
   // subarray. At the end of a call, the pivot value must be in data[first].  
   // You should swap, if necessary, to make this adjustment.  Otherwise, there 
   // should be no date movement in this method.  In order to easily count 
   // comparisons, all comparisons should be done using calls to "compare".

   private static void medianOfThree(int[] data,int first,int last)  
   {
      int temp,middle,median;

      if (last-first+1 < 3)
         return;
      middle = (first+last)/2;
      if (compare(data[first],data[middle]) <= 0)
         if (compare(data[middle],data[last]) <= 0)
            median = middle;
         else if (compare(data[last],data[first]) <= 0)
            median = first;
         else
            median = last;
      else
         if (compare(data[first],data[last]) <= 0)
            median = first;
         else if (compare(data[last],data[middle]) <= 0)
            median = middle;
         else
            median = last;
      temp = data[first];
      data[first] = data[median];
      data[median] = temp;
   }

   // The value returned by this method must be the index of the array
   // element that contains the pivot after the partition is complete.
   // Comparisons are counted by the calls to "compare".

   private static int partition(int[] data,int first,int last)
   {
      int left = first+1;
      int right = last;
      int temp;
      while(true)
      {
         while(left<=right && compare(data[left],data[first])<=0)
            ++left;
         while(right>=left && compare(data[first],data[right])<0)
            --right;
         if (left > right)
            break;
         temp = data[left];
         data[left] = data[right];
         data[right] = temp;
         ++left;
         --right;
      } 
      temp = data[first];
      data[first] = data[right];
      data[right] = temp;
      return right;
   }

   // This is the usual implementation of binary insertion sort that uses a call
   // to binarySearch to determine where the current item belongs in the array.

   private static void binaryInsertionSort(int[] data,int first,int last)
   {
      for(int i=first+1;i<=last;++i)
      {
         int target = binarySearch(data,data[i],first,i-1);
         if (target != i)
         {
            int temp = data[i];
            for(int j=i;j>target;--j)
               data[j] = data[j-1];
            data[target] = temp;
         }
      }
   }

   // Use binary search to determine the index of the data array after which x
   // should be inserted.

   private static int binarySearch(int[] data,int x,int first,int last)
   {
      int middle = 0;
      while(first<=last)
      {
         middle = (first+last)/2;
         if (compare(x,data[middle]) < 0)
            last = middle - 1;
         else if (compare(x,data[middle]) > 0)
            first = middle + 1;
         else
            return middle + 1;  
      }
      if (compare(x,data[middle]) > 0)
         return middle + 1;
      else
         return middle;
   }

   // This method is used for grading purposes.  The given array is sorted
   // and the result checked for correctness.

   private static void testCase(int[] data,String message)
   {
      comparisons = 0;
      methodCalls = 0;
      int numInversions = inversions(data);
      if (removeRecursion)
         nonrecursiveQuickSort(data);
      else
         quickSort(data,0,data.length-1);

      if (!IntArray.isSorted(data))
      {
         System.out.println();
         System.out.println("Sorting failed");
         System.exit(1);
      }
      if (removeRecursion)
         System.out.println("Non-recursive Quicksort worked correctly");
      else
         System.out.println("Recursive Quicksort worked correctly");
      if (message != null)
      {
         System.out.println("   " + message);
         System.out.println("   CUTOFF = " + fmt.format(CUTOFF));
         System.out.println("   Array size = " + fmt.format(data.length));
         System.out.println("   Number of inversions in original data = " + 
                                                     fmt.format(numInversions));
         System.out.println("   Comparisons = " + fmt.format(comparisons));
         System.out.println("   Method calls = " + fmt.format(methodCalls));
      }
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