/**
 * A Linked List class that can add, remove, get nodes containing data.
 * 
 *
 * @author Garrett Milster
 * @version 6
 */

import java.util.List;

public class LinkedList<E>
{

   private class Node
   {
      public E data;
      public Node next;
      public Node previous;
   }

   private Node head, tail;
   private int size = 0;
   
   private class MyIterator implements ListIterator<E>
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
      
      public E next()
      {
         if(!(hasNext()))
         {
            throw new java.util.NoSuchElementException();
         }
         
         if(cursor == null)
         {
            cursor = head;
         }
         else
         {
            cursor = cursor.next;
         }
         
         return cursor.data;
      }
      
      public boolean hasPrevious()
      {
         
         if(cursor == null)
         {
            return false;
         }
     
       return true;
      }
      
      public E previous()
      {
         E data;
        
         if(!(hasPrevious()))
         {
            throw new java.util.NoSuchElementException();
         }
         
       
         data = cursor.data;
         cursor = cursor.previous;
         
         return data;
      }
      
      public void remove()
      {
         throw new UnsupportedOperationException();
      }      
   }
   
   public LinkedList()
   {
         
   }
   public LinkedList(List<E> list)
   {
      int length = list.size();
      
      for(int i = 0; i < length; i++)
      {
         add(list.get(i));
      }
   }
   
   public void add(E data)
   {
      Node temp = new Node();
      temp.data = data;
      
      if(head == null)
      {
         temp.previous = null;
         head = tail = temp;
      }
      else
      {
         tail.next = temp;
         temp.previous = tail;
         tail = temp;
      }
      
      size = size + 1;
   }
   
   public void add(int index, E element)
   {
      int length = size();
      Node temp = new Node();
      temp.data = element;
      Node temp3;
      
      if(index < 0 || index > length)
      {
         throw new IndexOutOfBoundsException();
      }

      if(head == null)
      {
         temp.previous = null;  
         head = tail = temp;    
      }
      else if(index == 0)
      {
         temp3 = head;
         head = temp;
         temp.next = temp3;
         temp3.previous = temp;
         temp.previous = null;
      }
      else if(index == size)
      {
         tail.next = temp;
         temp.previous = tail;
         tail = temp;
      }
      else
      {
         Node temp1 = head;
         Node temp2 = head;
   
         for(int i = 0; i < index; i++)
         {
            temp2 = temp1;
            temp1 = temp1.next;
         }
      
      temp2.next = temp;
      temp.next = temp1;
      temp1.previous = temp;
      temp.previous = temp2;
      
      }
      
      size = size + 1;
   }
   
   public void add(List<E> list)
   {
      int length = list.size();
      
      for(int i = 0; i < length; i++)
      {
         add(list.get(i));
      }
   }

   public void clear()
   {
      head = tail = null;
      size = 0;
   }
   
   public E get(int index)
   {
      if(index < 0 || index >= size)
      {
         throw new IndexOutOfBoundsException();
      }
      
      Node temp = head;
      
      for(int i = 0; i < index; i++)
      {
         temp = temp.next;
      }
      return temp.data;
   }
   
   public int indexOf(E element)
   {
      int index = 0;
      
      for(int i = 0; i < size; i++)
      {
         if(element.equals(this.get(i)))
         {
            index = i;
            return index;
         }
      }
      
      if(index == 0)
      {      
      throw new java.util.NoSuchElementException();
      }
      
      return index;
   }

   public E remove(int index)
   {
      int length = size();
      E data;
      
      if(index < 0 || index >= length || head == null)
      {
         throw new IndexOutOfBoundsException();
      }

      Node temp1 = head;
      Node temp2 = head;
      
      if(index == 0)
      {
         data = head.data;
         head = head.next;
         if(head != null)
         {
            head.previous = null;
         }
         size = size -1;
         return data;
      }
      
      if(index == length -1)
      {
         for(int i = 0; i < index; i++)
         {
            temp2 = temp1;
            temp1 = temp1.next;
         }
         tail = temp2;
      }
      else
      {
         for(int i = 0; i < index; i++)
         {
            temp2 = temp1;
            temp1 = temp1.next;
         }
      }
      
      data = temp1.data;
      temp2.next = temp1.next;
      temp1 = temp1.next;
      
      if(temp1 != null)
      {
         temp1.previous = temp2;
      }
      size = size - 1;
      return data;
   }
   
   public E set(int index, E element)
   {
      E data;
      
      if(index < 0 || index >= size)
      {
         throw new IndexOutOfBoundsException();
      }
      
      Node temp = head;
      
      for(int i = 0; i < index; i++)
      {
         temp = temp.next;
      }
      
      data = temp.data;
      temp.data = element;
      
      return data;
   }
   
   public int size()
   {
      return size;
   }
   
   public ListIterator<E> listIterator()
   {
      MyIterator i = new MyIterator();
          
      return(i);
   }
   
}
