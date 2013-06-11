/* Garrett Milster */

public class InsertionTest
{

   public static void sortLinear(Comparable[] x)
   {
      for (int i = 1; i < x.length; i++)
      {
        // This if statement checks if a point in the array is greater than the one after it.
        if((x[i-1].compareTo(x[i]) > 0))
        {
           Comparable temp = x[i];
           x[i] = x[i-1];
           // This low variable is the index in the array where the temp variable needs to go.
           int low = linearSearch(x, i-1, temp);
         
           Comparable temp1 = temp;
           Comparable temp2 = temp;
           int high = i-1;
           
           // Saves the current value, and the overwrites it with the previous value.
           for(int j = low; j <= high; j++)
           {
              if(j == low)
              {
                 temp1 = x[j];
                 x[j] = temp;
              }
               else
               {
                  temp2 = x[j];
                  x[j] = temp1;
                  temp1 = temp2;
               }
            }
         }
      }
       
   }
   
   // All the variables in this method work the exact same as the sortLinear method.
   public static void sortBinary(Comparable[] x)
   {
      for (int i = 1; i < x.length; i++)
      {
        if((x[i-1].compareTo(x[i]) > 0))
        {
          Comparable temp = x[i];
          x[i] = x[i-1];
          int low = binarySearch(x, i-1, temp);
          
          Comparable temp1 = temp;
          Comparable temp2 = temp;
          int high = i -1;
          
            for(int j = low; j <= high; j++)
            {
                if(j == low)
                {
                   temp1 = x[j];
                   x[j] = temp;
                }
                else
                {
                   temp2 = x[j];
                   x[j] = temp1;
                   temp1 = temp2;
                }
             }
        }
      }
      
   }
   public static int linearSearch(Comparable x[], int high, Comparable key)
   {
      for(int i = 0; i <= high; i++)
      {
         // Steps through the array and looks for a place where the key parameter is less than or equal to a value, because that is the place it must be added.
         if(key.compareTo(x[i]) <= 0)
         {
            return i;
         }
      
      }
      
      return 0;
   }
   public static int binarySearch(Comparable x[], int high, Comparable key)
   {
      // Standard binary search.
      
      int low = 0;
      int mid = (low + high) / 2;
      int temphigh = high;
      
      while(low <= high)
      {
         mid = (low + high) / 2;

         if(key.compareTo(x[mid]) > 0)
         {
            low = mid + 1;
         }
         else if(key.compareTo(x[mid]) < 0)
         {
            high = mid - 1;
         }
         else
         {
            return mid;
         }
      }
      
     return low;
   }
} 