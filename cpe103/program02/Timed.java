// Garrett Milster

import java.util.Random;

public class Timed
{
   public static void main(String[] args)
   {
      InsertionTest sort = new InsertionTest();
      // three that are arrays already sorted.
      Comparable[] x1 = new Comparable[10000];
         for(int i = 0; i < x1.length; i++)
         {
            x1[i] = new Integer(i);
         }
      
      Comparable[] x2 = new Comparable[100000];
         for(int i = 0; i < x2.length; i++)
         {
            x2[i] = new Integer(i);
         }
      
      Comparable[] x3 = new Comparable[200000];
         for(int i = 0; i < x3.length; i++)
         {
            x3[i] = new Integer(i);

         } 
      
      long time1 = 0;
      long time2 = 0;
      double diff = 0;
      
      //Calculates the time it takes to sort.
      
      time1 = System.currentTimeMillis();
         sort.sortLinear(x1);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      // If the verify function returns true, meaning the list is sorted from least to greatest, the program prints the statement.
      
      if(verify(x1))
         System.out.println("For 10000 objects in order, the Linear Search sort time is " + diff + " seconds; Sorting Verified");
      
      time1 = System.currentTimeMillis();
         sort.sortLinear(x2);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      if(verify(x2))
         System.out.println("For 100000 objects in order, the Linear Search sort time is " + diff + " seconds; Sorting Verified");
      
      time1 = System.currentTimeMillis();
         sort.sortLinear(x3);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      if(verify(x3))
         System.out.println("For 200000 objects in order, the Linear Search sort time is " + diff + " seconds; Sorting Verified");
      
      // Repeating the process above but for Binary sort.
      
         System.out.println("");
      
      time1 = System.currentTimeMillis();
         sort.sortBinary(x1);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
         
      if(verify(x1))
         System.out.println("For 10000 objects in order, the Binary Search sort time is " + diff + " seconds; Sorting Verified");
      
      time1 = System.currentTimeMillis();
         sort.sortBinary(x2);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
         
      if(verify(x2))
         System.out.println("For 100000 objects in order, the Binary Search sort time is " + diff + " seconds; Sorting Verified");
   
      time1 = System.currentTimeMillis();
         sort.sortBinary(x3);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
         
      if(verify(x3))
         System.out.println("For 200000 objects in order, the Binary Search sort time is " + diff + " seconds; Sorting Verified");
      
      // Reverse order arrays.
      // Taking the original array and overwriting it with reverse order integers.
      
      Comparable[] y1 = x1;
      int counter = 10000;
         for(int i = 0; i < y1.length; i++)
         {
            y1[i] = counter;
            counter--;
         }
      
      Comparable[] y2 = x2;
      counter = 100000;
         for(int i = 0; i < y2.length; i++)
         {
            y2[i] = counter;
            counter--;
         }
      
      Comparable[] y3 = x3;
      counter = 200000;
         for(int i = 0; i < y3.length; i++)
         {
            y3[i] = counter;
            counter--;
         }
            
      System.out.println("");
      
      time1 = System.currentTimeMillis();
         sort.sortLinear(y1);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      if(verify(y1))
         System.out.println("For 10000 objects in reverse order, the Linear Search sort time is " + diff + " seconds; Sorting Verified");
      
      time1 = System.currentTimeMillis();
         sort.sortLinear(y2);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      if(verify(y2))
         System.out.println("For 100000 objects in reverse order, the Linear Search sort time is " + diff + " seconds; Sorting Verified");
      
      time1 = System.currentTimeMillis();
         sort.sortLinear(y3);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      if(verify(y3))
         System.out.println("For 200000 objects in reverse order, the Linear Search sort time is " + diff + " seconds; Sorting Verified");
      
      // Now to refresh the reverse order list after they were ordered.
      
      counter = 10000;
         for(int i = 0; i < y1.length; i++)
         {
            y1[i] = counter;
            counter--;
         }
      
      counter = 100000;
         for(int i = 0; i < y2.length; i++)
         {
            y2[i] = counter;
            counter--;
         }
      
      counter = 200000;
         for(int i = 0; i < y3.length; i++)
         {
            y3[i] = counter;
            counter--;
         }
         
      System.out.println("");
      
      // Using binary sort to order the reversed array. 
      
      time1 = System.currentTimeMillis();
         sort.sortBinary(y1);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
         
      if(verify(y1))
         System.out.println("For 10000 objects in reverse order, the Binary Search sort time is " + diff + " seconds; Sorting Verified");
      
      time1 = System.currentTimeMillis();
         sort.sortBinary(y2);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
         
      if(verify(y2))
         System.out.println("For 100000 objects in reverse order, the Binary Search sort time is " + diff + " seconds; Sorting Verified");
      
      time1 = System.currentTimeMillis();
         sort.sortBinary(y3);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
         
      if(verify(y3))
         System.out.println("For 200000 objects in reverse order, the Binary Search sort time is " + diff + " seconds; Sorting Verified");
      
      // Sorting the random array of integers with linear sort.
      // Taking the reverse ordered arrays and overwriting them with random numbers.
      
      Comparable[] z1 = y1;
      Random r1 = new Random();
         for(int i = 0; i < z1.length; i++)
         {
            z1[i] = r1.nextInt();
         }
      
      Comparable[] z2 = y2;
      Random r2 = new Random();
         for(int i = 0; i < z2.length; i++)
         {
            z2[i] = r2.nextInt();
         }
      
      Comparable[] z3 = y3;
      Random r3 = new Random();
         for(int i = 0; i < z3.length; i++)
         {
            z3[i] = r3.nextInt();
         }
      
      System.out.println("");
      
      time1 = System.currentTimeMillis();
         sort.sortLinear(z1);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      if(verify(z1))
         System.out.println("For 10000 objects in random order, the Linear Search sort time is " + diff + " seconds; Sorting Verified");
      
      time1 = System.currentTimeMillis();
         sort.sortLinear(z2);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      if(verify(z2))
         System.out.println("For 100000 objects in random order, the Linear Search sort time is " + diff + " seconds; Sorting Verified");
      
      time1 = System.currentTimeMillis();
         sort.sortLinear(z3);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      if(verify(z3))
         System.out.println("For 200000 objects in random order, the Linear Search sort time is " + diff + " seconds; Sorting Verified");
      
      //Now to refresh the random lists of integers.
         for(int i = 0; i < z1.length; i++)
         {
            z1[i] = r1.nextInt();
         }
      
         for(int i = 0; i < z2.length; i++)
         {
            z2[i] = r2.nextInt();
         }
      
         for(int i = 0; i < z3.length; i++)
         {
            z3[i] = r3.nextInt();
         }
      
      // Sorting the random array of integers with binary sort.
      
      System.out.println("");
      
      time1 = System.currentTimeMillis();
         sort.sortBinary(z1);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      if(verify(z1))
         System.out.println("For 10000 objects in random order, the Binary Search sort time is " + diff + " seconds; Sorting Verified");
      
      time1 = System.currentTimeMillis();
         sort.sortBinary(z2);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      if(verify(z2))
         System.out.println("For 100000 objects in random order, the Binary Search sort time is " + diff + " seconds; Sorting Verified");
      
      time1 = System.currentTimeMillis();
         sort.sortBinary(z3);
      time2 = System.currentTimeMillis();
         diff = (time2 - time1) * .001;
      
      if(verify(z3))
         System.out.println("For 200000 objects in random order, the Binary Search sort time is " + diff + " seconds; Sorting Verified");       
   }
   
   public static boolean verify(Comparable[] x)
   {
      // Goes through the list and checks if the current value is greater than the next value in the array.
      for(int i = 0; i < x.length - 1; i++)
      {
         if(x[i].compareTo(x[i + 1]) > 0)
         {
            if(x.length > 100000)
            {
               System.out.println("Failed on index " + i + ", NUMBERS: " + x[i] + " and " + x[i+1]);
            }
            return false;
         }
      }
      return true;
   }
}