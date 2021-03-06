import java.io.*;
import java.util.Iterator;

public class LinkedList implements Cloneable
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

   private Node head;
   private Node tail;
   private int size;

   // Default Constructor

   public LinkedList()
   {
      head = null;
      tail = null;
   }

   // Insert the given object at the beginning of the list.

   public void addFirst(Object item)
   {
      Node temp = new Node(item);
      
      Node temp3;

      if(head == null)
      {
         temp.prev = null;  
         head = tail = temp;    
      }
      else 
      {
         temp3 = head;
         head = temp;
         temp.next = temp3;
         temp3.prev = temp;
         temp.prev = null;
      }

      size = size + 1;
   }

   // Insert the given object at the end of the list.

   public void addLast(Object item)
   {
      Node temp = new Node(item);
      
      if(head == null)
      {
         temp.prev = null;
         head = tail = temp;
      }
      else
      {
         tail.next = temp;
         temp.prev = tail;
         tail = temp;
      }
      
      size = size + 1;
   }

   // Return the number of items in the list

   public int length()
   {
      return size;
   }

   // Determine if the list contains no items

   public boolean isEmpty()
   {
      if(size > 0)
      {
         return false;
      }
      
      return true;
      
   }

   // (Virtually) remove all items from the list

   public void clear()
   {
      head = null;
      tail = null;
      size = 0;
   }

   // Determine if the list contains the given item

   public boolean contains(Object item)
   {  

      Node temp = head;
      
      for(int i = 0; i < size ; i++)
      {
        
        if(item.equals(temp.item))
         {
            return true;
         }
         temp = temp.next;
      }

      return false;
   }

   // Remove first item on the list and return it
   
   public Object removeFirst()
   {
         if(head == null)
         {
            throw new Error("The list is empty");
         }
         if(size == 1)
         {
            Object item = head.item;
            head = null;
            tail = null;
            size = size - 1;
            return item;
         }
   
         Object item;
         item = head.item;
         head = head.next;
         head.prev = null;
         
         size = size -1;
         return item;
      
   }

   // Remove last item on the list and return it
   
   public Object removeLast()
   {
      Node temp1 = head;
      Node temp2 = head;
      
      if(size == 0)
      {
         throw new Error("THE LIST IS EMPTY");
      }
      if(size == 1)
      {
         Object item = head.item;
         head = null;
         tail = null;
         size = size - 1;
         return item;
      }
      
         Object temp = tail.item;
         tail = tail.prev;
        
    size = size - 1;
    return temp;
    
   }
   
   // Determine if two LinkedLists are equal
      
   public boolean equals(Object obj)
   {
      if(obj == null)
         return false;
      if(!(obj instanceof LinkedList))
         return false;
      LinkedList list = (LinkedList)obj;
      if(list.length() != this.length())
         return false;
      Node p = this.head;
      Node q = list.head;
      while(p != null)
      {
         if(!(p.item.equals(q.item)))
         {  
             return false;
         }
         p = p.next;
         q = q.next;
      }
      return true;
   }

   public boolean remove(Object obj)
   {
      if(size == 0)
      {
         return false;
      }

      Node temp = head;

      
      for(int i = 0; i < size ; i++)
      {
         if(temp.item.equals(obj))
         {
            if(size == 1)
            {
               head = null;
               tail = null;
               size = size - 1;
               return true;
            }  
            else if(i == 0)
            {
               head = head.next;
               size = size - 1;
               return true;
            }
            else if(i == size - 1)
            {
               tail = tail.prev;
               size = size - 1;
               return true;
            }
            else
            {
               temp.next.prev = temp.prev;
               temp.prev.next = temp.next;
               size = size - 1;
               return true;
            }
         }

         temp = temp.next;
      }

      return false;
   }
   
   public Iterator iterator()
   {
      return new MyIterator();
   }
   private class MyIterator implements Iterator<Object>
   {
      private Node cursor;
      
      public boolean hasNext()
      {
         if(cursor == null)
         {
            return head != null;
         }
         
         return cursor.next != null;
      }
      
      public Object next()
      {
         if(!(hasNext()))
         {
            throw new Error("No Next Element");
         }
         
         if(cursor == null)
         {
            cursor = head;
         }
         else
         {
            cursor = cursor.next;
         }
         
         return cursor.item;
      }

      
      public void remove()
      {
         throw new UnsupportedOperationException();
      }      
   }
   
   public Object clone()
   {
      LinkedList clone = new LinkedList();
      Node p = head;
      while(p != null)
      {
         clone.addLast(p.item);
         p = p.next;
      }
      return clone;
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
   // is the head item in the list. The number of items included is the smaller 
   // of the number of items in the list and the value of printLimit. The "..."
   // is shown only if there are list items that were not included.  

   public String toString()
   {
      String answer = "{";
      int ctr = 0;
      for(Node curr=head;curr!=null;curr=curr.next)
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


