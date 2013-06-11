import java.io.*;
import java.util.Iterator;

public class LinkedList
{

   private class Node
   {
      private Object item;
      private Node next;
      private Node prev;

      private Node(Object x)
      {
         item = x;
         next = null;
         prev = null;
      }
   }

   // Self explanatory instance variables

   private Node first;
   private Node last;

   // Default Constructor

   public LinkedList()
   {
      first = null;
      last = null;
   }

   // Insert the given object at the beginning of the list.

   public void addFirst(Object item)
   {
      // Supply details as in the assignment description
   }

   // Insert the given object at the end of the list.

   public void addLast(Object item)
   {
      // Supply details as in the assignment description
   }

   // Return the number of items in the list

   public int length()
   {
      // Supply details as in the assignment description
   }

   // Determine if the list contains no items

   public boolean isEmpty()
   {
      // Supply details as in the assignment description
   }

   // (Virtually) remove all items from the list

   public void clear()
   {
      first = null;
      last = null;
   }

   // Determine if the list contains the given item

   public boolean contains(Object item)
   {
      // Supply details as in the assignment description
   }

   // Remove first item on the list and return it
   
   public Object removeFirst()
   {
      // Supply details as in the assignment description
   }

   // Remove last item on the list and return it
   
   public Object removeLast()
   {
      // Supply details as in the assignment description
   }
   
   // Determine if two LinkedLists are equal
      
   public boolean equals(Object)
   {
      // Supply details as in the assignment description
   }

   // **********************************************************************

   // FOR THE PURPOSES OF THIS LAB, YOU DON'T NEED TO SPEND TIME READING THE
   // REST OF THIS FILE.  HOWEVER, YOU SHOULD DO SO LATER ON YOUR OWN TIME.

   // The following specifies the maximum number of items in the list that
   // will be included in the toString method.
 
   private static int printLimit = 20;

   // This method allows the client to control the number of list items
   // that will be included in the toString method.

   public static void setPrintLimit(int limit)
   {
      if (limit >= 1)
         printLimit = limit;
   }

   // This method produces a string of the form {item1,item2,...} where item1 
   // is the first item in the list. The number of items included is the smaller 
   // of the number of items in the list and the value of printLimit. The "..."
   // is shown only if there are list items that were not included.  

   public String toString()
   {
      String answer = "{";
      int ctr = 0;
      for(Node curr=first;curr!=null;curr=curr.next)
      {
         answer = answer + curr.item;
         ++ctr;
         if (curr.next != null)
            if (ctr == printLimit)
               return answer + ",...}";
            else
               answer = answer + ",";
      }
      return answer + "}";
   }

   public static class Error extends RuntimeException
   {
      public Error(String message)
      {
         super(message);
      }
   }
}


